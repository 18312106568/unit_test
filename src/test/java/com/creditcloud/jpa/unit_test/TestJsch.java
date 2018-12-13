/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.PipedInputStream;  
import java.io.PipedOutputStream; 
import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 *
 * @author MRB
 */
public class TestJsch {
    
    @Test
    public void testExcuteShell() throws JSchException, IOException{
        JSch jsch = new JSch();
        Session session = jsch.getSession("root", "192.168.1.103", 22);
        session.setPassword("123abc123abc");
        session.setConfig("StrictHostKeyChecking", "no");
        session.setTimeout(600000);
        session.connect();
        
        ChannelShell channel=(ChannelShell) session.openChannel("shell");
        channel.setAgentForwarding(true);
        PipedInputStream in = new PipedInputStream(); 
        PipedOutputStream pipeOut = new PipedOutputStream( in );  
        channel.setInputStream( in );  
        channel.connect();
        OutputStream os = channel.getOutputStream();
        PrintWriter writer = new PrintWriter(os);
        writer.write("sudo -ip 123abc123abc");
        writer.write("ls");
        writer.write("exit");
        writer.flush();
        System.out.println(in.available());
        
        if (in.available() > 0) {
                byte[] data = new byte[in.available()];
                int nLen = in.read(data);

                if (nLen < 0) {
                        System.out.println("network error.");
                }

                //转换输出结果并打印出来
                String temp = new String(data, 0, nLen,"UTF-8");
                System.out.println(temp);
        }
    }
    
    @Test
    public void testExec(){
        try{
            JSch jsch = new JSch();
            Session session = jsch.getSession("mrb", "192.168.1.103", 22);
            session.setPassword("123abc123abc");
            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(600000);
            session.connect();


            ChannelExec exec = (ChannelExec)session.openChannel("exec");
            InputStream in = exec.getInputStream();
            exec.setCommand("ip addr\n");
            exec.setErrStream(System.err);
            exec.connect();
           
            String result = IOUtils.toString(in, "UTF-8");
            exec.disconnect();
            System.out.println(result);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
