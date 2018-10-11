/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.netty;

/**
 *
 * @author MRB
 */
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.io.UnsupportedEncodingException;

/**
 * Handler implementation for the echo client. It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

   // private final ByteBuf firstMessage;
    
    private  Chatter chatter;

    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler() {
        chatter = null;
        //firstMessage = Unpooled.buffer(EchoClient.SIZE);
        /*
        firstMessage = Unpooled.buffer(EchoClient.SIZE);
        byte[] strBytes = "你好吗，服务器！".getBytes();
        for (int i = 0; i < strBytes.length; i++) {
            System.out.println("发送数据到服务端:"+strBytes[i]+"/"+strBytes.length);
            firstMessage.writeByte(strBytes[i]);
        }
        */
        
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        if(chatter==null){
            chatter = new Chatter(ctx);
            new Thread(chatter).start();
        }
       // ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        
        int len = ((ByteBuf)msg).readableBytes(); 
         byte[] content = new byte[len];
        ((ByteBuf)msg).getBytes(0,content);
        for(int i=0;i<len;i++){
            System.out.println(content[i]);
        }
        System.out.println("获取响应信息："+len+new String(content,"UTF-8"));
       // ctx.write(msg);
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
