package com.snatch.domain;

import lombok.Data;
@Data
public class Goods {
  private Long id;
  private String goodsName;
  private String goodsTitle;
  private String goodsImg;
  private String goodsDetail;
  private Double goodsPrice;
  private Integer goodsStock;
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

}
