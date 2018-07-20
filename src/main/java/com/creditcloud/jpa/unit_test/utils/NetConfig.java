/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author MRB
 */
public class NetConfig {
    // 证书数据
    private static List<byte[]> CERTIFICATES_DATA = new ArrayList<>();

    /**
     * 添加https证书
     *
     * @param inputStream
     */
    public synchronized static void addCertificate(InputStream inputStream) {
        System.out.println("#addCertificate inputStream = " + inputStream);
        if (inputStream != null) {

            try {
                int ava = 0;// 数据当次可读长度
                int len = 0;// 数据总长度
                List<byte[]> data = new ArrayList<>();
                while ((ava = inputStream.available()) > 0) {
                    byte[] buffer = new byte[ava];
                    inputStream.read(buffer);
                    data.add(buffer);
                    len += ava;
                }

                byte[] buff = new byte[len];
                int dstPos = 0;
                for (byte[] bytes : data) {
                    int length = bytes.length;
                    System.arraycopy(bytes, 0, buff, dstPos, length);
                    dstPos += length;
                }

                CERTIFICATES_DATA.add(buff);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * https证书
     *
     * @return
     */
    public static List<byte[]> getCertificatesData() {
        return CERTIFICATES_DATA;
    }
}
