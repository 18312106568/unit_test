/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.ssl;

/**
 *
 * @author MRB
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
 
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
 
public class SSLClient {
 
    private static String CLIENT_KEY_STORE = "D:\\var\\client\\MRB123.keystore";
 
    public static void main(String[] args) throws Exception {
        // 客户端的keystore文件地址
        System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);
        // 输出日志文件，可以看出握手的过程
        System.setProperty("javax.net.debug", "ssl,handshake");
 
        SocketFactory sf = SSLSocketFactory.getDefault();
        Socket s = sf.createSocket("localhost", 8443);
 
        PrintWriter writer = new PrintWriter(s.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        writer.println("hello");
        writer.flush();
        System.out.println(reader.readLine());
        s.close();
    }
 
}

