package com.snatch.redis;
//通过前缀可以进行批量操作
public interface KeyPrefix {
		
	public int expireSeconds();
	
	public String getPrefix();
	
}
