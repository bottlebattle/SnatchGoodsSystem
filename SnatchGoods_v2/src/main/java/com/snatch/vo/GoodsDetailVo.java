package com.snatch.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName: GoodsDetailVo
 * @description:
 * @author: nping
 * @create: 2020-03-01 19:17
 **/
@Setter
@Getter
public class GoodsDetailVo {
    private long id;
    private String goodsName;
    private String goodsImg;
    private Date startDate;
    private double goodsPrice;
    private double snatchPrice;
    private long snatchStock;
}
