package com.snatch.controller;

import com.snatch.domain.User;
import com.snatch.service.UserService;
import com.snatch.util.CodeMsg;
import com.snatch.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.util.List;

/**
 * @ClassName: UserController
 * @description: 用户登录
 * @author: nping
 * @create: 2020-03-01 15:18
 **/

@RequestMapping("/login")
@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping("/loginPage")
    public String toLoginPage(){
        System.out.println("应该没错呀");
        return "login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public CodeMsg login(LoginVo loginVo, HttpServletResponse response){
        System.out.println(loginVo.getPassword());
        CodeMsg result = userService.login(response, loginVo);
        return result;
    }

    @RequestMapping("/userlist")
    @ResponseBody
    public List<User> getUserList(){
        List<User> lis = userService.getUserList();
        return lis;
    }
}
