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
public class TestByte {
    
    @Test
    public void testString(){
        byte[] bytes = "\u4e2d\u6587".getBytes();
       
        byte[] newBytes = new byte[bytes.length];
        for(int i=0;i<bytes.length;i++){
            newBytes[i] = (byte) (bytes[i]&(0xFF>>1));
            System.out.println(bytes[i]);
        }
       
        System.out.println("123中".length());
        System.out.println(new String(newBytes));
        System.out.println(new String(bytes));
    }
}
