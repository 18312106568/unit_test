package com.creditcloud.jpa.unit_test;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import org.junit.Test;

import java.lang.reflect.Method;

public class TestJna {

    @Test
    public void jnaSayHello(){
//        Clibrary.INSTANCE.HelloWorld();
        Clibrary.INSTANCE.printf("Hello, World\n");
//        System.out.println(Clibrary.INSTANCE.getClass());
//        System.out.println(Clibrary.INSTANCE.getClass().getClassLoader());
//        Clibrary.INSTANTCE.sayHello();
    }

    @Test
    public void openDll(){
        Method method = getNativeMethod();
        System.out.println(method);
        try {
            method.invoke(null,"E:\\share\\sayHello.dll", -1);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public Method getNativeMethod(){
        Class clazz = Native.class;
        for(Method method : clazz.getDeclaredMethods()){
            if("open".equals(method.getName()) && method.getParameterCount()==2){
                method.setAccessible(true);
                return method;
            }
            continue;
        }
        return null;
    }

//    static interface Clibrary extends Library {
//        //加载libhello.so链接库
//        Clibrary INSTANCE = (Clibrary) Native.load("sayHello", Clibrary.class);
//        //此方法为链接库中的方法
//
//        //int sayHello();
//        void HelloWorld();
//    }

    static interface Clibrary extends Library {
        Clibrary INSTANCE = (Clibrary)
                Native.load((Platform.isWindows() ? "msvcrt" : "c"),
                        Clibrary.class);

        void printf(String format, Object... args);
    }

}
