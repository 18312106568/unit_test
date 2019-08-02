/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Binary;

import org.junit.Test;

/**
 *
 * @author MRB
 */
public class TestInteger {

    @Test
    public void testInteger(){
        System.out.println((((3*0.998)*1.0774*0.998)-(2*1.002))*13260-13260);
    }

    @Test
    public void testToBinary(){
        System.out.println(Integer.toHexString(21>>2));
        System.out.println(Integer.toHexString(-21>>2));
        System.out.println(Integer.toHexString(-21>>>2));
        System.out.println(0xd);
    }

    @Test
    public void testCharToInt(){
        char c33 = 3*16+3;
        char c3f = 3*16+11;
        System.out.println(c3f);
    }

    @Test
    public void testFibonacc(){
        int ALL_SIZE = 2019;
        long[] a = new long[ALL_SIZE];
        a[0]=1;
        a[1]=1;
        for(int i=2;i<ALL_SIZE;i++){
            a[i]=a[i-1]+a[i-2];
        }
        for(int i=0;i<ALL_SIZE;i++){
            System.out.println(i+"-"+a[i]);
        }
    }
}
