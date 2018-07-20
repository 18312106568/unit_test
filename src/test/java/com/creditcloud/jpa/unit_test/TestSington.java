/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

/**
 *
 * @author MRB
 */
public class TestSington {
    
    public void testSington(){
        System.out.println(Sington.class);
    }
    
    public static class Sington<T>{
        private static Sington instance;
        
        public Sington getInstance(Class<T> clazz){
            if(instance==null){
                instance = new Sington();
            }
            return instance;
        }
        
    }
}
