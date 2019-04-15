package com.creditcloud.jpa.unit_test.Thread;

import org.junit.Test;

import java.text.DateFormat;
import java.util.Random;


public class TestThreadLocal {

    @Test
    public void testCounter() throws InterruptedException {
        for(int i=0;i<5;i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(String.format("name:%s :count:%d",
                            Thread.currentThread().getName(),CounterUtils.getCount()));
                }
            });
            thread.start();

        }
        Thread.sleep(100);
        System.out.println("end");

    }

    static class CounterUtils {
        public static Integer count;
        private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

        static Integer getCount(){
            count = threadLocal.get();
            if(count==null){
                count = (int) Math.random()*10;
                threadLocal.set(count);
            }
            return count;
        }
    }
}
