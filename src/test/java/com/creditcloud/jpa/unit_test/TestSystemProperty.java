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
public class TestSystemProperty {
    
    @Test
    public void testProperty(){
        System.out.println(System.getProperty("ssl",""));
    }
    
    @Test
    public void testLibName(){
        
        SecurityManager security = System.getSecurityManager();
        security.checkLink("nio");
    }
}
