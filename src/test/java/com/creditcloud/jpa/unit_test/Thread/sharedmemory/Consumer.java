/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread.sharedmemory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 *
 * @author MRB
 */
public class Consumer extends Thread {

    private String mFileName;
    private FileChannel mFileChannel;
    private MappedByteBuffer mMappedByteBuffer;

    public Consumer(String fn) {
        try {
            mFileName = fn;

            // 获得一个可读写的随机存取文件对象
            RandomAccessFile RAFile = new RandomAccessFile(mFileName, "r");

            // 获得相应的文件通道
            mFileChannel = RAFile.getChannel();

            // 取得文件的实际大小，以便映像到共享内存
            int size = (int) mFileChannel.size();

            // 获得共享内存缓冲区，该共享内存可读
            mMappedByteBuffer = mFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(300);
                FileLock lock = null;
                lock = mFileChannel.tryLock(0, 10, true);
                if (lock == null) {
                    System.err.println("Consumer: lock failed");
                    continue;
                }
                Thread.sleep(200);
                System.out.println("Consumer: " + mMappedByteBuffer.getInt(0) 
                        + ":" + mMappedByteBuffer.getInt(4) + ":" + mMappedByteBuffer.getInt(8));
                lock.release();
            } catch (IOException ex) {
                System.out.print(ex);
            } catch (InterruptedException ex) {
                System.out.print(ex);
            }
        }
    }

    public static void main(String args[]) {
        Consumer consumer = new Consumer("sharedMemory.bin");
        consumer.start();
    }
}
