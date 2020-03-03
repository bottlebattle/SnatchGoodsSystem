package com.snatch.rabbitmq;

import com.snatch.domain.User;
import com.snatch.vo.GoodsDetailVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnatchMessage {
	private User user;
	private GoodsDetailVo goodsDetail;

}
