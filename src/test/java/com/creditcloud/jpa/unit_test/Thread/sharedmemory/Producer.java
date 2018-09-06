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
public class Producer extends Thread {

    private String mFileName;
    private FileChannel mFileChannel;
    private MappedByteBuffer mMappedByteBuffer;

    public Producer(String fn) {
        try {
            mFileName = fn;

            // 获得一个可读写的随机存取文件对象
            RandomAccessFile ramFile = new RandomAccessFile(mFileName, "rw");
            
            // 获得相应的文件通道
            mFileChannel = ramFile.getChannel();

            
            // 取得文件的实际大小，以便映像到共享内存
            int size = (int) mFileChannel.size();
            
            System.out.println(size);

            // 获得共享内存缓冲区，该共享内存可读
            mMappedByteBuffer = mFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, size).load();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void run() {
        int i = 0;
        while (true) {
            try {
                FileLock lock = null;
                lock = mFileChannel.tryLock();
                if (lock == null) {
                    System.err.println("Producer: lock failed");
                    continue;
                }
                mMappedByteBuffer.putInt(0, ++i);
                mMappedByteBuffer.putInt(4, ++i);
                mMappedByteBuffer.putInt(8, ++i);
                System.out.println("Producer: " + mMappedByteBuffer.getInt(0) + ":" 
                        + mMappedByteBuffer.getInt(4) + ":" + mMappedByteBuffer.getInt(8));
                Thread.sleep(200);
                lock.release();
                Thread.sleep(5000);
            } catch (IOException ex) {
                System.out.print(ex);
            } catch (InterruptedException ex) {
                System.out.print(ex);
            }
        }

    }

    public static void main(String args[]) {
        Producer producer = new Producer("sharedMemory.bin");
        producer.start();
    }

}
