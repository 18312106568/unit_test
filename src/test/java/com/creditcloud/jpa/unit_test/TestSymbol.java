package com.creditcloud.jpa.unit_test;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class TestSymbol {

    @Test
    public void testPrority(){
        System.out.println(5-2>2);
    }

    @Test
    public void testAccessController(){
        String javaHome = AccessController.doPrivileged(new PrivilegedAction<String>() {
            @Override
            public String run() {
                return System.getProperty("java.home");
            }
        });
        System.out.println(javaHome);
        System.out.println(System.getSecurityManager());
    }

    @Test
    public void testPrivate() throws IllegalAccessException, InvocationTargetException {
        /**
         * 测试能否访问实例的私有属性与方法
         */
        TestString testString = new TestString("123");
        Class clazz = TestString.class;
        for(Field field : clazz.getDeclaredFields()){
            if("test".equals(field.getName())){
                field.setAccessible(true);
                System.out.println(field.get(testString));
                break;
            }
            continue;
        }

        for(Method method : clazz.getDeclaredMethods()){
            if("test".equals(method.getName())){
                method.setAccessible(true);
                method.invoke(testString);
                break;
            }
            continue;
        }
    }
}
