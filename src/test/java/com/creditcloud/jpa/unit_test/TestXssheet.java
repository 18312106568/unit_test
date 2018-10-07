/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.nio.DataSource;
import org.apache.poi.poifs.nio.FileBackedDataSource;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author MRB
 */
public class TestXssheet {

    private static final String EXCEL_XLS = "xls";  
    private static final String EXCEL_XLSX = "xlsx";  
    
    @Test
    public void testSheet() throws IOException {
        Workbook wb = null;  
        File file = new File("G://2199811.xls");
        InputStream in = new FileInputStream(file);
        long start = System.currentTimeMillis();
        wb = new HSSFWorkbook(in);  
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
         System.out.println("speed time:"+(end-start));
         System.out.println(sb.toString());
    }
}