package com.creditcloud.jpa.unit_test.Thread;


import com.creditcloud.jpa.unit_test.utils.ReflectUtils;
import com.google.gson.Gson;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestInSecurity {

    ExecutorService  executorService = Executors.newFixedThreadPool(16);
    final int MAX = 20000;

    @Test
    public void testStringBuilder() throws InterruptedException {
        StringBuffer sb = new StringBuffer();
        CountDownLatch latch =new CountDownLatch(MAX);
        for(int i=0;i<MAX;i++){
            Thread task = new Thread(new Runnable() {
                @Override
                public void run() {
                    sb.append(System.currentTimeMillis());
                }
            });
           executorService.submit(task);
        }
        System.out.println(sb.toString().length());
        System.out.println(sb.toString());
    }


    @Test
    public void testCaputure() throws InterruptedException, IllegalAccessException {
        for(int i=0;i<20;i++){
            System.out.println(testHashMap());
        }

    }

    public int testHashMap() throws InterruptedException, IllegalAccessException {
        Gson gson = new Gson();
        Field field = ReflectUtils.getField(HashMap.class,"table");

        HashMap<String, Integer> map = new HashMap();
       // CountDownLatch latch =new CountDownLatch(MAX);
        String str1 = "Aa";
        String str2 = "BB";
        String str3 = "C#";
        String str4 = "D" + (char) 4;
        String str5 = "@" + (char) 128;
        String str6 = "" + (char) 63 + (char) 159;
        String str7 = "" + (char) 62 + (char) 190;
        String str8 = "" + (char) 61 + (char) 221;

        map.put(str1, Integer.MAX_VALUE);
        map.put(str2, Integer.MIN_VALUE);
        map.put(str3, Integer.MAX_VALUE);
        map.put(str4, Integer.MIN_VALUE);
        map.put(str5, Integer.MAX_VALUE);
        map.put(str6, Integer.MIN_VALUE);
        map.put(str7, Integer.MAX_VALUE);
        map.put(str8, Integer.MIN_VALUE);

//        System.out.println(map.keySet());
//        System.out.println(gson.toJson(field.get(map)));
//        System.out.println(((Object[])field.get(map)).length);
        for(int i=0;i<MAX;i++){
            String str = ""+(char)i+(char)(2112-i*31);
            Thread task = new Thread(new Runnable() {
                @Override
                public void run() {
                        map.put(str, Integer.MIN_VALUE);
                }
            });
            executorService.submit(task);

        }
        Thread.sleep(1500);

       return ((Object[])field.get(map)).length;
    }
}
