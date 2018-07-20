/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 *
 * @author MRB
 */
public class TestRedis {
    
    @Test
    public void testRedis(){
        Jedis jedis = new Jedis("localhost",6379);
        jedis.connect();//连接
//        System.out.println(jedis.exists("abc"));
//        System.out.println(jedis.set("abc", "123"));
//        System.out.println(jedis.get("abc"));
        String key = "KEY";
        for(int i=0;i<1000;i++){
            if(i%4!=0&&!jedis.exists(key+i)){
                System.out.println("找不到key"+i);
            }
        }
    }
}
