package com.snatch.controller;

import com.snatch.dao.OrderDao;
import com.snatch.dao.UserDao;
import com.snatch.domain.User;
import com.snatch.redis.RedisService;
import com.snatch.redis.UserKey;
import com.snatch.service.OrderService;
import com.snatch.service.UserService;
import com.snatch.util.CodeMsg;
import com.snatch.util.MD5Util;
import com.snatch.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 为了方便测试，封装了测试用到的api
 * @ClassName: TestController
 * @description:
 * @author: nping
 * @create: 2020-03-02 14:52
 **/
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    RedisService redisService;
    @Autowired
    OrderDao orderDao;

    /**
     * 此处是为了在缓存中生成token就假装登录一小下
     * @param loginVo
     * @param response
     * @return
     */
    @RequestMapping("/generateData")
    @ResponseBody
    public String generateDate(LoginVo loginVo, HttpServletResponse response){
        //2.模拟登录将信息都添加到token中去
        System.out.println(loginVo.getPassword());
        String result = login(response, loginVo);
        return result;
    }
    public String login(HttpServletResponse response, LoginVo loginVo) {
        System.out.println(loginVo.getUserId());
        User user = userDao.getUserById(loginVo.getUserId());

        //如果用户传来的密码和现在的密码一样，则说明可以
        String formPass = loginVo.getPassword();
        String deliverPass = MD5Util.FormtoDBPass(formPass,user.getSalt());

        //登录成功，生成token并且写入到cookie中
        //使用redis保存cookie。方便校验。
        String tooken = (String) userService.addCookie(response,user);
        redisService.set(UserKey.token,tooken,user);
        return tooken;
    }

    @ResponseBody
    @RequestMapping("/reset")
    public void reset(){
        //清除秒杀订单表
        orderDao.deleteAllSnatchOrder();
        System.out.println("清除秒杀订单成功");
        //重置库存

    }
}
