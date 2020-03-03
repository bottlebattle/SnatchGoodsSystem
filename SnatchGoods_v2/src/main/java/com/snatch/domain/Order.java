package com.snatch.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Order {

  private Long id;
  private Long userId;
  private Long goodsId;
  private Long deliveryAddrId;
  private String goodsName;
  private String goodsCount;
  private Double goodsPrice;
  private String status;
  private Date createDate;
  private Date payDate;

}
