/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.common.utils.AesEncryptUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 * @author 31507
 */
public class TestEncrypt {
    @Test
    public void testAES() throws Exception{
        String salt = "UzFlM3A1IDAyMDEwLDAgMDIwMDIxMTcgMTA6MzMgQU0=";
        String password = "qwerty";
        System.out.println(AesEncryptUtil.desEncrypt("m6wjssdiq88xbAr0kvIhJA=="));
//        System.out.println(AesEncryptUtil.encrypt(
//                AesEncryptUtil.desEncrypt("m6wjssdiq88xbAr0kvIhJA==")));
        String passphrase = getPassphrase(salt,password);
        System.out.println(passphrase);
        System.out.println(DigestUtils.sha1Hex(blendToString(salt, password)));
 //       int saltLen = "UzFlM3A1IDAyMDEwLDAgMDIwMDIxMTcgMTA6MzMgQU0=".length();
//        byte[] passphraseByte = pa
//        byte[] password = new byte[passphraseByte.length-saltLen];
//        System.arraycopy(passphraseByte, saltLen, password, 0, passphraseByte.length-saltLen);
//        System.out.println(new String(password));
    }

    
    public static byte[] encodePassphrase(String passphrase) {
        return DigestUtils.sha1(passphrase);
    }
    
    public static String getPassphrase(String salt, String userPassword) {
        return DigestUtils.sha1Hex(blend(salt.getBytes(), userPassword.getBytes()));
    }
    
    public static String blendToString(String salt, String userPassword){
        return new String(blend(salt.getBytes(), userPassword.getBytes()));
    }
     private static byte[] blend(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        int ai = 0;
        int bi = 0;
        for (int i = 0; i < result.length; i++) {
            if (ai == a.length || bi < ai && bi < b.length) {
                result[i] = b[bi++];
                continue;
            }
            if (bi == b.length || ai <= bi) {
                result[i] = a[ai++];
                continue;
            }
        }
        return result;
    }
}
