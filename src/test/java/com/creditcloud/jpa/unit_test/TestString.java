/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.utils.DateUtils;
import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.rule.JunitPerfRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author MRB
 */
public class TestString {

    public static String name = "123";

    public static String classpaths = "C:\\est\\lms\\helix-job\\classes;C:\\est\\lms\\lmsba\\classes;C:\\est\\lms\\lmslib\\lmsba.jar;C:\\est\\lms\\lmslib\\ddis.jar;C:\\est\\lms\\lmslib\\EstPubFunc.jar;C:\\est\\lms\\lmslib\\helixcore3.jar;C:\\est\\lms\\lmslib\\lmsdao.jar;C:\\est\\lms\\lmslib\\service-manager.jar;C:\\est\\lms\\lmslib\\workassn.jar;C:\\est\\lib\\ojdbc14.jar;C:\\est\\lib\\adaptx_0.9.7.jar;C:\\est\\lib\\jasperreports-0.5.0.jar;C:\\est\\lib\\commons-dbcp-1.1.jar;C:\\est\\lib\\bsh-1.2b7.jar;C:\\est\\lib\\jakarta-oro.jar;C:\\est\\lib\\commons-lang.jar;C:\\est\\lib\\jakarta-oro-2.0.5.jar;C:\\est\\lib\\unittest.jar;C:\\est\\lib\\cactus.jar;C:\\est\\lib\\xmlParserAPIs.jar;C:\\est\\lib\\jtf-0.1.jar;C:\\est\\lib\\commons-collections.jar;C:\\est\\lib\\simfang.jar;C:\\est\\lib\\commons-logging.jar;C:\\est\\lib\\struts.jar;C:\\est\\lib\\log4j-1.2.7.jar;C:\\est\\lib\\castor-0.9.5.2.jar;C:\\est\\lib\\ant_1.5.jar;C:\\est\\lib\\jdom.jar;C:\\est\\lib\\jndi_1.2.1.jar;C:\\est\\lib\\jython.jar;C:\\est\\lib\\commons-beanutils.jar;C:\\est\\lib\\jbcl.jar;C:\\est\\lib\\jta1.0.1.jar;C:\\est\\lib\\jakarta-poi-1.10.0-dev-20030222.jar;C:\\est\\lib\\commons-fileupload.jar;C:\\est\\lib\\xerces-J_1.4.0.jar;C:\\est\\lib\\postgresql.jar;C:\\est\\lib\\jakarta-regexp-1.1.jar;C:\\est\\lib\\commons-digester.jar;C:\\est\\lib\\itext-0.96.jar;C:\\est\\lib\\ldapjdk_4.1.jar;C:\\est\\lib\\jdbc-se2.0.jar;C:\\est\\lib\\commons-pool-1.1.jar;C:\\est\\lib\\commons-logging-api.jar;C:\\est\\lib\\gerald.jar;C:\\est\\lib\\velocity-1.3.jar;C:\\est\\lib\\commons-resources.jar;C:\\est\\lib\\rollerjm-utils.jar;C:\\est\\lib\\ftp.jar;C:\\est\\lib\\dom4j-full.jar;C:\\est\\lib\\commons-validator.jar;C:\\est\\lib\\servlet.jar;C:\\est\\lib\\struts-legacy.jar;C:\\est\\lib\\jdbc2_0-stdext.jar;C:\\est\\lib\\junit.jar;C:\\JBuilder9\\thirdparty\\jakarta-tomcat-4.1.24-LE-jdk14\\common\\lib\\servlet.jar;C:\\JBuilder9\\thirdparty\\junit3.8\\junit.jar;C:\\JBuilder9\\lib\\unittest.jar;C:\\est\\lib\\Axis-jb2005\\axis.jar;C:\\est\\lib\\Axis-jb2005\\axis-ant.jar;C:\\est\\lib\\Axis-jb2005\\commons-discovery.jar;C:\\est\\lib\\Axis-jb2005\\commons-logging.jar;C:\\est\\lib\\Axis-jb2005\\jaxrpc.jar;C:\\est\\lib\\Axis-jb2005\\saaj.jar;C:\\est\\lib\\Axis-jb2005\\wsdl4j.jar;C:\\JBuilder9\\jdk1.4\\demo\\jfc\\Java2D\\Java2Demo.jar;C:\\JBuilder9\\jdk1.4\\demo\\plugin\\jfc\\Java2D\\Java2Demo.jar;C:\\JBuilder9\\jdk1.4\\jre\\lib\\charsets.jar;C:\\JBuilder9\\jdk1.4\\jre\\lib\\ext\\dnsns.jar;C:\\JBuilder9\\jdk1.4\\jre\\lib\\ext\\ldapsec.jar;C:\\JBuilder9\\jdk1.4\\jre\\lib\\ext\\localedata.jar;C:\\JBuilder9\\jdk1.4\\jre\\lib\\ext\\sunjce_provider.jar;C:\\JBuilder9\\jdk1.4\\jre\\lib\\im\\indicim.jar;C:\\JBuilder9\\jdk1.4\\jre\\lib\\jaws.jar;C:\\JBuilder9\\jdk1.4\\jre\\lib\\jce.jar;C:\\JBuilder9\\jdk1.4\\jre\\lib\\jsse.jar;C:\\JBuilder9\\jdk1.4\\jre\\lib\\rt.jar;C:\\JBuilder9\\jdk1.4\\jre\\lib\\sunrsasign.jar;C:\\JBuilder9\\jdk1.4\\lib\\dt.jar;C:\\JBuilder9\\jdk1.4\\lib\\htmlconverter.jar;C:\\JBuilder9\\jdk1.4\\lib\\tools.jar";

