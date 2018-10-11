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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
 
import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
 
public class SSLServer extends Thread {
    private Socket socket;
 
    public SSLServer(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            String data = reader.readLine();
            System.out.println(data);
            writer.println(data);
            writer.close();
            socket.close();
        } catch (IOException e) {
 
        }
    }
 
    private static String SERVER_KEY_STORE = "D:\\var\\client\\MRB123.keystore";
    private static String SERVER_KEY_STORE_PASSWORD = "123abc123abc";
 
    public static void main(String[] args) throws Exception {
        //设置信认的keystore仓库
        System.setProperty("javax.net.ssl.trustStore", SERVER_KEY_STORE);
        //ssl的上下文
        SSLContext context = SSLContext.getInstance("TLS");
        KeyStore ks = KeyStore.getInstance("jceks");
        //通过KeyStore导入服务端的keystore文件。
        ks.load(new FileInputStream(SERVER_KEY_STORE), null);
        //这里的SunX509是keystore的文件格式
        KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");
        //通过keytool生成是会指定keystore的密码
        kf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
 
        context.init(kf.getKeyManagers(), null, null);
 
        ServerSocketFactory factory = context.getServerSocketFactory();
        ServerSocket _socket = factory.createServerSocket(8443);
        //客服端不需要验证服务端，如果需要验证的话，服务端需要有导入客户端的证书文件
        ((SSLServerSocket) _socket).setNeedClientAuth(false);
 
        while (true) {
            new SSLServer(_socket.accept()).start();
        }
    }
}
