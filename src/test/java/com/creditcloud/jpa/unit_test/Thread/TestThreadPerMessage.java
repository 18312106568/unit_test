package com.creditcloud.jpa.unit_test.Thread;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TestThreadPerMessage {
    public static void main(String args[]) throws IOException {
        final ServerSocketChannel ssc =
                ServerSocketChannel.open().bind(
                        new InetSocketAddress(8321));
        // 处理请求
        try{
            while (true) {
                // 接收请求
                final SocketChannel sc =
                        ssc.accept();
//                Fiber.schedule(()->{
//                    try {
//                        // 读 Socket
//                        ByteBuffer rb = ByteBuffer
//                                .allocateDirect(1024);
//                        sc.read(rb);
//                        // 模拟处理请求
//                        LockSupport.parkNanos(2000*1000000);
//                        // 写 Socket
//                        ByteBuffer wb =
//                                (ByteBuffer)rb.flip();
//                        sc.write(wb);
//                        // 关闭 Socket
//                        sc.close();
//                    } catch(Exception e){
//                        throw new UncheckedIOException((IOException) e);
//                    }
//                });
            }//while
        }finally{
            ssc.close();
        }
    }
}
