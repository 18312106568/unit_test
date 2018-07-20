package com.creditcloud.jpa.unit_test;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class httpServer {

    public static void main(String args[]) {

        try {

            ServerSocket server = new ServerSocket(4700);
            System.out.println(new String("程序运行开始".getBytes(),"GBK"));
            //创建一个ServerSocket在端口4700监听客户请求
            while (true) {
                Socket socket = server.accept();
                System.out.println("有请求接入:"+socket.getRemoteSocketAddress());
                BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter os = new PrintWriter(socket.getOutputStream());
                os.println("HTTP/1.1 200 OK");
                os.println("content-type: text/plain; charset=UTF-8");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String line = null;
                        int count =0;
                        int c;
                        try {
                            while ((c=is.read())!=-1) {
//                                if (StringUtils.isEmpty(line)) {
//                                    break;
//                                }
                                System.out.print((char)c);
                            }
                        } catch (IOException ex) {
                            log.error("io数据读取异常");
                        }
                        try {
                            os.println("HTTP/1.1 200 OK");
                            os.println("content-type: text/plain; charset=UTF-8");
                            os.close(); //关闭Socket输出流
                            is.close(); //关闭Socket输入流
                            socket.close(); //关闭Socket
                        } catch (IOException ex) {
                            log.error("socket会话中断异常");
                        }
                    }
                }).start();
            }
        } catch (Exception e) {

            System.out.println("Error:" + e);

            //出错，打印出错信息
        }

    }

}
