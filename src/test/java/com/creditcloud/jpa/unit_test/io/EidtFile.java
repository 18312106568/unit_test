/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.io;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author MRB
 */
public class EidtFile {
    
    public static void readNIO(String path,String path2) throws FileNotFoundException, IOException{
        RandomAccessFile ramFile = new RandomAccessFile(path, "rw");

        PrintWriter writeFile = new PrintWriter(new FileOutputStream(path2));
        String line = null;
        while((line=ramFile.readLine())!=null){
            
            writeFile.println(line.substring(2));
            System.out.println(line);
        }
        writeFile.flush();
        writeFile.close();
    }
    
    @Test
    public void editFile() throws IOException{
        readNIO("D:\\var\\2.txt","D:\\var\\3.txt");
    }


    @Test
    public void testRandomAccessFile() throws IOException {
        RandomAccessFile ramFile = new RandomAccessFile("D:\\var\\2.txt", "r");
        FileChannel channel = ramFile.getChannel();
        //channel.position(9);
        ByteBuffer buf = ByteBuffer.allocate(64);
        int bytesRead = channel.read(buf,10);
        System.out.println(bytesRead);
        byte[] data = new byte[64];
        if (bytesRead!=-1){
            buf.flip();
            buf.get(data,0,bytesRead);
            System.out.println(new String(data));
        }
    }
}
