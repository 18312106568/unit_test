package com.creditcloud.jpa.unit_test.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class RunTimeProxy implements MethodInterceptor {

    //通过Enhancer 创建代理对象
    private Enhancer enhancer = new Enhancer();

    //通过Class对象获取代理对象
    public Object getProxy(Class c){
        //设置创建子类的类
        enhancer.setSuperclass(c);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = methodProxy.invokeSuper(o,objects);

        long end = System.currentTimeMillis();
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        System.out.println(String.format("%s.%s cost the time :%d",className,methodName,(end-start)));
        return result;
    }
}
