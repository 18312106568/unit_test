package com.creditcloud.jpa.unit_test.proxy;

import lombok.Data;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;

public class TestCglib {

    @Test
    public void testStudentCglib(){
        Student student = new StudentInterceptor().newInstance(Student.class);
        System.out.println(student.name);
        System.out.println(student.getName());
    }

    static class StudentInterceptor implements MethodInterceptor{
        public  <T>T newInstance( Class<T> clazz ){
            try{
                Enhancer e = new Enhancer();
                e.setSuperclass(clazz);
                e.setCallback(this);
                return (T)e.create();
            }catch( Throwable e ){
                e.printStackTrace();
                throw new Error(e.getMessage());
            }

        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("123");
            return methodProxy.invokeSuper(o,objects);
        }
    }


    //学生
    @Data
    static class Student {
        String name;
        Integer age;
    }
}
