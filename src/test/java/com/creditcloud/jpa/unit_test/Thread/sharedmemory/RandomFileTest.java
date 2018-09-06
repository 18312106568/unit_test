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

/**
 *
 * @author MRB
 */
public class RandomFileTest {
    public static void main(String args[]) throws IOException{
       RandomAccessFile ramFile = new RandomAccessFile("sharedMemory.bin", "rw");
            
        // 获得相应的文件通道
       FileChannel mFileChannel = ramFile.getChannel();


        // 取得文件的实际大小，以便映像到共享内存
       int size = (int) mFileChannel.size();

       System.out.println(size);

        // 获得共享内存缓冲区，该共享内存可读
       MappedByteBuffer mMappedByteBuffer = mFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, size).load();
       
       
       System.out.println("File: " + mMappedByteBuffer.getInt(0) + ":" 
                        + mMappedByteBuffer.getInt(4) + ":" + mMappedByteBuffer.getInt(8));
       
       mMappedByteBuffer.flip();
       
       
    }
}
