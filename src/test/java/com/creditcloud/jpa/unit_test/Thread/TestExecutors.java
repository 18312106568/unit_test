/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread;

import com.creditcloud.jpa.unit_test.TestSyncAndLock;
import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sun.misc.Unsafe;

/**
 *
 * @author MRB
 */
public class TestExecutors {
    private volatile int state = 0;
    
    private static final long stateOffset;
    
    private static Unsafe unsafe =  getUnsafe();
    
    static {
        try {
            stateOffset = getUnsafe().objectFieldOffset
                (TestExecutors.class.getDeclaredField("state"));

        } catch (Exception ex) { throw new Error(ex); }
    }
    
    @Test
    public void testNewCached() throws InterruptedException{
        int LATCH_NUM = 20;
        CountDownLatch latch = new CountDownLatch(LATCH_NUM);
        //ExecutorService service = Executors.newCachedThreadPool();
        //ExecutorService service = Executors.newFixedThreadPool(10);
        //ExecutorService service = Executors.newSingleThreadExecutor();
        ExecutorService service = Executors.newScheduledThreadPool(5);
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                compareAndSetState(state,state+1);
                System.out.println(state);
                latch.countDown();
            }
        };
        long start = System.nanoTime();
        for(int i=0;i<LATCH_NUM;i++){
            service.submit(runnable);
            
        }
        latch.await();
        long end = System.nanoTime();
        System.out.println("总共耗时："+(end-start));
        service.shutdown();
        
    }
    
    @Test
    public void testFiexdThreadPool() throws InterruptedException{
        int LATCH_NUM = Integer.MAX_VALUE;
        CountDownLatch latch = new CountDownLatch(LATCH_NUM);
        ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                compareAndSetState(state,state+1);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TestExecutors.class.getName()).log(Level.SEVERE, null, ex);
                }
                latch.countDown();
            }
        };
        long start = System.nanoTime();
        for(int i=0;i<LATCH_NUM;i++){
            service.submit(runnable);
        }
        latch.await();
        long end = System.nanoTime();
        System.out.println("总共耗时："+(end-start));
        service.shutdown();
    }
    
    private static Unsafe getUnsafe()  {
        
        Class<?> unsafeClass = Unsafe.class;

        for (Field f : unsafeClass.getDeclaredFields()) {

            if ("theUnsafe".equals(f.getName())) {

                f.setAccessible(true);
                try{
                    return (Unsafe) f.get(null);
                }catch(IllegalArgumentException|IllegalAccessException  ie){
                    return null;
                }

            }
        }
        return null;

    }
    
    protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }
}
