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
        System.out.println("ad600da626d4b3ee191d0056705e4746f78465722f8503892bdeab93ce8e19be0bd401b80448c80a2de00dc17fcc1c99".length());
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
        TestString.PString testString = new TestString.PString("123");
        Class clazz = TestString.PString.class;
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