    public static String dirPath = "E:\\project\\est\\lms\\wms\\wms\\WEB-INF\\conf\\metadata";

    public TestString(){}

    @Test
    public void testFilesuffix(){
        File dir = new File(dirPath);
        File[] xmlFile = dir.listFiles();
        for(File xml : xmlFile){
            if(xml.getName().endsWith("-jwz.xml")){
                System.out.println(xml.getName()+new Date(xml.lastModified())+getCreateTime(xml.getAbsolutePath()));
            }

        }
    }

    @Test
    public void testclassPath(){
        String[] classpathArr = classpaths.split(";");
        for(String classpath : classpathArr){
            //System.out.print(classpath+"-");
            if(classpath.startsWith("C:\\est")){
                System.out.println(classpath.replace("C:\\est","E:\\project\\est"));
            }
            if(classpath.startsWith("C:\\JBuilder9")){
                System.out.println(classpath.replace("C:\\JBuilder9","E:\\softface\\JBUILDER"));
            }
        }
    }

    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule();

    @Test
    public void testSdf() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yy");
        String nowStr = sdf.format(new Date());
        Date now = sdf.parse("03-41");
        System.out.println(nowStr);
        System.out.println(now);
    }

    @Test
    public void testStringAppend(){
        String str1 = "aa"+"bb"+"cc"+"dd";
        String str2 = str1+"ee";
        String str3 = "";
        Integer abc = 256;
        System.out.println(str2);
        //System.out.println("mystr:"+str1);
    }

    public void testJavaplocals(String abc){
    }

    @Test
    @JunitPerfConfig(duration = 1000)
    public void testStringIntern() throws InterruptedException {
        String s1 = new String("bbb").intern();
        String s2 = "bbb";
        System.out.println(System.getProperty("file.encoding"));
        System.out.println("测试常量池");
        System.out.println(s1 == s2);    // true
       // Thread.sleep(3000);

    }

    @Test
    public void testFilePath() throws FileNotFoundException{
        System.out.println(this.getClass().getResource("").getPath());
        //System.out.println(ResourceUtils.getURL("classpath"));
    }

    //@Test
    public static void main(String[] args){
        //System.out.println(Charset.defaultCharset());
        Scanner scan = new Scanner(System.in,Charset.defaultCharset().name());
        System.out.println("请开始输入：");
        //System.out.println(scan.hasNext());
        while (scan.hasNext()) {
            String line = scan.nextLine();
            System.out.println(line);
            System.out.println(line.getBytes().length);
        }
    }

    @Test
    @JunitPerfConfig(duration = 1000)
    public void testUTF8ToGBK() throws UnsupportedEncodingException, InterruptedException, InterruptedException, InterruptedException{
        System.out.println(Charset.defaultCharset().name());
        String str1 = new String("你好".getBytes(),"GBK");
        char[] gbkBytes = str1.toCharArray();
        System.out.println(String.valueOf(gbkBytes, 0, 2));
        System.out.println("你好".getBytes().length);
        for(Map.Entry<Object,Object> entry : System.getProperties().entrySet()){
            System.out.println(entry.getKey()+"-"+entry.getValue());
        }
       // Thread.sleep(200);
    }

    @Test
    public void testAscll(){
        System.out.println((char) (97+25));
    }


    public static class PString{
        private  String test;

        public PString(String test){
            this.test = test;
        }


        private void test(){
            System.out.println(test);
        }
    }

    @Test
    public  void editFileName(){
        File dir = new File("E:\\tmp\\bms3_dzfgs");
        File[] childFiles = dir.listFiles();
        for(File file : childFiles){
            if(file.getName().endsWith(".pbd")){
                File newFile = new File(file.getAbsolutePath().replaceAll(".pbd",".pbl"));
                file.renameTo(newFile);
                System.out.println(file.getName());
            }
        }
    }

    @Test
    public void testSystemTime(){
        long timeS = 1555084800000L+24*60*60*1000L;
        System.out.println(timeS);
        System.out.println(DateUtils.format(new Date(timeS)));
    }

    @Test
    public void testDateTimeStamp() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss SSS");
        Date date = sdf.parse("20190430 12:00:00 000");
        Long timeDiff = date.getTime()-System.currentTimeMillis();
        System.out.println(date.getTime());
        System.out.println((date.getTime()-System.currentTimeMillis())/(1000*3600));
    }

    /**
     * 读取文件创建时间
     */
    private static String getCreateTime(String filePath){
        String strTime = null;
        try {
            Process p = Runtime.getRuntime().exec("cmd /C dir "
                    + filePath
                    + "/tc" );
            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = br.readLine()) != null){
                if(line.length()<17){
                    continue;
                }
                    strTime = line.substring(0,17);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return strTime;

    }

    @Test
    public void testPrint(){
        for(int i=0;i<Short.MAX_VALUE*2-1;i++){
            System.out.print((int)(Math.random()*10));
        }
    }

    @Test
    public void testIntern(){
        String str = Long.toString(System.currentTimeMillis());
        System.out.println(str == str.intern());
    }

    @Test
    public void testLongestCommonPrefix(){
        String strs[] = {"flw",""};
        String longPrefix = longestCommonPrefix(strs);
        System.out.println(longPrefix);
    }

    public String longestCommonPrefix(String[] strs) {
        int cursor = 0;
        while(true){
            if(strs==null || strs.length==0 ){
                return "";
            }
            if(strs.length ==1){
                return strs[0];
            }
            for(int strIndex=1;strIndex<strs.length;strIndex++){
                if(strs[0].length()<=cursor
                        ||strs[strIndex].length()<=cursor
                        ||strs[0].charAt(cursor)!=strs[strIndex].charAt(cursor)){
                    return strs[0].substring(0,cursor);
                }
            }
            cursor++;
        }

    }

    /**
     * kmp算法 求next数组
     */
    public int[] getNext(String temp){
        char[] tempCharArr = temp.toCharArray();
        int[] nextArr = new int[tempCharArr.length];
        for(int i=0;i<nextArr.length;i++){
            nextArr[i] = -1;
        }
        int index=1;
        while(index<tempCharArr.length){
            int k = nextArr[index-1];
            while(k!=-1 && tempCharArr[k+1]!=tempCharArr[index]){
                k = nextArr[k];
            }
            if(tempCharArr[k+1]==tempCharArr[index]){
                nextArr[index++] = k+1;
                continue;
            }
            nextArr[index++] = -1;
        }
        return nextArr;
    }


    public int kmp(String oriStr,String temp){
        char[] oriArr = oriStr.toCharArray();
        char[] tempArr = temp.toCharArray();
        int[] nextArr = getNext(temp);
        int tempIndex = -1;
        for(int i=0;i<oriArr.length;i++){
            if(tempIndex == tempArr.length-1){
                return i-tempArr.length;
            }
            while(tempIndex!=-1 && tempArr[tempIndex+1]!=oriArr[i]){
                tempIndex=nextArr[tempIndex];
            }
            if(tempArr[tempIndex+1]==oriArr[i]){
                tempIndex++;
            }
        }
        return -1;
    }

    @Test
    public void testKmp(){
        String oriStr = "aabcbcad";
        String temp = "bca";
        int kmp = kmp(oriStr,temp);
        if(kmp!=-1){
            System.out.println(kmp);
            System.out.println(oriStr.substring(kmp,kmp+temp.length()));
        }
    }

    @Test
    public void testGetNext(){
        int[] nextArr = getNext("ababc");
        for(Integer next : nextArr){
            System.out.println(next);
        }
    }

    @Test
    public void testGBK(){
        String temp = "中文123";
        char[] tempCharArr = temp.toCharArray();
        for(char tempChar : tempCharArr){
            System.out.println(tempChar);
        }
    }


    public void quickSort(int[] arr,int start,int end){
        if(start>=end){
            return;
        }
        int left=start;
        int right=end;
        int sentry = arr[end];
        while(left!=right){
            while(right>left && arr[left]<=sentry){
                left++;
            }
            while(right>left && arr[right]>=sentry){
                right--;
            }
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }
        arr[end]=arr[left];
        arr[left]=sentry;
        quickSort(arr,start,left-1);
        quickSort(arr,left+1,end);
    }

    public <E>void swap(E a,E b){
        E temp =a;
        a=b;
        b=temp;
    }

    @Test
    public void testSwap(){
        int num1 = 1;
        int num2 = 2;
        Integer num3 =3;
        Integer num4 = 4;
        List<Integer> numList1 = new ArrayList<>();
        List<Integer> numList2 = new ArrayList<>();
        numList1.add(1);

        swap(num1,num2);
        swap(num3,num4);
        swap(numList1,numList2);
        System.out.println(num1);
        System.out.println(num2);
        System.out.println(num3);
        System.out.println(num4);
        System.out.println(numList1.size());
        System.out.println(numList2.size());


    }

}
