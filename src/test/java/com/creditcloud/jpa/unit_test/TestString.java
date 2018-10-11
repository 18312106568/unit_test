/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.rule.JunitPerfRule;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Scanner;
import org.junit.Test;
import org.junit.Rule;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author MRB
 */
public class TestString {
    
    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule();
    
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
}
