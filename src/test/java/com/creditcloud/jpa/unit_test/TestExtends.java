/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import java.util.Date;
import java.util.Map;


/**
 *
 * @author MRB
 */
public class TestExtends  {
    
    public static void main(String args[]) throws InterruptedException{
        //Thread.sleep(10000);
        TestExtends child = new Child();
        long num =5L;
        child.get(num);//会强制转为对象
        
        System.out.println(System.getProperty("file.encoding"));
        //Thread.sleep(20000);
    }
    
    void get(Object obj){
        System.out.println(obj.getClass());
        System.out.println(obj instanceof Number);
        System.out.println("我是父亲"+obj);
    };
    
    
    static class Child extends TestExtends{
       
         void get(Integer num){
             System.out.println("我是儿子"+num);
         };
    }
}
