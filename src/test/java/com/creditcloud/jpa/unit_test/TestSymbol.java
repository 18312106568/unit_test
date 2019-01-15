package com.creditcloud.jpa.unit_test;

import org.junit.Test;

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
}
