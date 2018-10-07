/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author MRB
 */
@RestController
@RequestMapping("/file")
public class UploadController {
    @PostMapping("/upload")
    @ResponseBody
    public String postFile(@RequestParam("file") MultipartFile file) throws IOException{
        Workbook wb = null;  
        System.out.println(file.getName());
        System.out.println(file.getSize());
        long start = System.currentTimeMillis();
        wb = new HSSFWorkbook(file.getInputStream());  
        long end = System.currentTimeMillis();
        
        System.out.println("speed time:"+(end-start));
        
        System.out.println(wb.getNumberOfSheets());
        Sheet sheet = wb.getSheetAt(0);
        System.out.println(sheet.getFirstRowNum());
        System.out.println(sheet.getLastRowNum());
        StringBuilder sb = new StringBuilder();
        for(Row row : sheet){
           Iterator it = row.cellIterator();
           while(it.hasNext()){
               sb.append(it.next().toString());
           }
           sb.append('\n');
        }
         end = System.currentTimeMillis();
        System.out.println ("speed time:"+(end-start));
        return sb.toString();
    }
}
