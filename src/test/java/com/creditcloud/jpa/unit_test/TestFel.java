package com.creditcloud.jpa.unit_test;

import com.greenpineyu.fel.Expression;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.CommonFunction;
import com.greenpineyu.fel.function.Function;
import org.junit.Test;

public class TestFel {

    @Test
    public void testCalculate(){

        long start = System.currentTimeMillis();
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        ctx.set("单价", 5000);
        ctx.set("数量", 12);
        ctx.set("运费", 7500);
        Expression exp = fel.compile("$('Math').max(单价*数量+运费,70000)",ctx);

        Object result = exp.eval(ctx);
        long end = System.currentTimeMillis();
        System.out.println(String.format("cost:%d",end-start));
        System.out.println(result);
        System.out.println(addMethod(100,1000,500));
        System.out.println(MAX(100,50));
    }

    @Test
    public void testFun(){
        Function fun = new CommonFunction() {

            public String getName() {
                return "MAX";
            }

            /*
             * 调用hello("xxx")时执行的代码
             */
            @Override
            public Object call(Object[] arguments) {
                Object msg = null;
                if(arguments!= null || arguments.length<2){
                    msg = arguments[0];
                }
                return MAX((int)arguments[0],(int)arguments[1]);
            }

        };
        FelEngine e = new FelEngineImpl();
        //添加函数到引擎中。
        e.addFun(fun);
        String exp = "MAX(6000,70000)";
        //解释执行
        Object eval = e.eval(exp);
        System.out.println("hello "+eval);
        //编译执行
        Expression compile = e.compile(exp, null);
        eval = compile.eval(null);
        System.out.println("hello "+eval);
    }


    public Object addMethod(int a,int b,int c){
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        ctx.set("单价", a);
        ctx.set("数量", b);
        ctx.set("运费", c);
        Expression exp = fel.compile("单价*数量+运费",ctx);

//        ctx = fel.getContext();
        Object result = exp.eval(ctx);
        return result;
    }

    public static Object MAX(int a,int b){
        return Math.max(a,b);
    }
}
