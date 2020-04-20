/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Properties;
/**
 *
 * @author MRB
 */
public class TestClassLoader {

    public static void main(String[] args){
        System.out.println("BootstrapClassLoader 的加载路径: ");

        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for(URL url : urls) {
            System.out.println(url);
        }
        System.out.println("----------------------------");

        URLClassLoader extClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader().getParent();

        System.out.println(extClassLoader);
        System.out.println("扩展类加载器 的加载路径: ");

        urls = extClassLoader.getURLs();
        for(URL url : urls)
                System.out.println(url);

        System.out.println("----------------------------");

        //取得应用(系统)类加载器
        URLClassLoader appClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();

        System.out.println(appClassLoader);
        System.out.println("应用(系统)类加载器 的加载路径: ");

        urls = appClassLoader.getURLs();
        for(URL url : urls) {
            System.out.println(url);
        }

        System.out.println("----------------------------");

        URLClassLoader bootLoader = (URLClassLoader) extClassLoader.getParent();
        System.err.println(bootLoader);

    }

    @Test
    public void testSystemProperty() throws InterruptedException{
        Properties pros = System.getProperties();
        pros.wait();
        pros.notify();
        for(Map.Entry<Object,Object> entry : pros.entrySet()){
            System.out.println(entry.getKey()+"<---------->"+entry.getValue());
        }
        System.out.println("<------------>");
        System.out.println(System.getProperty("file.encoding"));
    }

    @Test
    public void testClassLoader(){
        System.out.println(String.class.getClassLoader());
        System.out.println(TestClassLoader.class.getClassLoader());
    }


    @Test
    public void testCompilerAndRun() {
        System.out.println(TestString.name);
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        String javaAbsolutePath = "E:\\tmp\\TestString.java";
        int status = javac.run(null, null, null, "-encoding", "UTF-8", "-classpath", javaAbsolutePath.toString(), javaAbsolutePath);
        if(status!=0){
            System.out.println("没有编译成功！");
        }
        try {
            this.getClass().getClassLoader().loadClass("com.creditcloud.jpa.unit_test.TestString");
            System.out.println(TestString.name);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
