/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.utils.ConverUtil;
import com.creditcloud.jpa.unit_test.utils.HttpUtil;
import com.creditcloud.jpa.unit_test.utils.NetConfig;
import com.creditcloud.jpa.unit_test.vo.BaseResult;
import com.creditcloud.jpa.unit_test.vo.tp.CrackVo;
import com.google.gson.Gson;
import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.util.ResourceUtils;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author MRB
 */
public class TestCookie {
    
    private static final int SIZE = 4096;  
    
    final OkHttpClient client = new OkHttpClient();
    
    final Gson gson = new Gson();
    
    @Test
    public void testStringFormat(){
    
        System.out.println(String.format("试算成功条数：%d", 10));
    }
    
    @Test
    public void testEquals(){
        Integer integer = new Integer(2018);
        Integer integer2 = new Integer(2018);
        int i = 2018;
        System.out.println(i==integer);
        System.out.println(integer==i);
        System.out.println(integer2==i);
        System.out.println(integer2==integer);
    }
    
    @Test
    public void testFile(){
        try {
             File file = ResourceUtils.getFile("classpath:area-code.txt");
             FileInputStream fis = new FileInputStream(file);
             StringBuilder codeBuilder = new StringBuilder();
             int len = 0; 
             byte[] buf =new byte[SIZE];
             while((len=fis.read(buf))!=-1){
                 System.out.println(new String(buf,0,len));  
                 codeBuilder.append(new String(buf,0,len) );
             }
             String codeAll = codeBuilder.toString();
             String[] codeArr = codeAll.split("\\|");
             Map<String,String> codeMap = new HashMap<>();
             for(String code :Arrays.asList(codeArr) ){
                 String[] codePC = code.split("-");
                 codeMap.put(codePC[0], codePC[1]);
             }
             for(Map.Entry<String,String> entity: codeMap.entrySet()){
                 System.out.println(entity.getKey()+"-"+entity.getValue());
             }
             fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testCookie(){
        try {
            Request request = new Request.Builder()
              .url("http://gamesafe.qq.com/api/punish?need_appeal=1&_=3602158526")
              .get()
              .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
              .addHeader("Accept-Encoding", "gzip, deflate")
              .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
              .addHeader("Cache-Control", "max-age=0")
              .addHeader("Connection", "keep-alive")
              .addHeader("Cookie", "pgv_pvi=40127488; RK=lc+qzOzvOv; ptcz=b784c3ba5b812d3750bda9369caaf38f753796fd1643ae81d55789b486332123; eas_sid=n115y1Z2e5Z4M3v9N8w5D571p3; pgv_pvid=8111045596; pac_uid=1_315077558; pgv_pvid_new=315077558_ecb5b9d079; luin=o0315077558; lskey=000100001055f4b747a362dad398eb26a3b13f68ac7f08b08fb43d02ff80c1c2b109549fdf582158e92e1dd7; rankv=2018020217; _ga=GA1.2.44742193.1524103725; _gid=GA1.2.1847172698.1524103725; pgv_si=s1540252672; _qpsvr_localtk=0.3938258769202392; confirmuin=0; ptisp=ctc; ptnick_3602158526=e5b08fe7a096e5a4b4; ptnick_315077558=4d5242; ETK=; ptnick_3096235163=33303936323335313633; ptnick_2073310240=e5b08fe7a096e5a4b435; ptnick_3393947095=e5b08fe7a096e5a4b437; ptnick_2444618204=e5b08fe7a096e5a4b434; pgv_info=ssid=s502279952&pgvReferrer=; ptnick_3414660853=e5b08fe7a096e5a4b433; ptnick_2627915209=e5b08fe7a096e5a4b431; ptnick_3222815597=e5b08fe7a096e5a4b43131; ptnick_3509163357=e5b08fe7a096e5a4b432; ptnick_3446440468=e5b08fe7a096e5a4b438; ptnick_3487880669=e5b08fe7a096e5a4b439; pt_login_sig=fiEY7xr1TsFcuak-UL4WQlRqbkD6ruh4dch6onjyUh5w0-alQ4xXzxP4i26VUDlo; pt_clientip=2bd3716cbcf2fcb6; pt_serverip=ea5a0ab91aad905c; pt_local_token=1903918491; uikey=33504cb9688940bb2b6f0827b8cdb1c6a4e699c175535a74a14fed998490962e; ptui_loginuin=3602158526; pt2gguin=o3602158526; uin=o3602158526; superuin=o3602158526; o_cookie=3602158526; verifysession=h01e60b43e4e74cd83e270a3e912e3be7a479908a6aaf5515600a57dca11da03ff287fc371037b414ce; ptdrvs=bMLOAH8q5wDpscjjtJTrv-vHzt7aGDdhuQvuZ6sIdow_; ptvfsession=d2cd6a599031c7ba717317331ecfcccb1db7766e80ccae7054fb0385ccf69b8da5ce079c05271d35f2de69ab11a20879d6b407f64dcdb053; dlock=8_1524213828_1_; dev_mid_sig=adf977c23bf4cf9ac0f68f6445f947ed109bb3507f98f5054d7d552d64c10e1effd9e82a3d810016; qrsig=29xidBcurA3ZgECqB1FT*H3vYU1jhnTBE6DlLJ5ZTGAV9Jls*xXbreyxH74A7GnJ; skey=@EafjEaw11; supertoken=287475000; superkey=stuUdJoqzs8rptaxjtnwvPo1OgxLK2PxCcBb5CTgUX4_; pt_recent_uins=d4f147d0705e96bc800e97cf963b929850e561ddfffab6124c0f527bfd3ca8796dfbfaacfd49cc49f2d18c754bb6631384458ae1ab184026; pt_guid_sig=d6bfd85b74daed0c0da1dfcdbfd7e1292c20d00318b0d37bf98fb3486d285f23; IED_LOG_INFO2=userUin%3D3602158526%26nickName%3D%2525E5%2525B0%25258F%2525E7%2525A0%252596%2525E5%2525A4%2525B4%26userLoginTime%3D1524213835")
              .addHeader("Host", "gamesafe.qq.com")
              .addHeader("If-None-Match", "025239ac14b4fa2b48a435e04d620767cea4af9c")
              .addHeader("Upgrade-Insecure-Requests", "1") 
              .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")      
              .build();
            Response response = client.newCall(request).execute();
            BaseResult baseResult = gson.fromJson(response.body().string(), BaseResult.class);
            System.out.println("返回结果："+ baseResult.getData().toString());
            System.out.println(  gson.fromJson(baseResult.getData().get(0).toString()  , CrackVo.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
    
    @Test
    public void testConver(){
        String url = "https://ssl.ptlogin2.qq.com/check?regmaster=&pt_tea=2&pt_vcode=1&uin=2105954553&appid=21000109&js_ver=10270&js_type=1&login_sig=vuAsTWmmA70Az8-Ugk-plwLIf8tbve0xDoBEPHPg6EsYC4*Fq-ytN40tJvLgXi8L&u1=http%3A%2F%2Fgamesafe.qq.com%2F&r=0.8029317727916006&pt_uistyle=40&pt_jstoken=915971442";
        Map<String,String> paramMap = ConverUtil.converToMap(url.substring(url.indexOf("?")+1), "\\&");
        for(Map.Entry<String,String> entry :paramMap.entrySet()){
            if(entry.getKey().equals("login_sig")){
                System.out.println("login_sig的长度"+entry.getValue().length());
            }
            System.out.println(entry.getKey()+"-"+entry.getValue());
        }
        System.out.println("5S22EjF3fvgzDQDn0a6qvHT8QxJl4*QHo*EqmvqDrtBWXFZVnrKVX*IRUMeNfgT1".length());
    }
    
    
    @Test
    public void testSSL(){
        try {
            File file = ResourceUtils.getFile("classpath:sslCert.cer");
            InputStream is = new FileInputStream(file);
            NetConfig.addCertificate(is); // 这里将证书读取出来，，放在配置中byte[]里
            OkHttpClient client = HttpUtil.createOkHttp();
            final Request request = new Request.Builder()
                .url("https://www.rongxintong.com:8443/services/YfsWebService?wsdl")
                .build();
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testSSL2(){
        try {
            /**
             * 加载证书
             */
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            List<InputStream> certificates = new ArrayList();
             File file = ResourceUtils.getFile("classpath:sslCert.cer");
            InputStream is = new FileInputStream(file);
            certificates.add(is);
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null) {
                        certificate.close();
                    }
                } catch (IOException e) {
                }
            }
            /**
             * 发送请求
             */
            OkHttpClient.Builder client = new OkHttpClient().newBuilder();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory
                    = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(
                    null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom()
            );
           client.socketFactory(sslContext.getSocketFactory());
            
            final Request request = new Request.Builder()
                .url("http://localhost:8091")
                .build();
            Response response = client.build().newCall(request).execute();
            System.out.println(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
    @Test
    public void testNewFile() throws IOException{
        File file = new File("D://tmp/cap/1.txt");
        FileWriter fw = new FileWriter(file);
        fw.write("test");
        fw.flush();
        fw.close();
    }
    
    @Test
    public void testDate(){
        System.out.println(new Date(1515997891000L));
    }
    
}
