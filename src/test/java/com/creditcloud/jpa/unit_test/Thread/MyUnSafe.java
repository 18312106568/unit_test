/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 *
 * @author MRB
 */
public class MyUnSafe {
    
    static volatile Unsafe unsafe;
    
    public static Unsafe getInstance(){
        if(unsafe==null){
            synchronized(Unsafe.class){
                if(unsafe==null){
                    unsafe = getUnsafe();
                }
            }
        }
        return unsafe;
    }
    
    private static Unsafe getUnsafe()  {
        
        Class<?> unsafeClass = Unsafe.class;

        for (Field f : unsafeClass.getDeclaredFields()) {

            if ("theUnsafe".equals(f.getName())) {

                f.setAccessible(true);
                try{
                    return (Unsafe) f.get(null);
                }catch(IllegalArgumentException|IllegalAccessException  ie){
                    return null;
                }

            }
        }
        return null;

    }
}
