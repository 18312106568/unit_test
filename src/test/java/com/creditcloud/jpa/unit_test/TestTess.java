package com.creditcloud.jpa.unit_test;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestTess {

    @Test
    public void testPng(){
        ITesseract instance = new Tesseract();
        instance.setDatapath(System.getProperty("user.dir")+"//tess");
        instance.setLanguage("eng");

        try {
            String result =  instance.doOCR(new File(System.getProperty("user.dir")+"/images/003.png"));
            System.out.println(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testChi(){
        ITesseract instance = new Tesseract();
        instance.setDatapath(System.getProperty("user.dir")+"//tess");
        instance.setLanguage("chi_sim");
        List<String> resultList = new ArrayList();
        File dir = new File(System.getProperty("user.dir")+"/images");
        File[] imageList = dir.listFiles();
        for(File image:imageList) {
            try {
                String result = instance.doOCR(image);
                System.out.println(result);
            } catch (TesseractException e) {
                e.printStackTrace();
            }
        }

    }

}
