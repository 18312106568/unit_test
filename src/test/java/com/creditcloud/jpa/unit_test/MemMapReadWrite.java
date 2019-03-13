/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author MRB
 */
public class MemMapReadWrite {

    private static int len;

    /**
     * 读文件
     *
     * @param fileName
     * @return
     */
    public static ByteBuffer readFile(String fileName) {
        try {
            RandomAccessFile file = new RandomAccessFile(fileName, "rw");
            len = (int) file.length();
            FileChannel channel = file.getChannel();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, len);

            return buffer.get(new byte[len]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写文件
     *
     * @param readFileName
     * @param writeFileName
     */
    public static void writeFile(String readFileName, String writeFileName) {
        try {
            RandomAccessFile file = new RandomAccessFile(writeFileName, "rw");
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = readFile(readFileName);

            MappedByteBuffer bytebuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, len);
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < len; i++) {
                bytebuffer.put(i, buffer.get(i));
            }
            bytebuffer.flip();
            long endTime = System.currentTimeMillis();
            System.out.println("写文件耗时： " + (endTime - startTime));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void copyFile(String sourceFile,String targetFile){
        try {
            RandomAccessFile readRandomAccessFile = new RandomAccessFile(sourceFile,"rw");
            FileChannel readFileChannel = readRandomAccessFile.getChannel();

            RandomAccessFile writeRandomAccessFile = new RandomAccessFile(targetFile,"rw");
            FileChannel writeFileChannel = writeRandomAccessFile.getChannel();
            //MappedByteBuffer mappedByteBuffer = writeFileChannel.map(FileChannel.MapMode.READ_WRITE,0,readRandomAccessFile.length());

            ByteBuffer buf = ByteBuffer.allocate(1024);
            int bytesRead = readFileChannel.read(buf);
            while(bytesRead != -1){
                buf.flip();
                writeFileChannel.write(buf);
//                while(buf.hasRemaining()){
//                    mappedByteBuffer.put(buf.get());
//                }
                buf.compact();
                bytesRead = readFileChannel.read(buf);
            }
            readFileChannel.close();
            writeFileChannel.close();
            //mappedByteBuffer.flip();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        String readFileName = "D:\\program\\glassfish4.rar";
////        String writeFileName = "D:\\tmp\\1.rar";
////
////        writeFile(readFileName, writeFileName);
        String sourceFile = "E:\\note\\WMS_MSC.sql";
        String targetFile = "E:\\note\\WMS_MSC.sql.bak";
        copyFile(sourceFile,targetFile);
    }
}
