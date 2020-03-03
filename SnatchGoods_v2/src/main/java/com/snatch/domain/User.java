package com.snatch.domain;

import lombok.Data;

/**
 * @ClassName: User
 * @description: 用户信息
 * @author: nping
 * @create: 2020-03-01 12:48
 **/
@Data
public class User {
    private long id;
    private String userName;
    private String password;
    private String salt;

}
