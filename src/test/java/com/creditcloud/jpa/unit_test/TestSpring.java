/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.utils.SpringUtil;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author MRB
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestApplication.class)
public class TestSpring {
    
    @Autowired
    ApplicationContext context;
    
    @Autowired
    JpaProperties jpaProperties;
    
    @Test
    public void testBeanFactory(){
        Gson gson = context.getBean(Gson.class);
        System.out.println(jpaProperties.getClass());
        System.out.println(gson.toJson(jpaProperties));
    }
}
