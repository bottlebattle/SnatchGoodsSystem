package com.snatch.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName: MD5Util
 * @description:
 * @author: nping
 * @create: 2020-02-10 14:37
 **/

public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassFormPass(String inputPass){
        return md5(inputPass+salt);
    }
    //转换成存储在数据库中的
    public static String FormtoDBPass(String inputPass,String salt){
        return md5(inputPass+salt);
    }
    //明文密码直接转换成数据库中的
    public static String inputPasstoDBPass(String inputPass,String salt){
        String formPass = inputPassFormPass(inputPass);
        return FormtoDBPass(formPass,salt);
    }
    public static void main(String[] args) {
        System.out.println(FormtoDBPass("40485f14ae3d4d84d69063f92214d49b",salt));
        System.out.println(inputPasstoDBPass("miaoshamima",salt));
    }
}
