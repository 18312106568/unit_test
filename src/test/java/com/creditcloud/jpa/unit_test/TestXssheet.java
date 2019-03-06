/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.util.Iterator;
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
        File file = new File("E://doc//需上传码上放心平台品规.xlsx");
        InputStream in = new FileInputStream(file);
        long start = System.currentTimeMillis();
        wb = new XSSFWorkbook(in);
        long end = System.currentTimeMillis();
        System.out.println("speed time:"+(end-start));
        
        System.out.println(wb.getNumberOfSheets());
        Sheet sheet = wb.getSheetAt(0);
        System.out.println(sheet.getFirstRowNum());
        System.out.println(sheet.getLastRowNum());
        StringBuilder sb = new StringBuilder();
        for(Row row : sheet){
//           Iterator it = row.cellIterator();
//           while(it.hasNext()){
//               sb.append(it.next().toString());
//           }
            System.out.println(row.getCell(2).toString());//+row.getCell(2).getCellType()
           sb.append('\n');
        }
         end = System.currentTimeMillis();
         System.out.println("speed time:"+(end-start));
         System.out.println(sb.toString());
    }

    @Test
    public void exportCompany() throws IOException {
        Workbook wb = null;
        File file = new File("E://doc//往来单位批量导入-广州医药.xls");
        InputStream in = new FileInputStream(file);
        OutputStream out = new FileOutputStream(file);

        wb = new HSSFWorkbook(in);
        Sheet sheet = wb.getSheetAt(0);
        int index=0;
        for(Row row : sheet) {
            index++;
            if (index <= 2) {
                continue;
            }
            Iterator it = row.cellIterator();
            while(it.hasNext()){
                System.out.println(it.next().toString());
            }
        }
        Row row = sheet.createRow(sheet.getLastRowNum()+1);
        row.createCell(0).setCellValue("123");
        row.createCell(1).setCellValue("123");
        wb.write(out);
        wb.close();
        out.close();
    }
}