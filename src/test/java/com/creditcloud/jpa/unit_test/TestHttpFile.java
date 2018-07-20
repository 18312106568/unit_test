/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

/**
 *
 * @author MRB
 */
@Slf4j
public class TestHttpFile {

    OkHttpClient client = new OkHttpClient();

    @Test
    public void getToken() throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
        RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"client_id\"\r\n\r\ntest\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"client_secret\"\r\n\r\n022127e182a934dea7d69s10697s8ac2\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"grant_type\"\r\n\r\npassword\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"account\"\r\n\r\nefushui\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"username\"\r\n\r\nefs000\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"password\"\r\n\r\n.efs0000\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        Request request = new Request.Builder()
                .url("http://efushui.beta.easydo.cn/oc_api/api/v1/oauth2/access_token")
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "896219ae-2954-c1db-5c7f-5b859e26e297")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());

    }

    @Test
    public void uploadFile() throws IOException, InterruptedException {
        final int count = 500;
        OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
        .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
        .build();
        
        // MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
        //RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"pac.txt\"; filename=\"gfwlist.pac\"\r\nContent-Type: application/x-ns-proxy-autoconfig\r\n\r\n\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        MediaType type = MediaType.parse("application/octet-stream");//"text/xml;charset=utf-8"
        File file = new File("/tmp/gfwlist.pac");
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            final int num = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestBody fileBody = RequestBody.create(type, file);
                    Request request = new Request.Builder()
                            .url("http://efushui.beta.easydo.cn/wo_api/api/v2/content/upload?account=efushui&instance=default&uid=847093550&filename=" + num + ".txt&access_token=92cd2b17bcbafc7af49c5a6fc1d3b824")
                            .post(fileBody)
                            .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                            .addHeader("cache-control", "no-cache")
                            .addHeader("postman-token", "8f8384b0-b069-c934-5726-b6eaec30c071")
                            .build();
                    try {
                        long start = System.currentTimeMillis();
                        Response response = client.newCall(request).execute();
                        String jsonStr = response.body().string();
                        long end = System.currentTimeMillis();
                        JsonObject jsonObject = new Gson().fromJson(jsonStr, JsonObject.class);
                        System.out.println("cost:"+(end-start)+"-"+jsonObject);
                    } catch (Exception ex) {
                        log.error("upload file:{} error:{}",num,ex.getClass());
                    }
                    countDownLatch.countDown();
                }
            }).start();

        }
        countDownLatch.await();
    }

    @Test
    public void downloanFile() throws IOException {

        Request request = new Request.Builder()
                .url("http://efushui.beta.easydo.cn/wo_api/api/v1/content/download?account=efushui&instance=default&uid=183371635&filename=pac.txt&access_token=92cd2b17bcbafc7af49c5a6fc1d3b824")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        InputStream is = response.body().byteStream();
        File newFile = new File("/tmp/" + UUID.randomUUID().toString());
        FileWriter fw = new FileWriter(newFile);
        byte[] byteArr = new byte[1024];
        while (is.read(byteArr) != -1) {
            fw.write(new String(byteArr));
        }
        fw.flush();
        fw.close();

    }

}
