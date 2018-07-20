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
    public void testToBinary(){
        System.out.println(Integer.toHexString(21>>2));
        System.out.println(Integer.toHexString(-21>>2));
        System.out.println(Integer.toHexString(-21>>>2));
    }
}
