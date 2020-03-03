package com.snatch.service;

import com.snatch.domain.Goods;
import com.snatch.domain.SnatchOrder;
import com.snatch.domain.User;
import com.snatch.rabbitmq.MQSender;
import com.snatch.rabbitmq.SnatchMessage;
import com.snatch.redis.GoodsKey;
import com.snatch.redis.RedisService;
import com.snatch.util.CodeMsg;
import com.snatch.vo.GoodsDetailVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.spi.InitialContextFactory;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: SnatchService
 * @description:
 * @author: nping
 * @create: 2020-03-01 20:42
 **/
@Service
public class SnatchService implements InitializingBean {
    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    MQSender sender;

    //用来记录已经秒杀完的商品ID到内存

    private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();

    public CodeMsg snatch(String token, Long goodsId) {
        User user = userService.getUserBytoken(token);
        if(user == null){
            //登录,token不在缓存中
            System.out.println("未登录");
            return CodeMsg.NEED_RELOGIN;
        }
        //已经登录了，可以做秒杀了。
        //2.判断库存是不是够了
        //先从本地变量中获取查看商品id是不是已经秒杀完的id
        //内存标记，减少redis访问,localOverMap记录已经卖完的商品
        boolean isOver = localOverMap.get(goodsId);
        if(isOver) {
            //System.out.println(goodsId+"库存不足");
            return CodeMsg.STOCK_EMPTY;
        }
        //3.判断用户是否已经秒杀过了
        SnatchOrder snatchOrder = orderService.getSnatchOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if(snatchOrder != null){
            //返回已经秒杀过了
           // System.out.println("已经秒杀过了");
            return CodeMsg.REPATED_SNATCH;
        }
        //4.商品预减，将卖完的商品放入localmap中
        Long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, ""+goodsId);
        if(stock < 0) {
            //库存不足
            //System.out.println("库存不足！"+goodsId);
            localOverMap.put(goodsId, true);
            return CodeMsg.STOCK_EMPTY;
        }
        //4.预减成功，发起异步消息进行秒杀。
        SnatchMessage mm = new SnatchMessage();
        mm.setUser(user);
        GoodsDetailVo goods = goodsService.getGoodsDetail(goodsId);
        mm.setGoodsDetail(goods);
        sender.sendMiaoshaMessage(mm);

        return CodeMsg.SNATCH_SUCCESS;
    }

    @Transactional
    public boolean snatchgoods(User user, GoodsDetailVo goods){
        boolean success = goodsService.reduceSnatchStock(goods.getId());
        if(success) {
            return orderService.createSnatchOrder(user, goods);
        }
        return false;
    }

    /**
     * 将信息提前加载到文件中，信息主要包括，
     *  1.已经参与过秒杀的用户id
     *  2.秒杀商品的信息。（用来作为预减）
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //商品信息添加
        List<GoodsDetailVo> goodsList = goodsService.getSnatchGoodsDetailList();
        if(goodsList == null) {
            return;
        }
        for(GoodsDetailVo goods : goodsList) {
            redisService.set(GoodsKey.getGoodsDetail,""+goods.getId(),goods);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), goods.getSnatchStock());
            if(goods.getSnatchStock() <= 0){
                localOverMap.put(goods.getId(),true);
            }else {
                localOverMap.put(goods.getId(),false);
            }

        }
    }
}
