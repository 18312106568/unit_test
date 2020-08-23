/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.utils.ConverUtil;
import com.creditcloud.jpa.unit_test.utils.DateUtils;
import com.creditcloud.jpa.unit_test.utils.HttpUtil;
import com.creditcloud.jpa.unit_test.utils.NetConfig;
import com.creditcloud.jpa.unit_test.vo.BaseResult;
import com.creditcloud.jpa.unit_test.vo.tp.CrackVo;
import com.google.gson.Gson;
import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;
import okhttp3.*;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

//import java.util.HashSet;

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
            OkHttpClient client = HttpUtil.createOkHttps();
            final Request request = new Request.Builder()
                //.url("https://www.rongxintong.com:8443/services/YfsWebService?wsdl")
                    .url("http://www.baidu.com")
                .build();
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());

            System.out.println(DateUtils.format(new Date(response.sentRequestAtMillis())));
            System.out.println(DateUtils.format(new Date(response.receivedResponseAtMillis())));
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
                .url("http://www.baidu.com")
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
        SimpleDateFormat sdf = new SimpleDateFormat("EEE,d MMM yyyy hh:mm:ss z", Locale.ENGLISH);
        System.out.println(new Date(1515997891000L));
        System.out.println(sdf.format(new Date(1515997891000L)));
    }


    public void testConnectKey(){


    }

    @Test
    public void testDefaultEventLoopThreads(){
        int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println( SystemPropertyUtil.getInt("io.netty.eventLoopThreads",0));
        System.out.println(DEFAULT_EVENT_LOOP_THREADS);
        System.out.println( Runtime.getRuntime().availableProcessors());
        AtomicInteger aint = new AtomicInteger(10);
        aint.compareAndSet(10, 11);
        System.out.println(aint);
    }

    @Test
    public void testImitateExam() throws IOException {
        Integer pid = 480;
        //String loginSign="PHPSESSID=og0n6eovq3qr8br6955gmn3hm7;cdb_auth=2316elvypnl6%2FJ913%2BXo3SSPhW0SP8PL7iAyGOCODx9apIwQskN9TJH93DsYScQ4sJguachOsvW6wC0Kvce7EaJQ4EU;uchome_loginuser=20233;cdb_sid=E80NhA;";
        String loginSign="cdb_auth=8bcfzKMk7CQgWpgqj7LSNTVTq3f0dJan%2F%2FQaOTrTjQwT%2F%2FSvtyb1gJxe9jE7zNRIf9pKAP8LjJ3hBE5uUZphcEzQJqQ; uchome_loginuser=20233; PHPSESSID=srm0ts1jp0l9j2gencps9odnm5; cdb_sid=nvL9CP";
                //"PHPSESSID=h64dluvs5ndqs1078figt1p6d0; cdb_auth=e150EeNt3D7w7ML%2B3Dzfi69AAnjFa2pizSfOoZisqnD0IgPSv%2BE7a5XNyPFt3NdVkPWfi76ymunNwaoMeZhRkWVMFb0; uchome_loginuser=20233;cdb_sid=99vq1H;";
        String url = String.format("http://173.1.1.2/train/its_exam.php?action=submit&id=%d",pid);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "subVal=%CC%E1%BD%BB&choice-1=0&choice-2=0&choice-3=1&choice-4=0&choice-5=1&radio-1=D&radio-2=E&radio-3=C&radio-4=B&radio-5=C&radio-6=C&radio-7=D&radio-8=D&radio-9=A&checkbox-1-A=A&checkbox-1-B=B&checkbox-1-C=C&checkbox-1-D=D&checkbox-2-A=A&checkbox-2-B=B&checkbox-2-C=C&checkbox-2-D=D&checkbox-2-E=E&checkbox-3-A=A&checkbox-3-B=B&checkbox-3-C=C&checkbox-3-D=D&checkbox-4-A=A&checkbox-4-B=B&checkbox-4-C=C&checkbox-4-D=D&checkbox-4-E=E&checkbox-5-C=C&checkbox-5-D=D&checkbox-6-A=A&checkbox-6-C=C&checkbox-6-D=D&checkbox-6-E=E&checkbox-7-A=A&checkbox-7-C=C&checkbox-7-E=E&checkbox-8-A=A&checkbox-8-B=B&checkbox-8-C=C&checkbox-8-D=D&checkbox-9-A=A&checkbox-9-B=B&checkbox-9-C=C&checkbox-9-E=E&checkbox-10-A=A&checkbox-10-B=B&checkbox-10-C=C&checkbox-10-D=D&checkbox-11-A=A&checkbox-11-B=B&checkbox-11-C=C&checkbox-11-D=D");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8")
                .addHeader("Referer",String.format("http://173.1.1.2/train/its_exam.php?action=enter&id=%d",pid))
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36")
                .addHeader("Cookie", loginSign)
                //"PHPSESSID=og0n6eovq3qr8br6955gmn3hm7;cdb_auth=2316elvypnl6%2FJ913%2BXo3SSPhW0SP8PL7iAyGOCODx9apIwQskN9TJH93DsYScQ4sJguachOsvW6wC0Kvce7EaJQ4EU;uchome_loginuser=20233;cdb_sid=E80NhA;"
                .build();
        Response response = client.newCall(request).execute();
        byte[] data = response.body().bytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        GZIPInputStream gzip = new GZIPInputStream(bis);
        byte[] buf = new byte[1024];
        int num = -1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((num = gzip.read(buf, 0, buf.length)) != -1) {
            bos.write(buf, 0, num);
        }
        gzip.close();
        bis.close();
        byte[] ret = bos.toByteArray();
        bos.flush();
        bos.close();
        String result = new String(new String(ret,"GB2312").getBytes(),"UTF-8");
        System.out.println(result);
    }

    @Test
    public void headToMap(){
        String requestHeads = "Accept: */*\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 135\n" +
                "Content-Type: application/x-www-form-urlencoded; charset=UTF-8\n" +
                "Cookie: BAIDUID=FCB94DB885A48050416D64B2BC80D53D:FG=1; BIDUPSID=FCB94DB885A48050416D64B2BC80D53D; PSTM=1547449951; REALTIME_TRANS_SWITCH=1; FANYI_WORD_SWITCH=1; HISTORY_SWITCH=1; SOUND_SPD_SWITCH=1; SOUND_PREFER_SWITCH=1; BDUSS=Qzb3Q1SmxIMHB6N2ZCTk1-LU5qa1ZkOFd3eWNBeHJDQ0ZOUktWYURyYW1mN3BjQVFBQUFBJCQAAAAAAAAAAAEAAADmyHE50dXQobymAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKbyklym8pJcRV; BDSFRCVID=0KKOJeC624GIAD69R9w-UCuILZ_LPOJTH6aIedtMVlBhEMemOxpDEG0PDx8g0KubzID5ogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0P3J; H_BDCLCKID_SF=tJIeoIIatI_3fP36q4QV-JIehxQfbK62aJ0jahvvWJ5TMC_mQUrNQ55-0GrfelQmbm5rhf71Lp7kShPC-frqbb0I3xvGaUQu5Cj9XPJg3l02Vhb9e-t2ynLV24rRt4RMW20e0h7mWIbUsxA45J7cM4IseboJLfT-0bc4KKJxthF0HPonHj_-e5oW3f; Hm_lvt_64ecd82404c51e03dc91cb9e8c025574=1554791761,1555381331,1555482509,1555576313; to_lang_often=%5B%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%2C%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%5D; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; H_PS_PSSID=1449_21109_28775_28724_28836_28585_26350_28603_28892; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; delPer=0; PSINO=7; locale=zh; Hm_lpvt_64ecd82404c51e03dc91cb9e8c025574=1555990862; from_lang_often=%5B%7B%22value%22%3A%22est%22%2C%22text%22%3A%22%u7231%u6C99%u5C3C%u4E9A%u8BED%22%7D%2C%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%2C%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%5D\n" +
                "Host: fanyi.baidu.com\n" +
                "Origin: https://fanyi.baidu.com\n" +
                "Referer: https://fanyi.baidu.com/\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36\n" +
                "X-Requested-With: XMLHttpRequest";
        Map<String,String> cookieMap = ConverUtil.converToMap(requestHeads,"\n",":");
        for(String key : cookieMap.keySet()){
            System.out.println(String.format("%s=%s",key,cookieMap.get(key)));
        }
    }
}
