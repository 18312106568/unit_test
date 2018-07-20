/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.proxy.dynamic;

/**
 *
 * @author MRB
 */
public class HelloWorldImpl implements HelloWorld {  
    @Override  
    public void sayHello(String name) {  
        System.out.println("Hello " + name);  
    }  
}  
