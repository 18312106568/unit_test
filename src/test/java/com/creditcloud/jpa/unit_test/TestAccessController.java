/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import java.io.FilePermission;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.security.Security;
import org.junit.Test;

/**
 *
 * @author MRB
 */
public class TestAccessController {
    @Test
    public void testDoPrivileae(){
        System.out.println(this.getClass().getResource("").getPath());
        String user = AccessController.doPrivileged(
          new PrivilegedAction<String>() {
            public String run() {
                Permission perm = new FilePermission(
                        this.getClass().getResource("").getPath(), "read");
                AccessController.checkPermission(perm);
                return System.getProperty("user.name");
            }
          });
        String policy_class = AccessController.doPrivileged(
            new PrivilegedAction<String>() {
            public String run() {
                return Security.getProperty("policy.provider");
            }
        });
        System.out.println(policy_class);
        System.out.println(user);
    }
}
