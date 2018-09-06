/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import java.io.IOException;
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
public class TestSession {
    
    @Test
    public void testSessionId() throws IOException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
          .url("http://fanyi.baidu.com/translate?aldtype=16047&query=&keyfrom=baidu&smartresult=dict&lang=auto2zh#auto/zh/")
          .get()
          .addHeader("connection", "keep-alive")
          .addHeader("cache-control", "no-cache")
          .addHeader("postman-token", "e6dbe034-06db-023b-d87f-9775623fea10")
          .addHeader("Cookie", "JSESSIONID=3BD36FD7EFB2EFA17A885D794B12B59F")
          .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
    
    @Test
    public void testPostSessionId() throws IOException{
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
        RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"vk\"\r\n\r\nptui_checkVC('0','!KYL','\\x00\\x00\\x00\\x00\\x7d\\x86\\x50\\xf9','7f332da9d46461733c7b36ee1cacbacfa51edebfd28804442bd4e4b9443b023720de8e37d085b180dfbc56aab66c2b2dc1e4ddb0ac700993','2')\n\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        Request request = new Request.Builder()
          .url("http://localhost:8081/encrypt/encode1")
          .get()
          .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
          .addHeader("cache-control", "no-cache")
          .addHeader("postman-token", "ae894c92-8a78-53c4-d7b3-24b3e84cbb2d")
          .build();

        Response response = client.newCall(request).execute();
    }
}
