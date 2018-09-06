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
public class TestAutoBox {
    @Test
    public void testInteger(){
        Integer a = 128;
        Integer b = 128;
        int c = 128;
        Integer d = -128;
        Integer e = -128;
        //不同对象false
        System.out.println(a==b);
        
         //相同值true
        System.out.println(a==c);

        //相同对象 true
        System.out.println(d==e);
    }
}
