/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        Session session = jsch.getSession("mrb", "192.168.1.103", 22);
        session.setPassword("123abc123abc");
        session.setConfig("StrictHostKeyChecking", "no");
        session.setTimeout(600000);
        session.connect();
        
        ChannelShell shell = (ChannelShell)session.openChannel("shell");
        shell.connect();
        InputStream in = shell.getInputStream();
        OutputStream out = shell.getOutputStream();
        
        String shellCommand = "ls \n";
        out.write(shellCommand.getBytes());
        out.flush();
        
        System.out.println(in.available());
        
        if (in.available() > 0) {
                byte[] data = new byte[in.available()];
                int nLen = in.read(data);

                if (nLen < 0) {
                        System.out.println("network error.");
                }

                //转换输出结果并打印出来
                String temp = new String(data, 0, nLen,"iso8859-1");
                System.out.println(temp);
        }
        out.close();
        in.close();
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
