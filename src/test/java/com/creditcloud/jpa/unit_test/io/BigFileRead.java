/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 *
 * @author MRB
 */
public class BigFileRead {

    public static void read(String path) throws IOException{
         FileInputStream inputStream=null;
         inputStream=new FileInputStream(path);
         byte[] bytes =new byte[32*1024*1024];
         int i=0;
         while ((i=inputStream.read(bytes))!=-1) {
           System.out.println(new String(bytes));
        }
        inputStream.close();
    }
    
    
    /**
     * Scanner读取
     * @param path
     * @throws IOException 
     */
    public static void readScanner(String path) throws IOException{
        FileInputStream inputStream=null;
        Scanner scan=null;
        try {
            inputStream=new FileInputStream(path);
            scan=new Scanner(inputStream, "UTF-8");
            while(scan.hasNext()){
                String line=scan.nextLine();
                System.out.println(line);
            }
//            if(scan!=null)
//                throw scan.ioException();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (scan != null) {
                scan.close();
            }
        }
    }
    /**
     * apache common io 读取
     * @param path
     */
    public static void readApacheCommon(String path){
        LineIterator it=null;
        try {
            it = FileUtils.lineIterator(new File(path),Charsets.UTF_8.name());
            while(it.hasNext()){
                 String line = it.nextLine();
                 System.out.println(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            LineIterator.closeQuietly(it);
        }
    }

    /**
     * buffer读取
     */

    public static void readBuffer(String path){
        File file=new File(path);
        try {
            BufferedReader reader=new BufferedReader(new FileReader(file), 10*1024*1024);
            String line=null;
            while((line=reader.readLine())!=null){
               // System.out.println(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public static void readNIO(String path) throws FileNotFoundException, IOException{
        RandomAccessFile ramFile = new RandomAccessFile(path, "rw");
            
        // 获得相应的文件通道
       FileChannel mFileChannel = ramFile.getChannel();
       
       int size = (int) mFileChannel.size();
       
       System.out.println(size);
        // 获得共享内存缓冲区，该共享内存可读
       MappedByteBuffer mMappedByteBuffer = mFileChannel
               .map(FileChannel.MapMode.READ_WRITE, 0, size).load();
       
       System.out.println(new String(mMappedByteBuffer.toString()));
    }
    
    public static void main(String args[]) throws IOException{
        //读取10M大小文件
        //readScanner("E:\\eqipay_finance.sql");
        //readApacheCommon("E:\\eqipay_finance.sql");
        //readBuffer("E:\\eqipay_finance.sql");
        /**
         * 读取7G文件
         */
        //readScanner("E:\\eqipay_pay.sql");
        //read("E:\\eqipay_rcms.sql");
        System.out.println("程序开始");
        long start = System.currentTimeMillis();
        readBuffer("E:\\eqipay_pay.sql");
        long end = System.currentTimeMillis();
        System.out.println("遍历文件总共消耗时间："+(end-start));
    }
}