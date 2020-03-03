package com.snatch.redis;

/**
 * 商品的前前缀信息
 */
public class GoodsKey extends BasePrefix{

	private GoodsKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	public static GoodsKey getGoodsList = new GoodsKey(60, "gl");

	public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
	//库存信息
	public static GoodsKey getMiaoshaGoodsStock= new GoodsKey(0, "gs");
}
