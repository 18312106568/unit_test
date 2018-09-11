/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author MRB
 */
public class NIOServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NIOServer.class);

    public static void main(String[] args) throws IOException {
        System.out.println("服务开启");
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(4701));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            if (selector.selectNow() < 0) {
                continue;
            }
            //获取注册的channel
            Set<SelectionKey> keys = selector.selectedKeys();
            //遍历所有的key
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                //如果通道上有事件发生
                if (key.isAcceptable()) {
                    //获取该通道
                    ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = acceptServerSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    LOGGER.info("Accept request from {}", socketChannel.getRemoteAddress());
                    //同时将SelectionKey标记为可读，以便读取。
                    SelectionKey readKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    //利用SelectionKey的attache功能绑定Acceptor 如果有事情，触发Acceptor
                    //Processor对象为自定义处理请求的类
                    readKey.attach(new Processor());
                } else if (key.isReadable()) {
                    Processor processor = (Processor) key.attachment();
                    processor.process(key);
                }
            }
        }
    }

    static class Processor {

        private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);
        private static final ExecutorService service = Executors.newFixedThreadPool(16);

        public void process(final SelectionKey selectionKey) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    ByteBuffer buffer = null;
                    SocketChannel socketChannel = null;
                    try {
                        buffer = ByteBuffer.allocate(1024);
                        socketChannel = (SocketChannel) selectionKey.channel();
                        int count = socketChannel.read(buffer);
                        if (count < 0) {
                            socketChannel.close();
                            selectionKey.cancel();
                            LOGGER.info("{}\t Read ended", socketChannel);
                        } else if (count == 0) {
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    LOGGER.info("{}\t Read message {}", socketChannel, new String(buffer.array()));
                }
            });
        }
    }
}
