package com.creditcloud.jpa.unit_test.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtils {
    public static List<String> deepFiles(String dirName){
        List<String> result = new ArrayList<>();
        File dir = new File(dirName);
        if(dir.isFile()){
            result.add(dir.getAbsolutePath());

        }
        if(dir.isDirectory()){
            File[] childDirs = dir.listFiles();
            for(File childDir : childDirs){
                result.addAll(deepFiles(childDir.getAbsolutePath()));
            }
        }
        return result;
    }

    public static Map<String,Long> converToLastModifyMap(List<String> fileNameList){
        Map<String,Long> result = new HashMap<>();
        for(String fileName : fileNameList){
            File newFile = new File(fileName);
            result.put(newFile.getAbsolutePath(),newFile.lastModified());
        }
        return result;
    }
}
