package com.snatch.controller;

import com.snatch.domain.Goods;
import com.snatch.redis.GoodsKey;
import com.snatch.redis.RedisService;
import com.snatch.service.GoodsService;
import com.snatch.vo.GoodsDetailVo;
import com.snatch.vo.GoodsListVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName: GoodsController
 * @description:
 * @author: nping
 * @create: 2020-03-01 16:34
 **/

@RequestMapping("/goods")
@Controller
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/list")
    @ResponseBody
    public List<GoodsListVo> goodsList(){
        List<GoodsListVo> list = goodsService.getGoodsList();
        System.out.println(list.get(0).getGoodsName());
        return list;
    }

    @RequestMapping("/detail/{id}")
    @ResponseBody
    public GoodsDetailVo getDetail(@PathVariable("id") Long id){

        return goodsService.getGoodsDetail(id);
    }



}
