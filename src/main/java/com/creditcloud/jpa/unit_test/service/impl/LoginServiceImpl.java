/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.service.impl;

import com.creditcloud.jpa.unit_test.constant.QQConstant;
import com.creditcloud.jpa.unit_test.entity.QQLogin;
import com.creditcloud.jpa.unit_test.model.PtuiCheckVK;
import com.creditcloud.jpa.unit_test.repository.QQLoginRepository;
import com.creditcloud.jpa.unit_test.service.LoginService;
import com.creditcloud.jpa.unit_test.utils.ConverUtil;
import com.creditcloud.jpa.unit_test.utils.LoginUtil;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author MRB
 */
@Service
public class LoginServiceImpl implements LoginService{
    
    Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    
    @Autowired
    OkHttpClient client;

    @Autowired
    QQLoginRepository loginRepository;
    
    @Override
    public PtuiCheckVK isSafeLogin(String qq,String loginSig) {
        try {
            QQLogin qqLogin = loginRepository.findQQLoginByUin(qq);
            Request request = new Request.Builder()
                .url(String.format(QQConstant.QQ_LOGIN_URL, qq,loginSig))
                .get()
                .addHeader(QQConstant.HD_HOST_KEY, QQConstant.HD_HOST_VALUE)
                .addHeader(QQConstant.HD_CONNECTION_KEY, QQConstant.HD_CONNECTION_VALUE)
                .addHeader(QQConstant.HD_UPGRADE_KEY,QQConstant.HD_UPGRADE_VALUE)
                .addHeader(QQConstant.HD_AGENT_KEY,QQConstant.HD_AGENT_VALUE)
                .addHeader(QQConstant.HD_ACCEPT_KEY,QQConstant.HD_ACCEPT_VALUE)
                .addHeader(QQConstant.HD_REFER_KEY, QQConstant.HD_REFER_VALUE)
                .addHeader(QQConstant.HD_ENCODE_KEY, QQConstant.HD_ENCODE_VALUE)
                .addHeader(QQConstant.HD_LANG_KEY, QQConstant.HD_LAN_VALUE)
                .addHeader(QQConstant.HD_COOKIE_KEY,qqLogin.getCookies() )
                .build();
            Response response = client.newCall(request).execute();
            //获取返回的cookie
            List<String> cookieList = response.headers("Set-Cookie");
            String cookies = cookieList.toString().replaceAll("[\\[\\],]","");
            //System.out.println(cookies);
            Map<String,String> oCookieMap = ConverUtil.converToMap(qqLogin.getCookies(), "\\;");
            /**
             * 更新cookie
             */
            oCookieMap.put("ptui_loginuin", qq);
            oCookieMap.put("o_cookie", qq);
            oCookieMap.put("pt2gguin", qq);
            Map<String,String> aCookieMap = ConverUtil.converToMap(cookies, "\\;");
            for(Map.Entry<String,String> addEntry :aCookieMap.entrySet()){
                oCookieMap.put(addEntry.getKey(), addEntry.getValue());
            }
            
            qqLogin.setCookies(ConverUtil.converToStr(oCookieMap, ";"));
            qqLogin.setExpireTime(new Date(System.currentTimeMillis()+60*60*1000));
            //保存登陆信息
            loginRepository.save(qqLogin);
            return LoginUtil.getCheckVK(response.body().string().replace("'", ""));
        } catch (IOException e) {
        }
        return null;
    }

    @Override
    public Boolean tryLogin(String qq, String loginSig, PtuiCheckVK vk) {
        try {
            String entryptPassword = LoginUtil.getEntryptPassword(vk);
            if (StringUtils.isEmpty(entryptPassword)) {
                logger.info("==========》无法获取密码");
                return false;
            }
            QQLogin qqLogin = loginRepository.findQQLoginByUin(qq);

            String verifycode = vk.getVerifyCode().replace("'", "");
            String ptVerifySession = vk.getPVerifySession().replace("'", "");
            Request request = new Request.Builder()
                    .url("https://ssl.ptlogin2.qq.com/login?u=" + qq
                            + "&verifycode=" + verifycode
                            + "&pt_vcode_v1=0&pt_verifysession_v1=" + ptVerifySession
                            + "&p=" + entryptPassword
                            + "&pt_randsalt=2&pt_jstoken=915971442&u1=http%3A%2F%2Fgamesafe.qq.com%2F&ptredirect=1&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=2-13-1524339500088&js_ver=10270&js_type=1"
                            + "&login_sig=" + loginSig
                            + "&pt_uistyle=40&aid=21000109&daid=8&")
                    .get()
                    .addHeader(QQConstant.HD_HOST_KEY, QQConstant.HD_HOST_VALUE)
                    .addHeader(QQConstant.HD_CONNECTION_KEY, QQConstant.HD_CONNECTION_VALUE)
                    .addHeader(QQConstant.HD_UPGRADE_KEY, QQConstant.HD_UPGRADE_VALUE)
                    .addHeader(QQConstant.HD_AGENT_KEY, QQConstant.HD_AGENT_VALUE)
                    .addHeader(QQConstant.HD_REFER_KEY, QQConstant.HD_REFER_VALUE)
                    .addHeader(QQConstant.HD_ENCODE_KEY, QQConstant.HD_ENCODE_VALUE)
                    .addHeader(QQConstant.HD_LANG_KEY, QQConstant.HD_LAN_VALUE)
                    .addHeader(QQConstant.HD_COOKIE_KEY, qqLogin.getCookies())
                    .build();
            Response response = client.newCall(request).execute();
             //获取返回的cookie
            List<String> cookieList = response.headers("Set-Cookie");
            String cookies = cookieList.toString().replaceAll("[\\[\\],]","");
            //转换cookies字符串为map
            Map<String,String> oCookieMap = ConverUtil.converToMap(qqLogin.getCookies(), "\\;");
            /**
             * 更新cookie
             */
            Map<String,String> aCookieMap = ConverUtil.converToMap(cookies, "\\;");
            aCookieMap.entrySet().forEach((addEntry) -> {
                oCookieMap.put(addEntry.getKey(), addEntry.getValue());
            });
            qqLogin.setCookies(ConverUtil.converToStr(oCookieMap, ";"));
            //保存登陆信息
            loginRepository.save(qqLogin);
            logger.info("==========》返登陆结果：{}",response.body().string());
        } catch (IOException ex) {
        }
        return false;
    }
    
}
