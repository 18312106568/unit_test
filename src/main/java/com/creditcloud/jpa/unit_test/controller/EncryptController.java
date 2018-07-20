/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.controller;

import com.creditcloud.jpa.unit_test.model.PtuiCheckVK;
import com.creditcloud.jpa.unit_test.utils.LoginUtil;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MRB
 */
@Controller
@RequestMapping("/encrypt")
public class EncryptController {
    @RequestMapping("/encode")
    public String encode(Map<String, Object> map,@RequestParam("vk") String vk) throws UnsupportedEncodingException{
        PtuiCheckVK pcvk = new Gson().fromJson(URLDecoder.decode(vk, "UTF-8"), PtuiCheckVK.class);
        System.out.println(pcvk);
        map.put("pcvk", pcvk);
        return "encrypt";
    } 
    
    @RequestMapping("/encode1")
    public String encode1(){
        return "encrypt_1";
    } 
}
