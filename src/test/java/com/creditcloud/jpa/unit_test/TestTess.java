package com.creditcloud.jpa.unit_test;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Test;

import java.io.File;

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
    public void testJpg(){
        ITesseract instance = new Tesseract();
        instance.setDatapath(System.getProperty("user.dir")+"//tess");
        instance.setLanguage("eng");

        try {
            String result =  instance.doOCR(new File(System.getProperty("user.dir")+"/images/002.jpg"));
            System.out.println(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }

    }

}
