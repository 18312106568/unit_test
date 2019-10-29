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

        FelEngine fel2 = new FelEngineImpl();
        FelContext ctx2 = fel2.getContext();
        ctx2.set("单价", 5000);
        ctx2.set("数量", 12);
        ctx2.set("运费", 12500);
        ctx2.set("运费", 15500);
        long end = System.currentTimeMillis();
//        System.out.println(String.format("cost:%d",end-start));
//        System.out.println(result);
//        System.out.println(addMethod(100,1000,500));
//        System.out.println(MAX(100,50));
        System.out.println(exp.eval(ctx2));
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

        Object result = exp.eval(ctx);
        return result;
    }

    public static Object MAX(int a,int b){
        return Math.max(a,b);
    }


    @Test
    public void testMblFel() throws InterruptedException {
        Expression exp = getMblExpression();
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = getMblFelContext();
        long start = System.currentTimeMillis();
        for(int i=0;i<10000;i++) {
            final int num = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ctx.set("i",num);
                    Double result =(Double) exp.eval(ctx);
                    System.out.println(result);
                }
            }).start();

        }
        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    private Expression getMblExpression(){
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = getMblFelContext();
        ctx.set("i", 1);
        Expression exp = fel.compile("P0*(1+T0*$('Math').sqrt(i/TS15))",ctx);
        return exp;
    }

    private FelContext getMblFelContext(){
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        ctx.set("P0", 0.0012D);
        ctx.set("T0", 0.5D);
        ctx.set("TS15", 900.0D);
        return ctx;
    }

    @Test
    public void testSqrt(){
        long start =System.currentTimeMillis();
        for(int i=0;i<10000;i++){
            //System.out.println(String.format("%d :%.4f-%.4f",i,0.0012D/(1+0.5*Math.sqrt(i/900.0D)),0.0012D*(1+0.5*Math.sqrt(i/900.0D))));
            System.out.println(0.0012D*(1+0.5*Math.sqrt(i/900.0D)));
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}