package com.creditcloud.jpa.unit_test;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TestZip {

    @Test
    public void dozip() throws IOException {
        File dir = new File("F:\\var\\pdf");
        File[] dirChildFiles = dir.listFiles();

        File zipFile = new File("pdf.zip");
        if(!zipFile.exists()){
            zipFile.createNewFile();
        }
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        byte[] buffer = new byte[1024];
        int len = 0;
        for(File dirChildFile : dirChildFiles){
            FileInputStream fis = new FileInputStream(dirChildFile);
            out.putNextEntry(new ZipEntry(dirChildFile.getName()));
            while((len=fis.read(buffer))>0){
                out.write(buffer,0,len);
                out.flush();
            }
            fis.close();
        }
        out.closeEntry();
        out.close();
        System.out.println(zipFile.getAbsolutePath());
    }

    @Test
    public void doDeflat() throws IOException {
        File zipFile = new File("pdf.zip");
        FileInputStream zipIn = new FileInputStream(zipFile);
        int lenSum = 0;
        int index =1;
        byte[] buffer = new byte[1024];
        int len=0;
        FileOutputStream zipPartOut = new FileOutputStream(
                new File("pdf.zip.part" + index));
        while((len=zipIn.read(buffer))>0) {
            if (lenSum > zipFile.length() / 2 && index==1) {
                index =2;
                zipPartOut.flush();
                zipPartOut.close();
                zipPartOut  = new FileOutputStream(
                        new File("pdf.zip.part" + index));
            }
            lenSum += len;
            zipPartOut.write(buffer,0,len);
        }
        zipPartOut.flush();
        zipPartOut.close();
    }

    final static int BUFFER = 1024;

    final static int MEGA = 1024*1024;


    @Test
    public void dozip2() throws IOException {
        //zip("F:\\var\\pdf\\公司借款合同_一米小贷-加表单.pdf");
        createSplitZipFile("F:\\var\\pdf\\公司借款合同_一米小贷-加表单.pdf");
    }

    public void zip(String path) throws IOException {
        File file = new File(path);

        BufferedInputStream origin = null;
        byte data[] = new byte[BUFFER];
        FileInputStream fi = new FileInputStream(file);
        origin = new BufferedInputStream(fi, BUFFER);
        int count;
        int offset = 0;
        ZipOutputStream out = null;
        while (true) {
            if ((count = origin.read(data, 0, BUFFER)) != -1) {
                if (offset % (MEGA) == 0) {
                    FileOutputStream dest = new FileOutputStream(file.getName()
                            + ".(part" + (offset / (MEGA) + 1) + ").zip");
                    out = new ZipOutputStream(new BufferedOutputStream(dest));
                    ZipEntry entry = new ZipEntry(file.getName());
                    out.putNextEntry(entry);
                }
                out.write(data, 0, count);
                offset += 1024;
                if (offset % (MEGA) == 0 && offset != 0) {
                    out.close();
                }
            } else {
                out.close();
                break;
            }
        }
        origin.close();
    }

    public void createSplitZipFile(String path) {

        try {
            // Initiate ZipFile object with the path/name of the zip file.
            ZipFile zipFile = new ZipFile(path+".zip");
            System.out.println(zipFile.isValidZipFile());
            //System.out.println(zipFile.isSplitArchive());
            // Build the list of files to be added in the array list
            // Objects of type File have to be added to the ArrayList
            ArrayList filesToAdd = new ArrayList();
            filesToAdd.add(new File(path));

            // Initiate Zip Parameters which define various properties such
            // as compression method, etc.
            ZipParameters parameters = new ZipParameters();

            // set compression method to store compression
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

            // Set the compression level. This value has to be in between 0 to 9
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            // Create a split file by setting splitArchive parameter to true
            // and specifying the splitLength. SplitLenth has to be greater than
            // 65536 bytes
            // Please note: If the zip file already exists, then this method throws an
            // exception
            zipFile.createZipFile(filesToAdd, parameters, true, MEGA);
            System.out.println(zipFile.getSplitZipFiles());
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}
