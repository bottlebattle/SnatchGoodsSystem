package com.snatch.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName: GoodsVo
 * @description: 秒杀商品信息展示
 * @author: nping
 * @create: 2020-03-01 16:56
 **/
@Getter
@Setter
public class GoodsListVo {

    private long id;
    private String goodsName;
    private double snatchPrice;
    private Date startDate;
    private Date endDate;
    private int snatchStock;
}
