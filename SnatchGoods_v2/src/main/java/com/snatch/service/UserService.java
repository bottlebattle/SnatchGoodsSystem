package com.snatch.service;

import com.snatch.dao.UserDao;
import com.snatch.domain.User;
import com.snatch.redis.RedisService;
import com.snatch.redis.UserKey;
import com.snatch.util.CodeMsg;
import com.snatch.util.MD5Util;
import com.snatch.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: UserService
 * @description:
 * @author: nping
 * @create: 2020-03-01 15:43
 **/
@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    RedisService redisService;

    public CodeMsg login(HttpServletResponse response, LoginVo loginVo) {
        System.out.println(loginVo.getUserId());
        User user = userDao.getUserById(loginVo.getUserId());

        if(user == null){
            return CodeMsg.USER_NOT_EXIST;
        }
        //如果用户传来的密码和现在的密码一样，则说明可以
        String formPass = loginVo.getPassword();
        String deliverPass = MD5Util.FormtoDBPass(formPass,user.getSalt());
        System.out.println(deliverPass);
        System.out.println(user.getPassword());
        if(!user.getPassword().equals(deliverPass)){
            return CodeMsg.PASSWORD_ERROR;
        }
        //登录成功，生成token并且写入到cookie中
        //使用redis保存cookie。方便校验。
        String tooken = addCookie(response,user);
        redisService.set(UserKey.token,tooken,user);
        //System.out.println(getUserBytoken(tooken).toString());
        return CodeMsg.LOGIN_SUCCESS;
    }

    public String addCookie(HttpServletResponse response, User user) {
        String token = UUID.randomUUID().toString().replace("-","");
        //TODO:将token放入缓存中。
        Cookie cookie = new Cookie("token",token);
        System.out.println("设置的cookie："+ token);
        //cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        return token;
    }

    public User getUserBytoken(String token){
        User user = redisService.get(UserKey.token, token, User.class);
        return user;
    }

    public List<User> getUserList() {
        return userDao.getUserList();
    }
}
