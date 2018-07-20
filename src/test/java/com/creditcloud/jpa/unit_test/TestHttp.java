package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.driver.ExproderDriver;
import com.creditcloud.jpa.unit_test.model.PtuiCheckVK;
import com.creditcloud.jpa.unit_test.utils.LoginUtil;
import com.creditcloud.jpa.unit_test.vo.ResponseForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.util.ResourceUtils;

public class TestHttp {

    @Test
    public void testLogin() throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://")
                .post(null)
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "1bd3d9fb-c3e0-6083-08e1-9fb4941d81b6")
                .build();

        Response response = client.newCall(request).execute();
        //http://gamesafe.qq.com/
    }

    @Test
    public void testLoginPage() throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://gamesafe.qq.com/")
                .get()
                .addHeader("Host", "gamesafe.qq.com")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cache-Control", " max-age=0")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Referer", "https://www.baidu.com/link?url=XNlDcRlIEuDM82bwH34bq-KCYfpNNQNppDFk_omyb3oe0Islp7lSZIWcgFWoV9y2&wd=&eqid=dbaaffe800023811000000065adc121e")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Cookie", " pgv_pvi=1345968128; pac_uid=0_59954353aa931; RK=eU+qjsz/aP; ptcz=fa8c0087d2eb2e7a4c2ce82b0ed6820492c2382c7fb0063fb47ac13a83d79ef7; pgv_pvid=3025145152; eas_sid=E1x5j250c048U224M8L1g259P1; ts_uid=8285669824; ts_refer=aq.qq.com/cn2/unionverify/pc/pc_uv_show; tvfe_boss_uuid=dc72a0023dfc59dd; _ga=GA1.2.199907167.1524067202; ptui_loginuin=2105954553; o_cookie=2105954553; pt2gguin=o2105954553; ied_rf=www.baidu.com/link; pgv_info=pgvReferrer=&ssid=s2507795316")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.headers());
        
        //System.out.println(response.body().string());
    }

    @Test
    public void testIsSafeLogin() throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://ssl.ptlogin2.qq.com/check?regmaster=&pt_tea=2&pt_vcode=1"
                        + "&uin=3250537630&appid=21000109&js_ver=10270&js_type=1"
                        + "&login_sig=YWI0ZGY4Ny1iODJjLTQ1MzAtODZmZC0xYWMzZjcxZDBhN2IxNTI1MzM2MTMwMDY5"
                        + "&u1=http%3A%2F%2Fgamesafe.qq.com%2F&r=0.8029317727916006&pt_uistyle=40&pt_jstoken=915971442")
                .get()
                .addHeader("Host", "ssl.ptlogin2.qq.com")
                .addHeader("Connection", "keep-alive")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                .addHeader("Accept", "*/*;")
                .addHeader("Referer", "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?proxy_url=http://game.qq.com/comm-htdocs/milo/proxy.html&appid=21000109&target=top&s_url=http%3A%2F%2Fgamesafe.qq.com%2F&daid=8")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Cookie", " pgv_pvi=1345968128; pac_uid=0_59954353aa931; RK=eU+qjsz/aP; ptcz=fa8c0087d2eb2e7a4c2ce82b0ed6820492c2382c7fb0063fb47ac13a83d79ef7; pgv_pvid=3025145152; eas_sid=E1x5j250c048U224M8L1g259P1; ts_uid=8285669824; ts_refer=aq.qq.com/cn2/unionverify/pc/pc_uv_show; tvfe_boss_uuid=dc72a0023dfc59dd; _ga=GA1.2.199907167.1524067202;  ied_rf=www.baidu.com/link; pgv_info=pgvReferrer=&ssid=s2507795316")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.headers());
        List<String> cookies = response.headers("Set-Cookie");
        System.out.println(cookies);
        System.out.println(response.body().string());
    }

    @Test
    public void testTryLogin() throws Exception {
        OkHttpClient client = new OkHttpClient();
        String uin = "3250537630";
        String loginSig = "YWI0ZGY4Ny1iODJjLTQ1MzAtODZmZC0xYWMzZjcxZDBhN2IxNTI1MzM2MTMwMDY5";
        String verifycode = "!TMA";
        String ptVerifySession = "f4b021442528007ec70e6e0fc4f5731ec2c7e60c5aea5638959b54b528ee652b0652df77af8cde4105d6482467d4480ad05eab8190aea217";
        String entryPassword = "UVFJU6YEDB3qdNWuGCqqQuBHky7uz2Z8hVsD9AbsvBdMe8K7QCSsq*OImxaBTgwL2AsmDhTCKmW6yYhwtWVlfRw8Q439fFSkWpfRvG4tdKZx7Q*qTyc7bPCSD15yoq6-WXcQ91WZNWbjh7PcT20d7c6u6lBqb9GEh2OPgHvNaZMYgL17upRBlXuICvM-OxysUAKyQukVnJG9ZbuNAuyDSc-VmbY4eTQ0LLI5WSwcP8n1kDw2AB8S8oHeqjgCD4HjAMnDo5ebhJxPRCmT1k-dGCIdFLpE639n25zdl0*4YnmZBrzdF30A9Nnlb5nR0pAhISAozKFYPLbK0LMy8v-VAQ__";
        String addHeader = "confirmuin=0;Path=/;Domain=ptlogin2.qq.com;ptdrvs=FVPDrwZaT2TE17XENxjR*sWkEyJqZWe3VoKL8wBayeY_;Path=/;Domain=ptlogin2.qq.com;ptvfsession=f4b021442528007ec70e6e0fc4f5731ec2c7e60c5aea5638959b54b528ee652b0652df77af8cde4105d6482467d4480ad05eab8190aea217;Path=/;Domain=ptlogin2.qq.com;ptisp=ctc;Path=/;Domain=qq.com;";
        Request request = new Request.Builder()
                .url("https://ssl.ptlogin2.qq.com/login?u="+uin
                        +"&verifycode=" + verifycode 
                        + "&pt_vcode_v1=0&pt_verifysession_v1=" + ptVerifySession
                        + "&p=" + entryPassword 
                        + "&pt_randsalt=2&pt_jstoken=915971442&u1=http%3A%2F%2Fgamesafe.qq.com%2F&ptredirect=1&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=2-13-1524339500088&js_ver=10270&js_type=1"
                        + "&login_sig="+loginSig
                        +"&pt_uistyle=40&aid=21000109&daid=8&")
                .get()
                .addHeader("Host", "ssl.ptlogin2.qq.com  ")
                .addHeader("Connection", "keep-alive")
                .addHeader("User-Agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                .addHeader("Accept", "*/*;")
                .addHeader("Referer", "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?proxy_url=http://game.qq.com/comm-htdocs/milo/proxy.html&appid=21000109&target=top&s_url=http%3A%2F%2Fgamesafe.qq.com%2F&daid=8")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Cookie", "pgv_pvi=1345968128; pac_uid=0_59954353aa931; RK=eU+qjsz/aP; ptcz=fa8c0087d2eb2e7a4c2ce82b0ed6820492c2382c7fb0063fb47ac13a83d79ef7; pgv_pvid=3025145152; eas_sid=E1x5j250c048U224M8L1g259P1; ts_uid=8285669824; ts_refer=aq.qq.com/cn2/unionverify/pc/pc_uv_show; tvfe_boss_uuid=dc72a0023dfc59dd; _ga=GA1.2.199907167.1524067202; ptui_loginuin=3250537630; o_cookie=3250537630; pt2gguin=3250537630; ied_rf=www.baidu.com/link; pgv_info=pgvReferrer=&ssid=s2507795316;" + addHeader)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.headers());
        System.out.println(response.body().string());
    }

    @Test
    public void testSplitHead() {
        String cookieHead = "pgv_pvi=1345968128; pac_uid=0_59954353aa931; RK=eU+qjsz/aP; ptcz=fa8c0087d2eb2e7a4c2ce82b0ed6820492c2382c7fb0063fb47ac13a83d79ef7; pgv_pvid=3025145152; eas_sid=E1x5j250c048U224M8L1g259P1; ts_uid=8285669824; ts_refer=aq.qq.com/cn2/unionverify/pc/pc_uv_show; tvfe_boss_uuid=dc72a0023dfc59dd; _ga=GA1.2.199907167.1524067202; ptui_loginuin=2105954553; o_cookie=2105954553; pt2gguin=o2105954553; ied_rf=www.baidu.com/link; pgv_info=pgvReferrer=&ssid=s2507795316;";
        String[] cookieArr = cookieHead.split(";");
        Map<String, String> cookieMap = new HashMap();
        for (String cookie : cookieArr) {
            String[] cookieKeyValue = cookie.split("=");
            cookieMap.put(cookieKeyValue[0], cookieKeyValue[1]);
            System.out.println(cookieKeyValue[0] + "=" + cookieKeyValue[1]);
        }
        StringBuilder cookieBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
            cookieBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
        }
        System.out.println(cookieBuilder.toString());
    }

    public static long getUnsignedIntt(int data) { //将int数据转换为0~4294967295 (0xFFFFFFFF即DWORD)。 
        byte arr[] = intToByteArray(data);
        for (int i = arr.length - 1; i >= 0; i--) {
            System.out.print(arr[i]);
        }
        System.out.println();
        return data & 0x0FFFFFFF;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{
            (byte) ((a >> 24) & 0xFF),
            (byte) ((a >> 16) & 0xFF),
            (byte) ((a >> 8) & 0xFF),
            (byte) (a & 0xFF)
        };
    }

    @Test
    public void testBase64() {
        //System.out.println(Base64.decode("123"));
        System.out.println(Integer.MAX_VALUE + 1);
        for (int i = 0; i < 10; i++) {
            System.out.println((int) (4294967295l * Math.random()));
        }
        System.out.println(Long.toBinaryString((int) -1));
        System.out.println(Integer.toHexString(40));
        System.out.println(Base64.encode("123".getBytes()));
        int i = -1;
        System.out.println(getUnsignedIntt(i));

        byte b = Byte.MIN_VALUE;
        short s = b;
        s &= 0xff;
        System.out.println(s);

        short si = -1;
        int i1 = si;
        i1 &= 0xffff;
        System.out.println(i1);

        int i2 = -1;
        long l1 = i2;
        l1 &= 0xffffffff;
        System.out.println(l1);

        System.out.println(-1 & 0xffff);
        long l = -1 & 0x0FFFFFFFF;

        //   System.out.println(String.format("长整形%l",-1&0x0FFFFFFFF));
        //   System.out.println(getUnsignedIntt(-1&0x0FFFFFFFF));
    }

    @Test
    public void testJs() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);

        File jsFileName = ResourceUtils.getFile("classpath:encry.js");   // 读取js文件   
        System.out.println(jsFileName.isFile() + "-" + jsFileName.exists());
        // 执行指定脚本
        try (FileReader reader = new FileReader(jsFileName)) {
            engine.eval("print (btoa(123));");
            engine.eval(reader);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;    // 调用merge方法，并传入两个参数
                String s = (String) invoke.invokeFunction("$.Encryption.getEncryption", "123abc123abc", null, "!FOR", null);
                System.out.println("s = " + s);
            }
        }

    }

    @Test
    public void testHtmlUnit() {
        try {
            final WebClient webClient = new WebClient(BrowserVersion.CHROME);

            final HtmlPage page = webClient.getPage("http://localhost:8081/encrypt/encode1");

            final String pageText = page.getBody().asText();
            webClient.close();
            //$.Encryption.getEncryption('123abc123abc', undefined,'!SZH', undefined).length+   
           // System.out.println(new String(pageText.getBytes("ISO-8859-1"), "UTF-8"));
           System.out.println(pageText);
           System.out.println(new String(Base64.decode(pageText)));
           System.out.println(new String(pageText.getBytes("Unicode")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSplit() {
        String strArr[] = "B0279216-37FC-4A1B-B9BF-09455C35A145^7E40E9AA-CA69-40E4-979E-A1536C6892FB".split("\\^");
        for (String str : strArr) {
            System.out.println(str);
        }
        String requestUrl = "https://ssl.ptlogin2.qq.com/login?u=2105954553&verifycode=@Gzu&pt_vcode_v1=1&pt_verifysession_v1=t02LvU1fARGpgtvxwC1gWJMQLtL523veCwTi5BDy-89afYHv3jgqCXOtGHreyu2ispD5aMYH3RTIkT-PODxWTTQrmtSGzn0RXkhlIWAtZEFX_hf1F6dQFqpRw**&p=FoM64h2XSNTCGrth0Waox-93rvC60gsTdAFWHgNmLiykoXi0qHXAd2smAQahCanTm7TKNL53mP952KBnfzQwthzwe2hN496MHYu7EoW-iMt3WI-JI4RpuWNh*ERx6jGQFyql1-8J5JMCt2P3XiX0*EbdU15yFEFdo9JoYu4vNY-ISyF3jRB804jjqgEbHD5v2kMcrVbH0FdcD0gFtjoDaqGAfMfGHXPwdZed8uOG4Z*oCNz80n7DixuFp80CVESOrR0pqObXc5l0vOFYdEYB7rBAg5h45a94atuQhE90R7Q81kkP-uLH5lOwgxK98QOxbPb0hg1R96a3UPGBh2dbYA__&pt_randsalt=2&pt_jstoken=3308169468&u1=https%3A%2F%2Fgraph.qq.com%2Foauth2.0%2Flogin_jump&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=4-12-1524800053086&js_ver=10270&js_type=1&login_sig=5S22EjF3fvgzDQDn0a6qvHT8QxJl4*QHo*EqmvqDrtBWXFZVnrKVX*IRUMeNfgT1&pt_uistyle=40&aid=716027609&daid=383&pt_3rd_aid=101172721&has_onekey=1&";
        System.out.println(requestUrl.indexOf("?"));
        String params = requestUrl.substring(requestUrl.indexOf("?") + 1, requestUrl.length());
        String[] paramArr = params.split("&");
        Map<String, String> paramMap = new HashMap<String, String>();
        for (int i = 0; i < paramArr.length; i++) {
            String[] paramEntity = paramArr[i].split("\\=");
            paramMap.put(paramEntity[0], paramEntity[1]);
        }
        for (Map.Entry<String, String> entity : paramMap.entrySet()) {
            System.out.println();
        }
        //System.out.println(requestUrl.substring(requestUrl.indexOf("?")+1,requestUrl.length()));
    }


    @Test
    public void testChrome1() {
        System.setProperty("webdriver.chrome.driver", 
                "C:\\Program Files\\internet explorer\\IEDriverServer.exe");
        WebDriver driver = new FirefoxDriver();; //新建一个WebDriver 的对象，但是new 的是FirefoxDriver的驱动
        driver.get("http://www.baidu.com");//打开指定的网站
        System.out.println( driver.getCurrentUrl());
        try {
           
            /**
             * WebDriver自带了一个智能等待的方法。
             * dr.manage().timeouts().implicitlyWait(arg0, arg1）；
             * Arg0：等待的时间长度，int 类型 ； Arg1：等待时间的单位 TimeUnit.SECONDS 一般用秒作为单位。
             */
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * dr.quit()和dr.close()都可以退出浏览器,简单的说一下两者的区别：第一个close，
         * 如果打开了多个页面是关不干净的，它只关闭当前的一个页面。第二个quit，
         * 是退出了所有Webdriver所有的窗口，退的非常干净，所以推荐使用quit最为一个case退出的方法。
         */
        driver.quit();//退出浏览器
    }
    
    
    @Test
    public void testSeleniumEncrypt() throws UnsupportedEncodingException{
        String res = "ptui_checkVC('0','!TMA','\\x00\\x00\\x00\\x00\\xc1\\xbf\\x44\\x9e','f4b021442528007ec70e6e0fc4f5731ec2c7e60c5aea5638959b54b528ee652b0652df77af8cde4105d6482467d4480ad05eab8190aea217','2')";
        WebDriver driver = ExproderDriver.getInstance();
        //输入要访问的网页地址
        driver.get("http://localhost:8081/encrypt/encode?vk=" +
                URLEncoder.encode(new Gson().toJson(LoginUtil.getCheckVK(res)), "utf-8"));
        final String encryption = driver.findElement(By.tagName("body")).getText();
       // driver.get("http://localhost:8081/encrypt/encode1" );
        //final String encrypt1 = driver.findElement(By.tagName("body")).getText();
        //关闭浏览器
        //driver.quit();
        System.out.println("=====>"+encryption);
        
    }
    
    @Test 
    public void testClass() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
       Class<PtuiCheckVK> clazz = PtuiCheckVK.class;
       PtuiCheckVK vk = clazz.newInstance();
       Field[] fields = clazz.getDeclaredFields();
       for(Field field : fields){
           System.out.println(String.class.equals(field.getType())+field.getType().getName()+":"+field.getName());
           Method method = clazz.getDeclaredMethod("set"+upperCase(field.getName()),field.getType());
           method.invoke(vk, "123");
       }
       System.out.println(vk);
//       Method[] methods = clazz.getDeclaredMethods();
//       for(Method method : methods){
//           System.out.println(method.getName());
//       }
    }
    
    @Test
    public void testChangeCameToHung(){
        System.out.println(changeCameToHung("cameString"));
    }
    
    /**
     * 首字母大写
     * @param str
     * @return 
     */
    public String upperCase(String str) {  
        return str.substring(0, 1).toUpperCase() + str.substring(1);  
    }  
    
    /**
     * 峰驼式转匈牙利命名
     * 
     * @param cameString
     * @return 
     */
    public String changeCameToHung(String cameString){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<cameString.length();i++){
            int c = cameString.charAt(i);
            if (c>='A'&&c<='Z') {
                sb.append("_").append((char)(c+32));
                continue;
            }
            sb.append((char)c);
        }
        return sb.toString();
    }
    
    @Test
    public void testDoLogin(){
        
    }
    
    @Test
    public void testGetDeclarations() throws FileNotFoundException, IOException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long dayTimestamp = 60*60*24*1000;
        long now = System.currentTimeMillis();
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        File file = ResourceUtils.getFile("classpath:YHX000.txt");
        BufferedReader bufReader = new BufferedReader(new FileReader(file));
        String orgCode ; 
        while ((orgCode=bufReader.readLine())!=null) {
           ObjectMapper mapper = new ObjectMapper();
           for(int i=0;i<30;i++){
                long searchTimestamp = now - dayTimestamp*i*15;
                String start = sdf.format(new Date(searchTimestamp));
                String end = sdf.format(new Date(searchTimestamp+dayTimestamp*15));
                System.out.println("查询"+orgCode+"时间："+start+"-"+end);
                RequestBody body = RequestBody.create(mediaType, 
                        String.format("{\n\t\"CustomsTenCode\":\"%s\",\n\t\"StartTime\":\"%s\",\n\t\"EndTime\":\"%s\"\n}",orgCode, start,end));
                Request request = new Request.Builder()
                  .url("https://test.21eline.com/apidae/Apimeter/Declaration")
                  .post(body)
                  .addHeader("content-type", "application/json")
                  .addHeader("cache-control", "no-cache")
                  .addHeader("postman-token", "baeab8ef-cb47-f080-e6c9-beca36a96ca5")
                  .build();

                try {
                    Response response = client.newCall(request).execute();
                    String responseBody = response.body().string();
                    int lBracket = responseBody.indexOf("[");
                    if(responseBody.charAt(lBracket+1)!=']'){
                        ResponseForm form = mapper.readValue(responseBody,ResponseForm.class );
                        System.out.println(form);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TestHttp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    
    @Test
    public void testSvn(){
        System.setProperty ("jsse.enableSNIExtension", "false");
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
//        try {
//            /**
//             * 加载证书
//             */
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keyStore.load(null);
//            int index = 0;
//            List<InputStream> certificates = new ArrayList();
//            File file1 = ResourceUtils.getFile("classpath:efushui.cer");
//            File file2 = ResourceUtils.getFile("classpath:svn-efushui.cer");
//            // System.out.println(file.isFile());
//            InputStream is = new FileInputStream(file1);
//            certificates.add(is);
//            InputStream is2 = new FileInputStream(file2);
//            certificates.add(is2);
//            for (InputStream certificate : certificates) {
//                String certificateAlias = Integer.toString(index++);
//                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
//
//                try {
//                    if (certificate != null) {
//                        certificate.close();
//                    }
//                } catch (IOException e) {
//                }
//            }
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            TrustManagerFactory trustManagerFactory
//                    = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
//            sslContext.init(
//                    null,
//                    trustManagerFactory.getTrustManagers(),
//                    new SecureRandom()
//            );
//              clientBuilder.socketFactory(sslContext.getSocketFactory());   
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
       try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        
 
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                        }

                       
                    }
            };
 
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
 
           
            clientBuilder.sslSocketFactory(sslSocketFactory);
            clientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        OkHttpClient client = clientBuilder.build();
        Request request = new Request.Builder()
                .url("https://svn.efushui.com/repos/yimi/branches/")
                .get()
                .addHeader("Host", "svn.efushui.com")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cache-Control", "max-age=0")
                .addHeader("Authorization", "Basic cm9uZ21pbmcuYmFpOjEyM2FiYzEyM2FiYw==")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Cookie", "Hm_lvt_7c6a8b4ded8feff1539685045fdc4e77=1526866098,1527064588,1527128528,15284434681")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("If-None-Match", "W/\"2411//branches-gzip\"")
                .addHeader("If-Modified-Since", "Wed, 04 Jul 2018 02:26:06 GMT")
                .build();
        String result = null;
        try {
            Response response = client.newCall(request).execute();
            result = new String(response.body().string().getBytes(),"UTF-8");
            System.out.println(result);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }
}
