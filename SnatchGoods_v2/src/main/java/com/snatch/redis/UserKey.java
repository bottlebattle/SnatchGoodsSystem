package com.snatch.redis;

import com.snatch.domain.User;

/**
 * @ClassName: UserKey
 * @description: 用户的前缀信息
 * @author: nping
 * @create: 2020-03-02 12:07
 **/

public class UserKey extends BasePrefix {
    public static final int TOKEN_EXPIRE = 3600*24 * 2;
    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static UserKey token = new UserKey(TOKEN_EXPIRE,"tk");
}
