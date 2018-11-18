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
    
    @Test
    public void testTohexString(){
        Integer num =20<<16;
        System.out.println(numberOfLeadingZeros(num));
        System.out.println(Integer.toHexString(num));
    }
    
    public static int numberOfLeadingZeros(int i) {
        // HD, Figure 5-6
        if (i == 0)
            return 32;
        int n = 1;
        if (i >>> 16 == 0) {
            n += 16; 
            i <<= 16; 
        }
        if (i >>> 24 == 0) {
            n +=  8;
            i <<=  8;
        }
        if (i >>> 28 == 0) {
            n +=  4;
            i <<=  4; }
        if (i >>> 30 == 0) { 
            n +=  2; 
            i <<=  2; 
        }
        n -= i >>> 31;
        return n;
    }
    
}
