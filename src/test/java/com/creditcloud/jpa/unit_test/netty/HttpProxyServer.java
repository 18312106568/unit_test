package com.creditcloud.jpa.unit_test.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HttpProxyServer {

    public static void main(String args[]) throws Exception {
        int port = 4702;
        System.out.println("程序开始=====>>");
        start(port);
    }

    public static void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast("httpCodec",new HttpServerCodec());
                            ch.pipeline().addLast("httpObject",new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("serverHandle",new HttpProxyServerHandle());
                        }
                    });
            ChannelFuture f = b
                    .bind(port)
                    .sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static class HttpProxyServerHandle extends ChannelInboundHandlerAdapter {

        private ChannelFuture cf;
        private String host;
        private int port;

        @Override
        public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
            if (msg instanceof FullHttpRequest) {
                FullHttpRequest request = (FullHttpRequest) msg;
                String host = request.headers().get("host");
                String[] temp = host.split(":");
                int port = 4702;
                if (temp.length > 1) {
                    port = Integer.parseInt(temp[1]);
                } else {
                    if (request.uri().indexOf("https") == 0) {
                        port = 443;
                    }
                }
                this.host = temp[0];
                this.port = port;
                if ("CONNECT".equalsIgnoreCase(request.method().name())) {//HTTPS建立代理握手
                    HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                    ctx.writeAndFlush(response);
                    ctx.pipeline().remove("httpCodec");
                    ctx.pipeline().remove("httpObject");
                    return;
                }
                //连接至目标服务器
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(ctx.channel().eventLoop()) // 注册线程池
                        .channel(ctx.channel().getClass()) // 使用NioSocketChannel来作为连接用的channel类
                        .handler(new HttpProxyInitializer(ctx.channel()));

                ChannelFuture cf = bootstrap.connect(temp[0], port);
                cf.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            future.channel().writeAndFlush(msg);
                        } else {
                            ctx.channel().close();
                        }
                    }
                });
//            ChannelFuture cf = bootstrap.connect(temp[0], port).sync();
//            cf.channel().writeAndFlush(request);
            } else { // https 只转发数据，不做处理
                if (cf == null) {
                    //连接至目标服务器
                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(ctx.channel().eventLoop()) // 复用客户端连接线程池
                            .channel(ctx.channel().getClass()) // 使用NioSocketChannel来作为连接用的channel类
                            .handler(new ChannelInitializer() {

                                @Override
                                protected void initChannel(Channel ch) throws Exception {
                                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx0, Object msg) throws Exception {
                                            ctx.channel().writeAndFlush(msg);
                                        }
                                    });
                                }
                            });
                    cf = bootstrap.connect(host, port);
                    cf.addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (future.isSuccess()) {
                                future.channel().writeAndFlush(msg);
                            } else {
                                ctx.channel().close();
                            }
                        }
                    });
                } else {
                    cf.channel().writeAndFlush(msg);
                }
            }
        }


    }

    public static class HttpProxyInitializer extends ChannelInitializer{

        private Channel clientChannel;

        public HttpProxyInitializer(Channel clientChannel) {
            this.clientChannel = clientChannel;
        }

        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(new HttpClientCodec());
            ch.pipeline().addLast(new HttpObjectAggregator(6553600));
            ch.pipeline().addLast(new HttpProxyClientHandle(clientChannel));
        }
    }


    public static class HttpProxyClientHandle extends ChannelInboundHandlerAdapter {

        private Channel clientChannel;

        public HttpProxyClientHandle(Channel clientChannel) {
            this.clientChannel = clientChannel;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            FullHttpResponse response = (FullHttpResponse) msg;
            //修改http响应体返回至客户端
            response.headers().add("test","from proxy");
            clientChannel.writeAndFlush(msg);
        }
    }
}
