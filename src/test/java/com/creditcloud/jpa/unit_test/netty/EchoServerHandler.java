/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.io.UnsupportedEncodingException;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        int len = ((ByteBuf)msg).readableBytes(); 
         byte[] content = new byte[len];
        ((ByteBuf)msg).getBytes(0,content);
        for(int i=0;i<len;i++){
            System.out.println(content[i]);
        }
        System.out.println("获取请求信息："+len+new String(content,"UTF-8"));
        //System.out.println(new String(((ByteBuf)msg).array(),"UTF-8"));
        //ctx.write(msg);
        ByteBuf resMsg = Unpooled.buffer(EchoClient.SIZE);
        byte[] strBytes = "你好啊，客户端！".getBytes();
        for (int i = 0; i < strBytes.length; i++) {
           // System.out.println("发送数据到服务端:"+strBytes[i]+"/"+strBytes.length);
            resMsg.writeByte(strBytes[i]);
        }
        ctx.write(resMsg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
