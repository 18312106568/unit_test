package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.utils.FileUtils;
import com.creditcloud.jpa.unit_test.vo.FileStorageVo;
import com.google.gson.Gson;
import org.junit.Test;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestFile {

    public static final String FILE_STORAGE_PATH = "E:\\project\\est\\lms\\filestorage.txt";

    @Test
    public void testDeepFiles() throws FileNotFoundException {
        List<String> deepArr = FileUtils.deepFiles("E:\\project\\est\\lms");
        Map<String,Long> lastModifyMap = FileUtils.converToLastModifyMap(deepArr);
        FileStorageVo fileStorageVo = new FileStorageVo();
        fileStorageVo.setFileDatas(lastModifyMap);
        fileStorageVo.setFileSize(deepArr.size());
        fileStorageVo.setLastSearchTime(new Date());
        fileStorageVo.setLastSearchTime(new Date());
        Gson gson = new Gson();
        String result = gson.toJson(fileStorageVo);
        PrintStream ps = new PrintStream(new FileOutputStream(new File(FILE_STORAGE_PATH)));
        ps.println(result);
    }

    @Test
    public void readFileStorage() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(FILE_STORAGE_PATH));
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNextLine()){
            sb.append(scanner.nextLine());
        }
        Gson gson = new Gson();
        FileStorageVo fileStorageVo = gson.fromJson(sb.toString(),FileStorageVo.class);
        Map<String,Long> dataMap = fileStorageVo.getFileDatas();
        for(String fileName : dataMap.keySet()){
            File newFile = new File(fileName);
            if(newFile.lastModified()>dataMap.get(fileName)){
                System.out.println(fileName);
            }
        }
    }
}
