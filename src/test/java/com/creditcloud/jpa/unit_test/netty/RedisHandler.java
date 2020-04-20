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
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.redis.DefaultBulkStringRedisContent;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.AsciiString;

/**
 * Created by RoyDeng on 17/7/20.
 */
public class RedisHandler extends SimpleChannelInboundHandler<RedisMessage> { // 1

    private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

    //处理读事件
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RedisMessage redisMessage) throws Exception {
        System.out.println("requestContent:" + redisMessage);
//        ByteBuf buf = msg.content();
//        int length = buf.readableBytes();
//        byte[] content = new byte[length];
//        buf.getBytes(0, content);
//        System.out.println(new String(content));
         if(redisMessage instanceof FullBulkStringRedisMessage){
             ByteBuf byteBuf = ((FullBulkStringRedisMessage) redisMessage).content();
             int len = byteBuf.readableBytes();
             byte[] content = new byte[len];
             byteBuf.getBytes(0,content);
             for(int i=0;i<len;i++){
                 System.out.println(content[i]);
             }
            System.out.println("获取请求信息："+new String(content,"UTF-8"));
             ByteBuf resMsg = Unpooled.buffer(EchoClient.SIZE);
             byte[] strBytes = "PING".getBytes();
             for (int i = 0; i < strBytes.length; i++) {
                 resMsg.writeByte(strBytes[i]);
             }
             DefaultBulkStringRedisContent redisContent = new DefaultBulkStringRedisContent(resMsg);
             ctx.write(redisContent);
         }
    }

    //处理读完成事件
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
        ctx.flush(); // 4
    }

    //处理异常事件
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
}
