package com.snatch.domain;

import lombok.Data;

@Data
public class SnatchOrder {

  private long id;
  private long userId;
  private long goodsId;
  private long orderId;
}
