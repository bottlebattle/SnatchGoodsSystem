package com.snatch.service;

import com.snatch.dao.GoodsDao;
import com.snatch.dao.GoodsTestDao;
import com.snatch.domain.Goods;
import com.snatch.domain.SnatchGoods;
import com.snatch.vo.GoodsDetailVo;
import com.snatch.vo.GoodsListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: GoodsService
 * @description:
 * @author: nping
 * @create: 2020-03-01 17:20
 **/
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;


    public List<GoodsListVo> getGoodsList() {
        return goodsDao.getGoodsListVos();
    }

    public GoodsDetailVo getGoodsDetail(Long id) {
        return goodsDao.getGoodsDetailById(id);
    }

    public int countOfSnatchStock(Long goodsId) {
        return  goodsDao.countOfSnatchStock(goodsId);
    }

    /**
     * 修改秒杀商品库存信息。
     * @param goodsId
     * @return
     */
    public boolean reduceSnatchStock(Long goodsId) {
        int ret = goodsDao.reduceStock(goodsId);
        return ret > 0;
    }

    public Goods getGoodsById(Long goodsId) {
        return goodsDao.getGoodsById(goodsId);
    }

    public List<GoodsDetailVo> getSnatchGoodsDetailList() {
        return goodsDao.getSnatchGoodsDetailList();
    }
}
