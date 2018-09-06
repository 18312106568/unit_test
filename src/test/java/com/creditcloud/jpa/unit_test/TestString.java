/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import org.junit.Test;

/**
 *
 * @author MRB
 */
public class TestString {
    @Test
    public void testStringAppend(){
        String str1 = "aa"+"bb"+"cc"+"dd";
        String str2 = str1+"ee";
        String str3 = "";   
        Integer abc = 256;
        System.out.println(str2);
        //System.out.println("mystr:"+str1);
    }
    
    public void testJavaplocals(String abc){
    }
    
    @Test
    public void testStringIntern() throws InterruptedException {
        String s1 = new String("bbb").intern();
        String s2 = "bbb";
        System.out.println(System.getProperty("file.encoding"));
        System.out.println("测试常量池");
        System.out.println(s1 == s2);    // true
        Thread.sleep(3000);
        
    }
}
