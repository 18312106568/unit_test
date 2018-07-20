/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author MRB
 */
@Slf4j
public class CustomInvocationHandler implements InvocationHandler {  
    private Object target;  
    
     public Object getInstance(Class<?> cls){     
        Object newProxyInstance = Proxy.newProxyInstance(  
                cls.getClassLoader(),  
                new Class[] { cls }, 
                this); 
        return (Object)newProxyInstance;
    }
  
    @Override  
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {  
         if (Object.class.equals(method.getDeclaringClass())) {  
            try {  
                return method.invoke(this, args);  
            } catch (Exception t) {  
                log.error("");
            }  
        //如果传进来的是一个接口（核心)
        } else {  
            return run(method, args);  
        }  
        return null;
    }  
    
    /**
     * 实现接口的核心方法 
     * @param method
     * @param args
     * @return
     */
    public Object run(Method method,Object[] args){  
        //TODO         
        //如远程http调用
        //如远程方法调用（rmi)
        //....
        System.out.println("执行代理方法");
       return "method call success!";
    }  
}  
