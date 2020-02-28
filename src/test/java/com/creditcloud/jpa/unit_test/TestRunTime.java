package com.creditcloud.jpa.unit_test;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestRunTime {
    //源码路径 源码java文件 输出路径 依赖包
    public static final String COMPILE_TEMP = "javac  -encoding UTF-8 -sourcepath %s %s -d %s -cp %s";

    @Test
    public void testCmd() throws IOException, InterruptedException {
        execCmd("java -version");
    }

    public void execCmd(String command) throws IOException {
        Process process = null;
        String[] cmd = { "cmd", "/c", command };
        process = Runtime.getRuntime().exec(cmd);

        Scanner errScanner = new Scanner(process.getErrorStream(),"GBK");
        while(errScanner.hasNextLine()){
            String line = new String(errScanner.nextLine().getBytes(),"UTF-8");
            System.out.println(line);
        }

        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
// 3、从流中读取数据
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line + "\r\n");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void testGetSourceFile(){
        List<String> result = getSourceFile("E:\\project\\gm-tools\\src\\main\\java",".java");
        for(String javaFile : result){
            System.out.println(javaFile);
        }
    }

    @Test
    public void testCompile(){
        String sourcePath = "E:\\project\\gm-tools\\src\\main\\java";
        List<String> sourceList = getSourceFile(sourcePath,".java");
        StringBuilder sourceSb = new StringBuilder();
        for(String source : sourceList){
            sourceSb.append(source).append(" ");
        }
        String targetPath = "E:\\project\\gm-tools\\target2";
        String libPath = "E:\\project\\gm-tools\\gm-tools\\lib\\";
        List<String> jarList = getSourceFile(libPath,".jar");
        StringBuilder libSb = new StringBuilder();
        for(String jar : jarList){
            libSb.append(jar).append(";");
        }
//        System.out.println(sourcePath);
//        System.out.println(sourceSb.toString());
//        System.out.println(targetPath);
//        System.out.println(libSb.toString());
        String command = String.format(COMPILE_TEMP,sourcePath,sourceSb.toString(),targetPath,libSb.toString());
        try {
            execCmd(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> getSourceFile(String path,String suffix){
        List<String> result = new ArrayList<>();
        File dir = new File(path);
        if(dir.isDirectory()){
            File[] childDirs = dir.listFiles();
            for(File childDir : childDirs){
                List<String> childResult = getSourceFile(childDir.getAbsolutePath(),suffix);
                if(childResult.isEmpty()){
                    continue;
                }
                result.addAll(childResult);
            }
        }
        if(dir.getName().indexOf(suffix)!=-1 && dir.getName().endsWith(suffix)){
            result.add(dir.getAbsolutePath());
        }
        return result;
    }
}
