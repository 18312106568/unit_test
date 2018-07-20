/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.proxy.dynamic.CustomInvocationHandler;
import com.creditcloud.jpa.unit_test.proxy.dynamic.HelloWorld;
import com.creditcloud.jpa.unit_test.proxy.dynamic.HelloWorldImpl;
import java.lang.reflect.Proxy;
import org.junit.Test;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 *
 * @author MRB
 */
public class TestProxy {
    
    @Test
    public  void testDynamic() {  
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");  
        HelloWorld proxy = (HelloWorld)new CustomInvocationHandler().getInstance(HelloWorld.class);
       
        proxy.sayHello("Mikan");  
        System.out.println(RepositoryFactoryBeanSupport.class);
        System.out.println(SimpleJpaRepository.class);
        System.out.println(Repository.class);
    } 
    
}
