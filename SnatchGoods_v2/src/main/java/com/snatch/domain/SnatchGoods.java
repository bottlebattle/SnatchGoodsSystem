package com.snatch.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SnatchGoods {

  private long id;
  private long goodsId;
  private double snatchPrice;
  private long snatchStock;
  private Date startDate;
  private Date endDate;

}
