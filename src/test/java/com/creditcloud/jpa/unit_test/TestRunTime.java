package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.utils.FileUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestRunTime {
    //源码路径 源码java文件 输出路径 依赖包
    public static final String COMPILE_TEMP = "javac  -encoding UTF-8 -sourcepath %s %s -d %s -cp %s";

    public static final String COMPILE_TEMP2 = "javac  -encoding UTF-8 -sourcepath %s %s -d %s ^";

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

    @Test
    public void testCompile2(){
        String sourcePath = "C:\\est\\lms\\lmsdao\\src-auto-gen";
        List<String> sourceList = getSourceFile(sourcePath,".java");
        StringBuilder sourceSb = new StringBuilder();
        for(String source : sourceList){
            sourceSb.append(source).append(" ");
        }
        String targetPath = "C:\\est\\lms\\lmsdao\\src-auto-gen\\target";
        String classPath = ".\\;C:\\est\\lms\\helix-job\\classes;C:\\est\\lms\\lmsba\\classes;C:\\est\\lms\\lmslib\\lmsba.jar;C:\\est\\lms\\lmslib\\ddis.jar;C:\\est\\lms\\lmslib\\EstPubFunc.jar;C:\\est\\lms\\lmslib\\helixcore3.jar;C:\\est\\lms\\lmslib\\lmsdao.jar;C:\\est\\lms\\lmslib\\service-manager.jar;C:\\est\\lms\\lmslib\\workassn.jar;C:\\est\\liproject\\li\\lib\\ojdbc14.jar;C:\\est\\liproject\\li\\lib\\adaptx_0.9.7.jar;C:\\est\\liproject\\li\\lib\\jasperreports-0.5.0.jar;C:\\est\\liproject\\li\\lib\\commons-dbcp-1.1.jar;C:\\est\\liproject\\li\\lib\\bsh-1.2b7.jar;C:\\est\\liproject\\li\\lib\\jakarta-oro.jar;C:\\est\\liproject\\li\\lib\\commons-lang.jar;C:\\est\\liproject\\li\\lib\\jakarta-oro-2.0.5.jar;C:\\est\\liproject\\li\\lib\\unittest.jar;C:\\est\\liproject\\li\\lib\\cactus.jar;C:\\est\\liproject\\li\\lib\\xmlParserAPIs.jar;C:\\est\\liproject\\li\\lib\\jtf-0.1.jar;C:\\est\\liproject\\li\\lib\\commons-collections.jar;C:\\est\\liproject\\li\\lib\\simfang.jar;C:\\est\\liproject\\li\\lib\\commons-logging-1.0.4.jar;C:\\est\\liproject\\li\\lib\\struts.jar;C:\\est\\liproject\\li\\lib\\log4j-1.2.11.jar;C:\\est\\liproject\\li\\lib\\castor-0.9.5.2.jar;C:\\est\\liproject\\li\\lib\\ant_1.5.jar;C:\\est\\liproject\\li\\lib\\jdom.jar;C:\\est\\liproject\\li\\lib\\jndi_1.2.1.jar;C:\\est\\liproject\\li\\lib\\jython.jar;C:\\est\\liproject\\li\\lib\\commons-beanutils.jar;C:\\est\\liproject\\li\\lib\\jbcl.jar;C:\\est\\liproject\\li\\lib\\jta1.0.1.jar;C:\\est\\liproject\\li\\lib\\jakarta-poi-1.10.0-dev-20030222.jar;C:\\est\\liproject\\li\\lib\\commons-fileupload.jar;C:\\est\\liproject\\li\\lib\\xerces-J_1.4.0.jar;C:\\est\\liproject\\li\\lib\\postgresql.jar;C:\\est\\liproject\\li\\lib\\jakarta-regexp-1.1.jar;C:\\est\\liproject\\li\\lib\\commons-digester.jar;C:\\est\\liproject\\li\\lib\\itext-0.96.jar;C:\\est\\liproject\\li\\lib\\ldapjdk_4.1.jar;C:\\est\\liproject\\li\\lib\\jdbc-se2.0.jar;C:\\est\\liproject\\li\\lib\\commons-pool-1.1.jar;C:\\est\\liproject\\li\\lib\\commons-logging-api.jar;C:\\est\\liproject\\li\\lib\\gerald.jar;C:\\est\\liproject\\li\\lib\\velocity-1.3.jar;C:\\est\\liproject\\li\\lib\\commons-resources.jar;C:\\est\\liproject\\li\\lib\\rollerjm-utils.jar;C:\\est\\liproject\\li\\lib\\ftp.jar;C:\\est\\liproject\\li\\lib\\dom4j-full.jar;C:\\est\\liproject\\li\\lib\\commons-validator.jar;C:\\est\\liproject\\li\\lib\\servlet.jar;C:\\est\\liproject\\li\\lib\\struts-legacy.jar;C:\\est\\liproject\\li\\lib\\jdbc2_0-stdext.jar;C:\\est\\liproject\\li\\lib\\junit.jar;E:\\softface\\JBUILDER\\thirdparty\\jakarta-tomcat-4.1.24-LE-jdk14\\common\\lib\\servlet.jar;E:\\softface\\JBUILDER\\thirdparty\\junit3.8\\junit.jar;E:\\softface\\JBUILDER\\lib\\unittest.jar;C:\\est\\liproject\\li\\lib\\Axis-jb2005\\axis.jar;C:\\est\\liproject\\li\\lib\\Axis-jb2005\\axis-ant.jar;C:\\est\\liproject\\li\\lib\\Axis-jb2005\\commons-discovery.jar;C:\\est\\liproject\\li\\lib\\Axis-jb2005\\commons-logging.jar;C:\\est\\liproject\\li\\lib\\Axis-jb2005\\jaxrpc.jar;C:\\est\\liproject\\li\\lib\\Axis-jb2005\\saaj.jar;C:\\est\\liproject\\li\\lib\\Axis-jb2005\\wsdl4j.jar;E:\\softface\\JBUILDER\\jdk1.4\\demo\\jfc\\Java2D\\Java2Demo.jar;E:\\softface\\JBUILDER\\jdk1.4\\demo\\plugin\\jfc\\Java2D\\Java2Demo.jar;E:\\softface\\JBUILDER\\jdk1.4\\jre\\lib\\charsets.jar;E:\\softface\\JBUILDER\\jdk1.4\\jre\\lib\\ext\\dnsns.jar;E:\\softface\\JBUILDER\\jdk1.4\\jre\\lib\\ext\\ldapsec.jar;E:\\softface\\JBUILDER\\jdk1.4\\jre\\lib\\ext\\localedata.jar;E:\\softface\\JBUILDER\\jdk1.4\\jre\\lib\\ext\\sunjce_provider.jar;E:\\softface\\JBUILDER\\jdk1.4\\jre\\lib\\im\\indicim.jar;E:\\softface\\JBUILDER\\jdk1.4\\jre\\lib\\jaws.jar;E:\\softface\\JBUILDER\\jdk1.4\\jre\\lib\\jce.jar;E:\\softface\\JBUILDER\\jdk1.4\\jre\\lib\\jsse.jar;E:\\softface\\JBUILDER\\jdk1.4\\jre\\lib\\rt.jar;E:\\softface\\JBUILDER\\jdk1.4\\jre\\lib\\sunrsasign.jar;E:\\softface\\JBUILDER\\jdk1.4\\lib\\dt.jar;E:\\softface\\JBUILDER\\jdk1.4\\lib\\htmlconverter.jar;E:\\softface\\JBUILDER\\jdk1.4\\lib\\tools.jar;E:\\project\\est\\lms\\gendao\\classes";
        String command = String.format(COMPILE_TEMP2,sourcePath,sourceSb.toString(),targetPath);
        try {
            System.out.println(sourceSb.toString());
            execCmd(command);
//            execCmd(classPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeepPath(){
        List<String> pathResult = getDirs("C:\\est\\lms\\lmsdao\\src-auto-gen");
        for(String path : pathResult){
            System.out.println(path);
        }
    }

    @Test
    public void testDeepFiles(){
        List<String> fileresult = FileUtils.deepFiles("E:\\project\\est\\lms");
       Integer count =0;
        for(String fileName : fileresult){
            if(fileName.indexOf("复")!=-1||fileName.matches(".*\\d$")){
               count++;
               File deleteFile = new File(fileName);
               if(deleteFile.isFile()){
                   deleteFile.delete();
               }
            }
        }
        System.out.println(count);
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

    public List<String> getDirs(String path){
        List<String> result = new ArrayList<>();
        File dir = new File(path);
        if(dir.isDirectory()){
            File[] childDirs = dir.listFiles();
            for(File childDir : childDirs){
                List<String> childResult = getDirs(childDir.getAbsolutePath());
                if(childResult.isEmpty()){
                    continue;
                }
                result.addAll(childResult);
            }
            result.add(dir.getAbsolutePath());
        }
        return result;
    }
}


