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
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author MRB
 */
@Slf4j
@Controller
@RequestMapping("/encrypt")
public class EncryptController {
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    
    @RequestMapping("/encode")
    public String encode(Map<String, Object> map,@RequestParam("vk") String vk) throws UnsupportedEncodingException{
        PtuiCheckVK pcvk = new Gson().fromJson(URLDecoder.decode(vk, "UTF-8"), PtuiCheckVK.class);
        System.out.println(pcvk);
        map.put("pcvk", pcvk);
        return "encrypt";
    } 
    
    @RequestMapping("/encode1")
    public String encode1(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        //获取servlet的context
        ServletContext context = request.getServletContext();
        
        //获取requestDispacher
        RequestDispatcher dispatcher = request.getRequestDispatcher("encrypt_1");
        
        HttpSession session = request.getSession();
        System.out.println("获取用户session");
        log.info(session.toString()+"-唯一ID:"+session.getId()
                +"-创建时间:"+sdf.format(session.getCreationTime())
                +"-最后访问时间:"+sdf.format(session.getLastAccessedTime())
                +"-最长不活跃间隔（s）:"+session.getMaxInactiveInterval());
        return "encrypt_1";
    } 
}
