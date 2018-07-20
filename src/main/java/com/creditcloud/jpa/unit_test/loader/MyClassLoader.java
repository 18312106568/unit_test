/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.loader;

/**
 *
 * @author MRB
 */
public class MyClassLoader extends ClassLoader {  
  
    public Class<?> defineMyClass( byte[] b, int off, int len)   
    {  
        return super.defineClass(b, off, len);  
    }  
      
} 
