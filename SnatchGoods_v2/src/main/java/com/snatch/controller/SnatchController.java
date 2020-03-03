package com.snatch.controller;

import com.snatch.domain.SnatchOrder;
import com.snatch.domain.User;
import com.snatch.redis.GoodsKey;
import com.snatch.redis.RedisService;
import com.snatch.redis.UserKey;
import com.snatch.service.GoodsService;
import com.snatch.service.OrderService;
import com.snatch.service.SnatchService;
import com.snatch.service.UserService;
import com.snatch.util.CodeMsg;
import com.snatch.vo.GoodsDetailVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: SnatchController
 * @description: 秒杀业务
 * @author: nping
 * @create: 2020-03-01 19:40
 **/
@Controller
@RequestMapping("/snatch")
public class SnatchController{
    //优化之前
    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    SnatchService snatchService;
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/snatchgoods")
    @ResponseBody
    public CodeMsg snatchgoods(HttpServletRequest request, Long goodsId){
        //获取cookie数组，找到token
        Cookie[] cookies = request.getCookies();
        String token = "";
        if (null==cookies) {
            return CodeMsg.NEED_RELOGIN;
            //返回登录页面。
        } else {
            for(Cookie cookie : cookies){
                //TODO：魔法值需要修改
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if(token == null ||token.length() == 0){
            return CodeMsg.NEED_RELOGIN;
        }
        //把查询到的token传入执行业务层代码。
        CodeMsg result = snatchService.snatch(token,goodsId);
        return result;
    }

}
