/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.utils;

import com.creditcloud.jpa.unit_test.constant.WebConstant;
import com.creditcloud.jpa.unit_test.driver.ExproderDriver;
import com.creditcloud.jpa.unit_test.model.PtuiCheckVK;
import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;

/**
 *
 * @author MRB
 */
public class LoginUtil {

    /**
     * 生成login_sig
     *
     * @return
     */
    public static String getLoginSig() {
        return new String(Base64.getEncoder().encode((UUID.randomUUID().toString() + new Date().getTime())
                .substring(1).getBytes())).replaceAll("\\/", "-").replaceAll("\\+", "*").replaceAll("\\=", "_");
    }

    public static PtuiCheckVK getCheckVK(String str) {
        PtuiCheckVK vk = new PtuiCheckVK();
        String[] arr = str.substring(str.indexOf("(") + 1, str.length() - 1).split("\\,");
        if (arr.length > 4) {
            vk.setType(arr[0]);
            vk.setVerifyCode(arr[1]);
            vk.setLookCode(arr[2]);
            vk.setPVerifySession(arr[3]);
        }
        return vk;
    }

    /**
     * 密码加密
     *
     * @param vk
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getEntryptPassword(PtuiCheckVK vk) {
//        try {
//            //输入要访问的网页地址
//            WebDriver driver = ExproderDriver.getInstance();
//            driver.get(WebConstant.ENCODE_URL + "?vk=" + URLEncoder.encode(new Gson().toJson(vk), "UTF-8"));
//            final String encryption = driver.findElement(By.tagName("body")).getText();
//            return encryption;
//        } catch (UnsupportedEncodingException e) {
//        }
        try{
            WebDriver driver = ExproderDriver.getChromeInstance();
            driver.get(WebConstant.ENCODE_URL + "?vk=" + URLEncoder.encode(new Gson().toJson(vk), "UTF-8"));
            final String encryption = driver.findElement(By.tagName("body")).getText();
            return encryption;
        } catch (UnsupportedEncodingException e) {
        }
        return "";
    }
}
