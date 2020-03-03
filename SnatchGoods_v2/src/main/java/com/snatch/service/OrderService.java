package com.snatch.service;

import com.snatch.dao.OrderDao;
import com.snatch.domain.*;
import com.snatch.vo.GoodsDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName: OrderService
 * @description:
 * @author: nping
 * @create: 2020-03-01 20:08
 **/
@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public SnatchOrder getSnatchOrderByUserIdAndGoodsId(Long userId, Long goodsId) {
        return orderDao.getSnatchOrderByUserIdAndGoodsId(userId,goodsId);
    }

    /**
     * 创建订单并且添加到秒杀订单中
     * @return
     */
    public boolean createSnatchOrder(User user, GoodsDetailVo goods) {
        //1.创建普通订单。
        //2.秒杀订单和普通订单关联
        Order order = new Order();
        order.setGoodsCount("1");
        order.setGoodsName(goods.getGoodsName());
        order.setCreateDate(new Date());
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        long orderId = orderDao.insert(order);
        System.out.println("订单号：" + orderId);
        //根据返回的订单号创建秒杀订单
        SnatchOrder snatchOrder = new SnatchOrder();
        snatchOrder.setGoodsId(goods.getId());
        snatchOrder.setOrderId(orderId);
        snatchOrder.setUserId(user.getId());
        orderDao.insertSnatchOrder(snatchOrder);

        return true;
    }
}
