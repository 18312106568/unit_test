package com.creditcloud.jpa.unit_test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;
import io.netty.handler.codec.redis.RedisDecoder;
import io.netty.handler.codec.redis.RedisEncoder;

/**
 * editor MRB
 */
public class RedisServer {


    public static void main(String args[]) throws Exception {
        int port = 4774;
        System.out.println("程序开始=====>>");
        start(port);
    }

    public static void start(int port) throws Exception {
        ServerBootstrap bootstap = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel)
                            throws Exception {
                        System.out.println("initChannel ch:" +channel);
                        channel.pipeline()
                                .addLast("decoder",new RedisDecoder())   // 1
                                .addLast("encoder",new RedisEncoder())  // 2
                                .addLast("aggregator", new RedisBulkStringAggregator())    // 3
                                .addLast("handler", new RedisHandler());        // 4
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) // determining the number of connections queued
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        ChannelFuture future = bootstap.bind(port);
        future.sync();
    }
}
