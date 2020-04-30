package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.utils.FileUtils;
import com.creditcloud.jpa.unit_test.vo.FileStorageVo;
import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class TestFile {

    public static final String FILE_STORAGE_PATH = "E:\\project\\est\\lms\\filestorage.txt";

    public static final String WMS_STORAGE_PATH = "E:\\project\\est\\lms\\wms_storage.txt";

    public static final String WORK_PROJECT_PATH = "E:\\project\\est\\lms\\wms\\wms";
    //public static final String WORK_PROJECT_PATH = "E:\\prject\\est\\lms\\wms\\wms";

    public static final String RUNNING_PROJECT_PATH = "D:\\tomcat\\webapps\\wms";

    public static final String WMS_SOURCE_PATH = "E:\\project\\est\\lms\\wms\\src";

    public static final String WMS_TARGET_PATH = "E:\\project\\est\\lms\\wms\\target";

    public static final String WMS_RUNNING_PATH = "D:\\tomcat\\webapps\\wms\\WEB-INF\\classes";

    public static final String RUNNING_CLASS_PATH = "D:\\tomcat\\webapps\\wms\\WEB-INF\\classes";

    public static final String WMS_BAK_PATH = "E:\\bak\\wms";

    public static final String PITCH_JSP_PATH = "/WEB-INF/jsp/wms/%s/pitch.jsp";
    public static final String MAIN_JSP_PATH = "/WEB-INF/jsp/wms/%s/main.jsp";


    @Test
    public void testDeepFiles() throws FileNotFoundException {
        List<String> deepArr = FileUtils.deepFiles("E:\\project\\est\\lms\\wms");
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
    public void testDeepWmsFiles() throws FileNotFoundException {
        List<String> deepArr = FileUtils.deepFiles("E:\\project\\est\\lms\\wms");
        Map<String,Long> lastModifyMap = FileUtils.converToLastModifyMap(deepArr);
        FileStorageVo fileStorageVo = new FileStorageVo();
        fileStorageVo.setFileDatas(lastModifyMap);
        fileStorageVo.setFileSize(deepArr.size());
        fileStorageVo.setLastSearchTime(new Date());
        fileStorageVo.setLastSearchTime(new Date());
        Gson gson = new Gson();
        String result = gson.toJson(fileStorageVo);
        PrintStream ps = new PrintStream(new FileOutputStream(new File(WMS_STORAGE_PATH)));
        ps.println(result);
        ps.flush();
        ps.close();
    }

    private void deepDir(String dir,String storage) throws FileNotFoundException {
        List<String> deepArr = FileUtils.deepFiles(dir);
        Map<String,Long> lastModifyMap = FileUtils.converToLastModifyMap(deepArr);
        FileStorageVo fileStorageVo = new FileStorageVo();
        fileStorageVo.setFileDatas(lastModifyMap);
        fileStorageVo.setFileSize(deepArr.size());
        fileStorageVo.setLastSearchTime(new Date());
        fileStorageVo.setLastSearchTime(new Date());
        Gson gson = new Gson();
        String result = gson.toJson(fileStorageVo);
        PrintStream ps = new PrintStream(new FileOutputStream(new File(storage)));
        ps.println(result);
        ps.flush();
        ps.close();
    }

    @Test
    public void testDeepFilesJar() throws FileNotFoundException {
        List<String> deepArr = FileUtils.deepFiles("E:\\project\\est\\lms\\lib");
        String fileName = "E:\\project\\est\\lms\\classpath.txt";
        Scanner scanner = new Scanner(new FileInputStream(new File(fileName)));
        Set<String> fileSet = new HashSet<>();
        while(scanner.hasNextLine()){
            fileSet.add(scanner.nextLine());
        }
        for(String deep : deepArr){
            if(fileName.endsWith(".jar")){
                fileSet.add(deep);
            }
        }
        StringBuilder sb = new StringBuilder();
       for(String filePath : fileSet){
            sb.append(filePath).append(";");
        }
        System.out.println(sb.toString());
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

    /**
     * 复制已修改文件到运行环境
     * @throws FileNotFoundException
     * @throws ParseException
     */
    @Test
    public void scanFileThisWeekEdit() throws FileNotFoundException, ParseException {
        deepDir("E:\\project\\est\\lms\\wms",WMS_STORAGE_PATH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date monday = sdf.parse("2020-04-09");
        Scanner scanner = new Scanner(new FileInputStream(WMS_STORAGE_PATH));
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNextLine()){
            sb.append(scanner.nextLine());
        }
        Gson gson = new Gson();
        FileStorageVo fileStorageVo = gson.fromJson(sb.toString(),FileStorageVo.class);
        Map<String,Long> dataMap = fileStorageVo.getFileDatas();
        for(String fileName : dataMap.keySet()){
            File newFile = new File(fileName);
            if(newFile.lastModified()>monday.getTime() ){
                if((fileName.endsWith(".xml")
                        || fileName.endsWith(".jsp") )
                        &&fileName.indexOf("annualcheckstockmgr")==-1){
                  //  System.out.println(fileName+"-"+sdf.format(newFile.lastModified()));
                    String runProjectFileName = fileName.replace(WORK_PROJECT_PATH,RUNNING_PROJECT_PATH);
                    File runProjectFile = new File(runProjectFileName);
                   // System.out.println(runProjectFileName+"-"+sdf.format(runProjectFile.lastModified()));
                    if(newFile.lastModified() != runProjectFile.lastModified()) {
                        copyFile(fileName, runProjectFileName, 1024);

                        System.out.println(String.format("已复制：%s", fileName));
                    }
                }
                if(fileName.endsWith(".java")){
                    String targetName= fileName.replace(
                            WMS_SOURCE_PATH,WMS_TARGET_PATH)
                            .replace(".java",".class");
                    String runTargetName = targetName
                            .replace(WMS_TARGET_PATH,WMS_RUNNING_PATH);
                    File targetFile = new File(targetName);
                    File runTargetFile = new File(runTargetName);
                    if(targetFile.exists()
                            && targetFile.lastModified() != runTargetFile.lastModified()
                        &&targetFile.getAbsolutePath().indexOf("annualcheckstockmgr")==-1){
                       copyFile(targetName,runTargetName,1024);

                       System.out.println(String.format("已复制：%s",targetName));
                    }

                }

            }

        }
    }

    public static void copyFile(String source, String dest, int bufferSize) {
        InputStream in = null;
        OutputStream out = null;
        try {
            createFile(dest);
            in = new FileInputStream(new File(source));
            out = new FileOutputStream(new File(dest));

            byte[] buffer = new byte[bufferSize];
            int len;

            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
        } finally {

        }
    }

    @Test
    public void readFile() throws FileNotFoundException {
        String fileName = "E:\\project\\est\\lms\\classpath.txt";
        Scanner scanner = new Scanner(new FileInputStream(new File(fileName)));
        StringBuilder sb =new StringBuilder();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            sb.append(line).append(";");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void appendFile() throws IOException, TemplateException {
        String fileCopyFile = "E:\\project\\est\\lms\\wms\\wms\\WEB-INF\\tiles-defs-kun.xml";
        String fileName = "E:\\project\\est\\lms\\wms\\wms\\WEB-INF\\tiles-defs-kun.xml.bak";
        Scanner scanner = new Scanner(new FileInputStream(new File(fileCopyFile)));
        PrintWriter pw = new PrintWriter(new FileWriter(new File(fileName)));
        String line = "";
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while(scanner.hasNextLine()){
            index++;
            if(( line=scanner.nextLine()).equals("</tiles-definitions>")){
                Template template = getTemp("123.ftl");
                template.process(new Object(),pw);
                String content = template.getSource(
                        0,0,Integer.MAX_VALUE,Integer.MAX_VALUE);
                System.out.println(content);
                pw.print("</tiles-definitions>");
                break;
            }
            pw.println(line);
        }
        //pw.print(sb.toString());
        pw.flush();
        pw.close();
    }

    @Test
    public void testAppendFile() throws IOException, TemplateException, SQLException {
        String tiesPath = "E:\\project\\est\\lms\\wms\\wms\\WEB-INF\\tiles-defs-kun.xml";
        String strutsPath = "E:\\project\\est\\lms\\wms\\wms\\WEB-INF\\struts-config-kun.xml";
        String queryConfigPath = "E:\\project\\est\\lms\\wms\\wms\\WEB-INF\\conf\\metadata\\query-config-kun.xml";
        String treePath = "E:\\project\\est\\lms\\wms\\wms\\WEB-INF\\conf\\metadata\\tree-config-kun.xml";
        String formPath = "E:\\project\\est\\lms\\wms\\wms\\WEB-INF\\conf\\metadata\\form-config-kun.xml";

        List<TestJDBC.Field> dataList =
                TestJDBC.listTableFields(TestJDBC.getWmsConn(),"ZX_RPT_IMG_DOC");
        TestJDBC.ProjectModel projectModel = new TestJDBC.ProjectModel();
        projectModel.setSql("select t.* from ZX_RPT_IMG_DOC t ");
        projectModel.setProjectName("rptimgdoc");
        projectModel.setFieldList(dataList);
        projectModel.setPkList(dataList);
        projectModel.setModuleList(new ArrayList<>());
        Map<String, TestJDBC.ProjectModel> modelMap = new HashMap<>();
        modelMap.put("bean",projectModel);

        //1.修改tie-des.ftl 同时创建对应的jsp文件
        //appendToFile("wms\\tie-des.ftl",modelMap,tiesPath,"</tiles-definitions>");
        String pitchJspFileName = WORK_PROJECT_PATH + String.format(PITCH_JSP_PATH,projectModel.getProjectName());
        String mainJspFileName = WORK_PROJECT_PATH + String.format(MAIN_JSP_PATH,projectModel.getProjectName());
        createFile(pitchJspFileName);
        createFile(mainJspFileName);

//        appendToFile("wms\\main_jsp.ftl",modelMap,mainJspFileName,null);
//        appendToFile("wms\\pitch_jsp.ftl",modelMap,pitchJspFileName,null);


        //2.修改struts-config.xml 同时新增Java文件
       // appendToFile("wms\\struts_main.ftl",modelMap,strutsPath,"</action-mappings></struts-config>");

        String pitchJavaPath = String.format(
                "E:\\project\\est\\lms\\wms\\src\\com\\est\\helix\\wms\\%s\\%sPitchAction.java"
                ,projectModel.getProjectName()
                ,projectModel.getProjectName().substring(0,1).toUpperCase()
                        +projectModel.getProjectName().substring(1));
        createFile(pitchJavaPath);

       // appendToFile("wms\\pitch-Java.ftl",modelMap,pitchJavaPath,null);

        //3.修改query-config.xml、tree-config.xml
       // appendToFile("wms\\query-config.ftl",modelMap,queryConfigPath,"</query-config>");
     //   appendToFile("wms\\tree-config.ftl",modelMap,treePath,"</tree-config>");
        appendToFile("wms\\form-config.ftl",modelMap,formPath,"</form-config>");
    }

    @Test
    public void testAppendProjectModel() throws SQLException {
        List<TestJDBC.Field> dataList =
                TestJDBC.listTableFields(TestJDBC.getWmsConn(),"ZX_RPT_IMG_DOC");
        for(TestJDBC.Field field : dataList){
            System.out.println(field);
        }
    }

    /**
     * 追加内容到文本之中
     * @param tempName
     * @param tempBean
     * @param fileName
     * @param endWith
     * @throws IOException
     * @throws TemplateException
     */
    public void appendToFile(String tempName,Object tempBean,String fileName,String endWith)
            throws IOException, TemplateException {
        StringBuilder sb = new StringBuilder();
        if(!StringUtils.isEmpty(endWith)){
            //备份文件
            bakWmsFile(fileName);

            Scanner scanner = new Scanner(new FileInputStream(new File(fileName)));


            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.equals(endWith)){
                    scanner.close();
                    break;
                }
                sb.append(line).append("\r\n");
            }

        }

        PrintWriter pw = new PrintWriter(new FileWriter(new File(fileName)));
        if(!StringUtils.isEmpty(endWith)) {
            pw.println(sb.toString());
        }


        Template template = getTemp(tempName);
        template.process(tempBean,pw);

        if(!StringUtils.isEmpty(endWith)) {
            pw.print(endWith);
        }


        pw.flush();
        pw.close();
    }

    /**
     * 获取模板文件
     * @param tempName
     * @return
     * @throws IOException
     */
    public static Template getTemp(String tempName) throws IOException {
        String resourcePaht = "\\src\\main\\resources\\templates";
        String basePath = System.getProperty("user.dir")+resourcePaht;
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

        cfg.setDirectoryForTemplateLoading(new File(basePath));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template template = cfg.getTemplate(tempName);
        return template;
    }

    @Test
    public void testFile() throws IOException {
        String filePath = "E:\\bak\\wms\\wms\\123\\222";
        File file = new File(filePath);
       createFile(file.getAbsolutePath());
    }

    @Test
    public void printToFile() throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(
                new File("E:\\tmp\\phone.txt")));
        for(int i=0;i<1000000;i++){
            pw.println(System.currentTimeMillis()+"-"+i);
        }

        pw.flush();
        pw.close();
    }

    @Test
    public void readFileToObject() throws FileNotFoundException, IllegalAccessException {
        Scanner scanner = new Scanner(
                new FileInputStream( new File("E:\\tmp\\phone.txt")));
        List<String> strList = new ArrayList();
        Map<Integer,String> strMap = new HashMap<>();
        Integer index=0;
        while(scanner.hasNextLine()){
            strList.add(scanner.nextLine());
        }
        System.out.println(String.format("时间戳占用%dB"
                ,TestObject.fullSizeOf(System.currentTimeMillis())));
        for(int i=0;i<strList.size();i++){
            strMap.put(i,strList.get(i));
        }

        System.out.println(String.format("时间戳字符串占用%dB"
                ,TestObject.fullSizeOf(String.valueOf(System.currentTimeMillis()))));
        System.out.println(String.format("文件:phone.txt,读取到内存类型占用%dMB"
                ,TestObject.fullSizeOf(strList)/(1000*1000)));


    }

    /**
     * JVM内存变化测试
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void testJvmUsageChange() throws InterruptedException, IOException {
        List<String> array = new ArrayList<>();
        Runtime run = Runtime.getRuntime();
        String cmdRsLine = TestRunTime.execCmd("jps -lvm|findstr TestFile").trim();
        String[] results = cmdRsLine.split("\\s+");
        log.info("正在运行的进程ID:{}",results[0]);
        log.info("init!sleep 30 second,jvm memory total usage: {}MB"
                ,(run.totalMemory()- run.freeMemory())/(1000*1000));
        TestRunTime.execCmd(String.format("jstat -gc  %s >> E:\\tmp\\jvm\\1.txt",results[0]));
        Thread.sleep(30000);
        for(int i=0;i<10;i++){
            for(int j=0;j<500000;j++){
                array.add(new String("1588037485736"));
            }

            log.info("add success!sleep 30 second,jvm memory total usage: {}MB"
                    ,(run.totalMemory()- run.freeMemory())/(1000*1000));
            TestRunTime.execCmd(String.format("jstat -gc  %s >> E:\\tmp\\jvm\\1.txt",results[0]));
            Thread.sleep(5000);
        }
        testJvmUsage();
    }

    @Test
    public void testJvmUsage() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(new File("E:\\tmp\\jvm\\1.txt")));
        Map<String,List<Double>> dataTableMap = new HashMap<>();
        List<String> titleList = new ArrayList<>();

        int dataLen = 0;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine().trim();
            if(line.startsWith("S0C") && titleList.isEmpty()){
                String[] titles = line.split("\\s+");
                for(String title : titles){
                    dataTableMap.put(title,new ArrayList<>());
                    titleList.add(title);
                }
            }
            if(line.startsWith("S0C") || StringUtils.isEmpty(line)){
                continue;
            }
            String[] contents = line.split("\\s+");
            for(int i=0;i<titleList.size();i++){
                List<Double> dataArr = dataTableMap.get(titleList.get(i));
                dataArr.add(Double.valueOf(contents[i]));

            }
            dataLen++;

        }
        for(int i=1;i<dataLen;i++){
            Double initialUsage = dataTableMap.get("S0U").get(0)
                    +dataTableMap.get("S1U").get(0)
                    +dataTableMap.get("EU").get(0)+
                    dataTableMap.get("OU").get(0);
            Double nowUsage = dataTableMap.get("S0U").get(i)
                    +dataTableMap.get("S1U").get(i)
                    +dataTableMap.get("EU").get(i)+
                    dataTableMap.get("OU").get(i);

            Double countUsage = dataTableMap.get("S0C").get(i)+
                    dataTableMap.get("S1C").get(i)+
                    dataTableMap.get("EC").get(i)+
                    dataTableMap.get("OC").get(i)+
                    dataTableMap.get("MC").get(i)+
                    dataTableMap.get("CCSC").get(i);

            System.out.println(String.format("总共占用内存%.2fMB,初始化值是%.2fMB" +
                            ",当前使用是%.2fMB,内存变化:%.2fMB"
                    ,countUsage*1024/(1000*1000)
                    ,initialUsage*1024/(1000*1000)
                    ,nowUsage*1024/(1000*1000)
                    ,(nowUsage-initialUsage)*1024/(1000*1000)));
        }
    }

    public File deepParent(File file){
        return file.getParentFile().exists()? file : deepParent(file.getParentFile());
    }

    /**
     * 备份项目文件
     * @param fileName
     * @throws IOException
     */
    public  static void bakWmsFile(String fileName) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd24hhmm");
        if(!fileName.startsWith(WORK_PROJECT_PATH)){
            return ;
        }

        String targetFile = fileName.replace(WORK_PROJECT_PATH,WMS_BAK_PATH)+sdf.format(System.currentTimeMillis());

        createFile(targetFile);
        copyFile(fileName,targetFile,1024);
    }

    /**
     * 创建文件
     * @param fileName
     * @throws IOException
     */
    public static void createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(!file.exists()) {
            file.createNewFile();
            System.out.println(String.format("创建文件:%s",file.getAbsolutePath()));
        }

    }

}
