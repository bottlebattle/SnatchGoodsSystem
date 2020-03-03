package com.snatch.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    StringRedisTemplate redisTemplate;
    /**
     * 获取当个对象
     * */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
            String realKey  = prefix.getPrefix() + key;
            String  str = redisTemplate.opsForValue().get(realKey);
            T t =  stringToBean(str, clazz);
            return t;
    }

    /**
     * 设置对象
     * */
    public <T> boolean set(KeyPrefix prefix, String key,  T value) {

            String str = beanToString(value);
            if(str == null || str.length() <= 0) {
                return false;
            }
            String realKey  = prefix.getPrefix() + key;
            int seconds =  prefix.expireSeconds();
            if(seconds <= 0) {
                redisTemplate.opsForValue().set(realKey, str);
            }else {
                redisTemplate.opsForValue().set(realKey, str, seconds);
            }
            return true;
    }

    /**
     * 判断key是否存在
     * */
    public <T> boolean exists(KeyPrefix prefix, String key) {

            String realKey  = prefix.getPrefix() + key;
            return  redisTemplate.hasKey(realKey);
    }

    /**
     * 删除
     * */
    public boolean delete(KeyPrefix prefix, String key) {

        Boolean result = redisTemplate.delete(prefix.getPrefix()+key);
        return result;
    }

    /**
     * 增加值
     * */
    public <T> Long incr(KeyPrefix prefix, String key) {

            String realKey  = prefix.getPrefix() + key;
            return  redisTemplate.opsForValue().increment(realKey);

    }

    /**
     * 减少值
     * */
    public <T> Long decr(KeyPrefix prefix, String key) {

            String realKey  = prefix.getPrefix() + key;
            return  redisTemplate.opsForValue().decrement(realKey);

    }

    public boolean delete(KeyPrefix prefix) {
       redisTemplate.delete(prefix.getPrefix());
       return true;
    }

    /*public List<String> scanKeys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> keys = new ArrayList<String>();
            String cursor = "0";
            ScanParams sp = new ScanParams();
            sp.match("*"+key+"*");
            sp.count(100);
            do{
                ScanResult<String> ret = jedis.scan(cursor, sp);
                List<String> result = ret.getResult();
                if(result!=null && result.size() > 0){
                    keys.addAll(result);
                }
                //再处理cursor
                cursor = ret.getCursor();
            }while(!cursor.equals("0"));
            return keys;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }*/

    public static <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }


    public static <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }

}
