/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.loader.MyClassLoader;
import com.creditcloud.jpa.unit_test.service.LoginService;
//import com.efushui.microservice.common.http.RetrofitAssociatedServiceClass;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.springframework.util.ResourceUtils;
//import com.efushui.microservice.model.vo.ResultVO;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import retrofit2.Call;

/**
 *
 * @author MRB
 */
public class TestAsm {
    
    @Test
    public void testNewClass() throws IOException{
        //项目字节码路径
        String bytePath = MyClassLoader.getSystemResource("").getPath();
        
        //项目包路径
        String packageName = MyClassLoader.class.getPackage().getName().replaceAll("\\.", "/");
        
        //字节码文件路径
        String newClassPath = new StringBuilder(bytePath).append(packageName).append('/').toString();
        
        //创建字节码文件
        OutputStream  clzOut = new FileOutputStream(new File(newClassPath+"Programmer.class"));
        
        ClassWriter classWriter = new ClassWriter(0);
        
        classWriter.visit(Opcodes.V1_8,// java版本  
                Opcodes.ACC_PUBLIC,// 类修饰符  
                "Programmer", // 类的全限定名  
                null, "java/lang/Object", null); 
        
        AnnotationVisitor  annVistor = classWriter.visitAnnotation("Ljavax/ws/rs/Path;", true);
        annVistor.visitEnum("value", "Ljavax/ws/rs/Path;","class");
        annVistor.visitEnd();
        //创建构造方法
        MethodVisitor mv = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);  
        mv.visitCode();  
        mv.visitVarInsn(Opcodes.ALOAD, 0);  
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>","()V");  
        mv.visitInsn(Opcodes.RETURN);  
        mv.visitMaxs(1, 1);  
        mv.visitEnd();  
        
        MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "code", "(Ljava/lang/String;)Lretrofit2/Call<Lcom/efushui/microservice/model/vo/ResultVO;>;",  
                null, null);  
        AnnotationVisitor  paramAnnVistor = methodVisitor.visitParameterAnnotation(0, "Ljavax/ws/rs/Path;", true);
        paramAnnVistor.visitEnum("value", "Ljavax/ws/rs/Path;","class");
        paramAnnVistor.visitEnd();
        methodVisitor.visitCode();  
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",  
                "Ljava/io/PrintStream;");  
        methodVisitor.visitLdcInsn("I'm a Programmer,Just Coding.....");  
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",  
                "(Ljava/lang/String;)V");  
        methodVisitor.visitInsn(Opcodes.RETURN);  
        methodVisitor.visitMaxs(2, 2);  
        methodVisitor.visitEnd();  
        classWriter.visitEnd();   
        
        byte[] data = classWriter.toByteArray();  
        
        clzOut.write(data);
        clzOut.flush();
        clzOut.close();
    }
    
    @Test
    public void testLoadClass() throws IOException{
         //项目字节码路径
        String bytePath = MyClassLoader.getSystemResource("").getPath();
        
        //项目包路径
        String packageName = MyClassLoader.class.getPackage().getName().replaceAll("\\.", "/");
        
        //字节码文件路径
        String newClassPath = new StringBuilder(bytePath).append(packageName).append('/').toString();
        
        //创建字节码文件
        InputStream  clzIn = new FileInputStream(new File(newClassPath+"Programmer.class"));
        
        byte[] result = new byte[1024];  
          
        int count = clzIn.read(result);  
        // 使用自定义的类加载器将 byte字节码数组转换为对应的class对象  
        MyClassLoader loader = new MyClassLoader();  
        Class clazz = loader.defineMyClass( result, 0, count);  
        System.out.println(clazz.getCanonicalName());  
        
        
        try {  
        //调用Programmer的code方法  
            Object o= clazz.newInstance();  
            clazz.getMethod("code", null).invoke(o, null);  
        } catch (Exception e) {  
          e.printStackTrace();  
       }  
    }
    
    @Test
    public void testInterface(){
        System.out.println(LoginService.class.getSuperclass());
        System.out.println(LoginService.class.getAnnotatedSuperclass());
        AnnotatedType[] interfaces = LoginService.class.getAnnotatedInterfaces();
        Type[] genInterfaces = LoginService.class.getGenericInterfaces();
        for(AnnotatedType clz :interfaces){
            System.out.println(clz);
        }
        System.out.println(genInterfaces.length);
        
    }
    
    @Test
    public void loanClass() throws IOException{
        File file = ResourceUtils.getFile("classpath:CertificateClient.class");
        
        InputStream in = new FileInputStream(file);
        
         byte[] result = new byte[1024];  
          
        int count = in.read(result);  
        // 使用自定义的类加载器将 byte字节码数组转换为对应的class对象  
        MyClassLoader loader = new MyClassLoader();  
        Class clazz = loader.defineMyClass( result, 0, count);  
         RetrofitAssociatedServiceClass clzAnn =(RetrofitAssociatedServiceClass) 
                 clazz.getAnnotation(RetrofitAssociatedServiceClass.class);
         System.out.println(clzAnn);
         
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method: methods){
            Annotation[] anns = method.getAnnotations();
            System.out.println(method.getName());
            for(Annotation ann : anns){
                System.out.println(ann);
            }
        }
       
    }
    
    @Test
    public void testAddChar(){
        int c = 65535;
        System.out.println((char)c);
    }
    
    //javax.persistence.Entity
    @Test
    public void addClz(){
//        ClassWriter cw = new ClassWriter(0);
//        FieldVisitor fieldVisitor;
//        MethodVisitor methodVisitor;
//        AnnotationVisitor  annotationVisitor;
        System.out.println(TestAsm.class.getSimpleName());
        System.out.println(TestAsm.class.getName());
    }
}
