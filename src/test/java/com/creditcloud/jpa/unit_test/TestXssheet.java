/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.utils.ConverUtil;
import com.creditcloud.jpa.unit_test.utils.TranslateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author MRB
 */
public class TestXssheet {

    private static final String EXCEL_XLS = "xls";  
    private static final String EXCEL_XLSX = "xlsx";  
    
//    @Test
//    public void testSheet() throws IOException {
//        Workbook wb = null;
//        File file = new File("E://doc//需上传码上放心平台品规.xlsx");
//        InputStream in = new FileInputStream(file);
//        long start = System.currentTimeMillis();
//        wb = new XSSFWorkbook(in);
//        long end = System.currentTimeMillis();
//        System.out.println("speed time:"+(end-start));
//
//        System.out.println(wb.getNumberOfSheets());
//        Sheet sheet = wb.getSheetAt(0);
//        System.out.println(sheet.getFirstRowNum());
//        System.out.println(sheet.getLastRowNum());
//        StringBuilder sb = new StringBuilder();
//        for(Row row : sheet){
////           Iterator it = row.cellIterator();
////           while(it.hasNext()){
////               sb.append(it.next().toString());
////           }
//            System.out.println(row.getCell(2).toString());//+row.getCell(2).getCellType()
//           sb.append('\n');
//        }
//         end = System.currentTimeMillis();
//         System.out.println("speed time:"+(end-start));
//         System.out.println(sb.toString());
//    }

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

    @Test
    public void exportColumn() throws IOException {
        Workbook wb = null;
        File file = new File("E:\\doc\\4.22-运费核算\\附件6.“点运费+件运费+出车费”计费模式相关表格.xlsx");
        InputStream in = new FileInputStream(file);
        long start = System.currentTimeMillis();
        wb = new XSSFWorkbook(in);
        long end = System.currentTimeMillis();
        System.out.println("speed time:"+(end-start));

//        System.out.println(wb.getNumberOfSheets());
        Sheet sheet = wb.getSheetAt(2);
//        System.out.println(sheet.getFirstRowNum());
//        System.out.println(sheet.getLastRowNum());
        StringBuilder sb = new StringBuilder();
        List<String> columnComments = new ArrayList<>();
        List<String> columnNotes = new ArrayList<>();
        List<String> enColumnNames = new ArrayList<>();
        List<String> columns = columnComments;
        for(Row row : sheet){
           if(row.getRowNum()!=sheet.getFirstRowNum()){
               columns=columnNotes;
           }
           Iterator it = row.cellIterator();
           String columnName = null;
           while(it.hasNext()){
               columnName=it.next().toString();
               if(row.getRowNum()==sheet.getFirstRowNum()){
                   try {
                       enColumnNames.add(TranslateUtils.translate(columnName));
                   }catch (Exception ex){
                        ex.printStackTrace();
                   }

               }
               sb.append(columnName);
                columns.add(columnName);
           }
            //System.out.println(row.getCell(2).toString());//+row.getCell(2).getCellType()
            sb.append('\n');
        }
        end = System.currentTimeMillis();
        System.out.println("speed time:"+(end-start));
        System.out.println(sb.toString());
        int size = columnComments.size()>columnNotes.size()?columnNotes.size():columnComments.size();
        for(int i=0;i<size;i++){
            System.out.println(String.format("    /**\n" +
                    "     * %s(%s)\n" +
                    "     */\n" +
                    "    private String %s;",columnComments.get(i),columnNotes.get(i), ConverUtil.phraseToChangCame(enColumnNames.get(i))));
        }
    }


}