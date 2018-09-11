/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.io;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import org.junit.Test;
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
}
