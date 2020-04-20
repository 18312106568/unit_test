/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.utils;

import okhttp3.*;
import org.drools.core.util.StringUtils;

import javax.net.ssl.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author MRB
 */
public class HttpUtil {
    public static OkHttpClient createOkHttp() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 添加证书
        List<InputStream> certificates = new ArrayList<>();
        List<byte[]> certs_data = NetConfig.getCertificatesData();

        // 将字节数组转为数组输入流
        if (certs_data != null && !certs_data.isEmpty()) {
            for (byte[] bytes : certs_data) {
                certificates.add(new ByteArrayInputStream(bytes));
            }
        }


        SSLSocketFactory sslSocketFactory = getSocketFactory(certificates);
        if (sslSocketFactory != null) {
            builder.sslSocketFactory(sslSocketFactory);
        }

        return builder.build();
    }

    public static OkHttpClient createOkHttps(){
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();

        try {
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

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
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
        return client;
    }


    /**
     * 添加证书
     *
     * @param certificates
     */
    private static SSLSocketFactory getSocketFactory(List<InputStream> certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            try {
                for (int i = 0, size = certificates.size(); i < size; ) {
                    InputStream certificate = certificates.get(i);
                    String certificateAlias = Integer.toString(i++);
                    keyStore.setCertificateEntry(certificateAlias, certificateFactory
                            .generateCertificate(certificate));
                    if (certificate != null)
                        certificate.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String doGet(String url) throws IOException {
        OkHttpClient client = createOkHttps();
        Request.Builder builder = new Request.Builder().url(url).get();
        Response response = client.newCall(builder.build()).execute();
        return response.body().string();
    }

    public static String doPost(String url,String heads,String params,Boolean isZip) throws IOException {
        return doPost(url,heads,params,"application/x-www-form-urlencoded",isZip);
    }


    public static String doPost(String url,String heads,String params,String mediaTypeStr,Boolean isZip) throws IOException {

        MediaType mediaType = MediaType.parse(mediaTypeStr);
        RequestBody body = RequestBody.create(mediaType,params);
        return doPost(url,heads,params,body,isZip);

    }


    public static String doPost(String url,String heads,String params,RequestBody body,Boolean isZip) throws IOException {

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body);

        if(!StringUtils.isEmpty(heads)){
            Map<String,String> headMap = ConverUtil.converToMap(heads,"\n",":");
            if(!headMap.isEmpty()){
                for(String key : headMap.keySet()){
                    requestBuilder.addHeader(key,headMap.get(key));
                }
            }
        }
        Request request = requestBuilder.build();
        Response response = createOkHttps().newCall(request).execute();
        byte[] data = response.body().bytes();
        if(!isZip){
            return new String(data);
        }
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
        return new String(ret);

    }


}
