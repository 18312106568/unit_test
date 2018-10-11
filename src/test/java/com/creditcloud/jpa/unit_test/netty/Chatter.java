/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import java.util.Scanner;

/**
 *
 * @author MRB
 */
public class Chatter implements Runnable{

    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));
    
    ChannelHandlerContext ctx;
    
    
    public Chatter(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }
    
    
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请开始输入：");
        while(scanner.hasNext()){
            ByteBuf msg = Unpooled.buffer(SIZE);
            byte[] datas = scanner.nextLine().getBytes();
            for (int i = 0; i < datas.length; i++) {
                //System.out.println("发送数据到服务端:"+strBytes[i]+"/"+strBytes.length);
                msg.writeByte(datas[i]);
            }
            ctx.writeAndFlush(msg);
        }
    }
    
}
