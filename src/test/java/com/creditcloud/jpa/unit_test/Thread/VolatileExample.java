package com.creditcloud.jpa.unit_test.Thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class VolatileExample {
    ConcurrentMap<String,Integer> concurrentMap;
    int x = 0;
    volatile boolean v = false;
     public VolatileExample(ConcurrentMap<String,Integer> concurrentMap){
         this.concurrentMap = concurrentMap;
     }
    public void writer() {
        x = 42;
        v = true;
    }
    public void reader() {
        concurrentMap.put(v+"-"+x,1);
    }

    public static void main(String args[]) throws InterruptedException {
        ConcurrentMap<String,Integer> concurrentMap = new ConcurrentHashMap<>();
        for(int i=0;i<10000;i++){
            VolatileExample volatileExample = new VolatileExample(concurrentMap);
            Thread readThread = new Thread(() -> volatileExample.reader());

            Thread writeThread = new Thread(() -> volatileExample.writer());
            writeThread.start();
            readThread.start();

            writeThread.join();
            readThread.join();

        }
        Thread.sleep(1000);
        for(String key : ((ConcurrentHashMap<String, Integer>) concurrentMap).keySet()){
            System.out.println(key);
        }
    }
}
