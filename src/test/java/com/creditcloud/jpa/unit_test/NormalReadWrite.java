/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author MRB
 */
public class NormalReadWrite {
    
    public static long copyFile(String source,String desc) throws FileNotFoundException, IOException{
        long start = System.currentTimeMillis();
        File sourceFile = new File(source);
        InputStream sourceIn = new FileInputStream(sourceFile);
        File descFile = new File(desc);
        OutputStream descOut = new FileOutputStream(descFile);
        int count = 0;
        byte[] bufs = new byte[1024];
        while ((count = sourceIn.read(bufs))>0) {
           descOut.write(bufs, 0, count);
        }
        sourceIn.close();
        descOut.flush();
        descOut.close();
        long end = System.currentTimeMillis();
        return end-start;
    }
    
    public static void main(String[] args){
        try {
            String readFileName = "D:\\program\\glassfish4.rar";
            String writeFileName = "D:\\tmp\\1.rar";
            System.out.println("写文件耗时："+copyFile(readFileName,writeFileName));
        } catch (Exception e) {
        }
    }
}
