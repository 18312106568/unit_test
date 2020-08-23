package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.driver.ExproderDriver;
import com.creditcloud.jpa.unit_test.model.PtuiCheckVK;
import com.creditcloud.jpa.unit_test.utils.BDtranslator;
import com.creditcloud.jpa.unit_test.utils.HttpUtil;
import com.creditcloud.jpa.unit_test.utils.LoginUtil;
import com.creditcloud.jpa.unit_test.utils.TranslateUtils;
import com.creditcloud.jpa.unit_test.vo.ResponseForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.Data;
import okhttp3.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.*;
import javax.script.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Test
    public void studyCar(){
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        OkHttpClient client = clientBuilder.build();
        String param = "{\"values\":{\"_widget_1510412023749\":{\"data\":\"2019-04-14 星期天\",\"visible\":true},\"_widget_1510491138916\":{\"data\":\"17638581226\",\"visible\":true},\"_widget_1456732020881\":{\"data\":\"郎晓伟\",\"visible\":true}},\"appId\":\"5ae88ad4e6772129e072bc14\",\"entryId\":\"56d3f774569fe8573b02be2f\",\"formId\":\"56d3f774569fe8573b02be2f\",\"hasResult\":true,\"fx_access_token\":\"5ae88ad4e6772129e072bc15\",\"fx_access_type\":\"form_public\"}";
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,param);
        Request request = new Request.Builder()
                .url("https://www.jiandaoyun.com/_/data/create")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("x-csrf-token", "8p9PJODZ-KNnRmY1-aqxsNzhJ8i7O6U9GzfE")
                //.addHeader("accept-encoding", "gzip, deflate, br")
                .addHeader("referer", "https://www.jiandaoyun.com/f/5ae88ad4e6772129e072bc15")
                .addHeader("x-requested-with", "XMLHttpRequest")
                .addHeader("origin", "https://www.jiandaoyun.com")
                .addHeader("x-jdy-ver", "1.97.3")
                .addHeader("cookie", "Hm_lvt_48ee90f250328e7eaea0c743a4c3a339=1554866573; JDY_SID=s%3A81Ae089vxXHLMnBclNNrgnQxC_z0iKa3.7cvwfzo1vYZAX5FVkTLFNtAMRHrquPTktfnFwEqXI2E; Hm_lpvt_48ee90f250328e7eaea0c743a4c3a339=1554971769")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String result = new String(response.body().string().getBytes(),"UTF-8");
            System.out.println(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    @Test
    public void testBaiduFanYi() throws IOException {
        String heads = "accept: */*\n" +
                "accept-encoding: gzip, deflate, br\n" +
                "accept-language: zh-CN,zh;q=0.9,en;q=0.8\n" +
                "cache-control: no-cache\n" +
                "content-length: 121\n" +
                "content-type: application/x-www-form-urlencoded; charset=UTF-8\n" +
                "cookie: BAIDUID=FCB94DB885A48050416D64B2BC80D53D:FG=1; BIDUPSID=FCB94DB885A48050416D64B2BC80D53D; PSTM=1547449951; REALTIME_TRANS_SWITCH=1; FANYI_WORD_SWITCH=1; HISTORY_SWITCH=1; SOUND_SPD_SWITCH=1; SOUND_PREFER_SWITCH=1; BDUSS=Qzb3Q1SmxIMHB6N2ZCTk1-LU5qa1ZkOFd3eWNBeHJDQ0ZOUktWYURyYW1mN3BjQVFBQUFBJCQAAAAAAAAAAAEAAADmyHE50dXQobymAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKbyklym8pJcRV; Hm_lvt_64ecd82404c51e03dc91cb9e8c025574=1557297824,1557994668,1558403021,1558494919; sideAdClose=18044; BDSFRCVID=N3POJeC626vdWRr9nh45UCuIQB_vy33TH6aobt7wzt9apyV8lxbiEG0PjM8g0KubvnQHogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0P3J; H_BDCLCKID_SF=tJkD_I_hJKt3qn7I5KToh4Athxob2bbXHDo-LIvLtDncOR5JhfA-3R-e04C82tkJKHTmLlT1alvvhb3O3M7ShbKP3a7lbtTx-HTDKfQF5l8-sq0x0bOte-bQypoa0q3TLDOMahkM5h7xOKQoQlPK5JkgMx6MqpQJQeQ-5KQN3KJmhpFuD6_Wj5b3Da8s-bbfHDn0WJ32KRROKROvhjRFDp8gyxomtjDqbDnUbt_XaJPMMf5J5frGLl4B2H-eLUkqKCOaLI35Mt-BHtJw367S2-DtQttjQPTufIkja-5tbJrGOb7TyU45bU47yaOR0q4Hb6b9BJcjfU5MSlcNLTjpQT8rDPCE5bj2qRu8VCIK3J; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; H_PS_PSSID=1449_21109_29135_29237_28518_29098_29134_28836_28585_26350; Hm_lpvt_64ecd82404c51e03dc91cb9e8c025574=1559796355; yjs_js_security_passport=40671ad420456fc24920bbfacab47fc4a72ac453_1559796426_js; delPer=0; PSINO=7; locale=zh; to_lang_often=%5B%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%2C%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%5D; from_lang_often=%5B%7B%22value%22%3A%22it%22%2C%22text%22%3A%22%u610F%u5927%u5229%u8BED%22%7D%2C%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%2C%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%5D\n" +
                "origin: https://fanyi.baidu.com\n" +
                "pragma: no-cache\n" +
                "referer: https://fanyi.baidu.com/\n" +
                "user-agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36\n" +
                "x-requested-with: XMLHttpRequest";
        String url = "https://fanyi.baidu.com/v2transapi";
        String params = "from=zh&to=en&query=%E4%B8%AD%E6%96%87&transtype=realtime&simple_means_flag=3&sign=567986.805251&token=2e3030089a45be932f9060f4a8c53950";
        String result = HttpUtil.doPost(url,heads,params,true);
        Gson gson = new Gson();
        Map<String, LinkedTreeMap> resultMap = gson.fromJson(result,Map.class);
        String transDst = ((LinkedTreeMap)((List)resultMap.get("trans_result").get("data")).get(0)).get("dst").toString();
        System.out.println(transDst);
    }

    @Test
    public void testMobileRequest() throws IOException {
        String url = "http://125.78.252.156/gchatpic_new/E11FBFEE284EBDFA6C635F194ABCFE9D35F01FE7E0CB4841FCA959BDBF33069FAA461A0E49DCCB25199A281E1408D3B076F8465733718A6D9AEF54FB0F9E8AE51C1772F6A7D10B9A80073806D8570E8860536A43B891DDFB/198?vuin=315077558&term=2&cldver=8.1.0.4150&rf=naio&msgTime=1565664085&mType=picGd";
        String heads = "Cookie: ST=00015D51ECF000586E47143ED075D8691735A9067EBB7BD10A4BAB68CDA78CC05C6F65F445FC7DBDAF7FDE0E7C2D7FB2E59A4D0C3512DBCDC11903E14D70D0C59DF93BCF1A9ED71C81C02186BE3E33CCAF1CB45AD43D2EBCEE5020ABCC943870\n" +
                "Referer: http://im.qq.com/mobileqq\n" +
                "Accept-Encoding: identity\n" +
                "Range: bytes=0-\n" +
                "User-Agent: Dalvik/2.1.0 (Linux; U; Android 8.0.0; MI 6 MIUI/V10.0.2.0.OCACNFH)\n" +
                "Host: 125.78.252.156\n" +
                "Connection: Keep-Alive";
        String result = HttpUtil.doPost(url,heads,"",false);
        System.out.println(result);
    }

    @Test
    public void testHttps() throws IOException {
        String url = "https://www.baidu.com";
        String result = HttpUtil.doGet(url);
        System.out.println(result);
    }

    @Test
    public void testTranslate() throws IOException, ScriptException {
//        System.out.println(System.getProperty("file.encoding"));
//        System.out.println(URLEncoder.encode("运输计费","UTF-8"));
        System.out.println(TranslateUtils.translate("英文"));
    }

    @Test
    public void testBDTranslator(){
        System.out.println(BDtranslator.translate("你是谁"));
    }

    @Test
    public void testGateIoMarket(){
        OkHttpClient client = HttpUtil.createOkHttps();
        Request request = new Request.Builder()
                .url("https://data.gateio.co/api2/1/marketlist")
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            System.out.println(result);
            Gson gson = new Gson();
            GateIoResult<GateIoMarket> gateIoResult = gson.fromJson(result,new TypeToken<GateIoResult<GateIoMarket>>(){}.getType());
            System.out.println(gateIoResult);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    //okhttp提交文件到服务器
    @Test
    public void testPostFile() throws IOException {
//        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
//
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
//        builder.proxy(proxy);
//        //cookie管理器
//        CookieManager cookieManager = new CookieManager();
//        OkHttpClient client = builder
//                .cookieJar(new JavaNetCookieJar(cookieManager))
//                .build();



        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("multipart/form-data");
        RequestBody body = new MultipartBody.Builder("--"+ UUID.randomUUID().toString()).setType(mediaType)
                .addFormDataPart("file1","out4",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("E:/tmp/flink/out4")))
                .build();
        Request request = new Request.Builder()
                .url("http://add-uat.elecadd.com/api/upload.string?sessionID=d942789f04c30b02d64be81940c999a1")
                .method("POST", body)
                .addHeader("Content-Type","multipart/form-data; ")
                .addHeader("boundary","--518387266801040454087085 ")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    @Test
    public void testStrangeServer() throws IOException {
        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=--------------------------518387266801040454087085");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("","/E:/tmp/flink/out4",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("/E:/tmp/flink/out4")))
                .build();
        String result = HttpUtil.doPost(
                "http://add-uat.elecadd.com/api/upload.string?sessionID=518387266801040454087085",
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
                        "Accept-Encoding: gzip, deflate\n" +
                        "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8\n" +
                        "Cache-Control: max-age=0\n" +
                        "Content-Length: 2268936\n" +
                        "Content-Type: multipart/form-data; boundary=----518387266801040454087085\n" +
                        "Cookie: .User=se=qcKY7xT7iJcLYTufUXQKLiWv%2FJc0yD0j\n" +
                        "Host: add-uat.elecadd.com\n" +
                        "Origin: null\n" +
                        "Proxy-Connection: keep-alive\n" +
                        "Upgrade-Insecure-Requests: 1\n" +
                        "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36",
                "","",true);
        System.out.println(result);
    }


    @Test
    public void testRoyaleApi() throws IOException {
        String result = HttpUtil.doGet(
                "https://royaleapi.com/player/P8LPVYPUJ/cards/levels");
        System.out.println(result);
    }
    /**
     *  symbol : 币种标识
     *     name: 币种名称
     *     name_en: 英文名称
     *     name_cn: 中文名称
     *     pair: 交易对
     *     rate: 当前价格
     *     vol_a: 被兑换货币交易量
     *     vol_b: 兑换货币交易量
     *     curr_a: 被兑换货币
     *     curr_b: 兑换货币
     *     curr_suffix: 货币类型后缀
     *     rate_percent: 涨跌百分百
     *     trend: 24小时趋势 up涨 down跌
     *     supply: 币种供应量
     *     marketcap: 总市值
     *     plot: 趋势数据
     */
    @Data
    public static class GateIoMarket{
        private Integer no;
        /**
         * 币种标识
         */
        private String symbol;

        /**
         * 总市值
         */
        private String marketcap;

        /**
         * 被兑换货币
         */
        private String curr_a;

        /**
         * 兑换货币
         */
        private String curr_b;

        /**
         * 被兑换货币交易量
         */
        private Double vol_a;

        /**
         * 兑换货币交易量
         */
        private String vol_b;

        /**
         * 涨跌百分比
         */
        private String rate_percent;

        /**
         * 24小时趋势 up涨 down跌
         */
        private String trend;



        /**
         *  币种供应量
         */
        private Long supply;

        /**
         * 交易对
         */
        private String pair;

        /**
         * 货币类型后缀
         */
        private String curr_suffix;

        /**
         * 当前价格
         */
        private String rate;

        /**
         * 趋势数据
         */
        private String plot;

        private String name;

        private String name_cn;

        private String name_en;
    }

    @Data
    public static class GateIoResult<T>{
        private Boolean result;
        private List<T> data;
    }

}
