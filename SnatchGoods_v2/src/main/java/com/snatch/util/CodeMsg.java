package com.snatch.util;

/**
 * @ClassName: CodeMsg
 * @description:
 * @author: nping
 * @create: 2020-03-01 15:44
 **/

public class CodeMsg {
    public int code;
    public String message;
    CodeMsg(int code,String message){
        this.code = code;
        this.message = message;
    }

    //*登录所用

    public final static CodeMsg LOGIN_SUCCESS = new CodeMsg(1000,"登录成功");
    public final static CodeMsg USER_NOT_EXIST = new CodeMsg(1001,"用户不存在");
    public final static CodeMsg PASSWORD_ERROR = new CodeMsg(1002,"密码错误");
    public final static CodeMsg NEED_RELOGIN = new CodeMsg(1003,"请先登录");

    //秒杀提示所用
    public final static CodeMsg SNATCH_SUCCESS = new CodeMsg(2000,"抢购成功！");
    public final static CodeMsg REPATED_SNATCH = new CodeMsg(2001,"你已经抢购过了");
    public final static CodeMsg STOCK_EMPTY = new CodeMsg(2002,"库存不足！");


}
