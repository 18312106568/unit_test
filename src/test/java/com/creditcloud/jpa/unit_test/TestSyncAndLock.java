/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sun.misc.Unsafe;

/**
 *
 * @author MRB
 */
public class TestSyncAndLock {

    private static final ReentrantLock testLock = new ReentrantLock();

    private static Object testSync = new Object();

    private static Unsafe unsafe =  getUnsafe();
    
    private volatile int state = 0;
    
    private static final long stateOffset;
    
    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                (TestSyncAndLock.class.getDeclaredField("state"));

        } catch (Exception ex) { throw new Error(ex); }
    }
    
    
    
    public void testSyncAndLock() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(3);
        for(int i=0;i<3;i++){
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    testLock(Thread.currentThread());
                    latch.countDown();
                    
                }
            });
            t1.start();
        }
        for(int i=0;i<2;i++){
            Thread t3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    testSync(Thread.currentThread());
                }
            });
            t3.start();
        }
        
        latch.await();
    }

    public static void testLock(Thread thread) {
        try {
            testLock.lock();
            System.out.println("线程" + thread.getName() + "获取锁");
        } catch (Exception e) {

        } finally {
            System.out.println("线程" + thread.getName() + "释放锁");
            testLock.unlock();
        }
    }

    public synchronized static void testSync(Thread thread) {
        System.out.println("当前执行线程：" + thread.getName());
    }

    @Test
    public void testSync1() throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                testScopeSync(Thread.currentThread());
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                testScopeSync(Thread.currentThread());
            }
        });
        t1.start();
        t2.start();
        Thread.sleep(100);
    }

    public static void testScopeSync(Thread thread) {
        synchronized (testSync) {
            for (int i = 1; i < 6; i++) {
                System.out.println("1" + "-" + thread.getName());
            }
        }
    }

    @Test
    public void testUnsafe() throws InterruptedException {
         final CountDownLatch latch = new CountDownLatch(2);
            for(int i=0;i<2;i++){
            Thread t1 = new Thread(new Runnable() {
               @Override
               public void run() {
                   if(compareAndSetState(0,1)){
                       System.out.println(Thread.currentThread().getName());
                      
                   }else{
                        System.out.println("CAS失败了！");
                   }
                   latch.countDown();
               }
           });
           t1.start();
         }
        latch.await();
    }
    
    protected final void setState(int newState) {
        state = newState;
    }
    
    protected final int getState() {
        return state;
    }
    
     protected boolean isHeldExclusively() {
        return state == 1;
    }

    protected final boolean compareAndSetState(int expect, int update) {
        // See below for intrinsics setup to support this
        //System.out.println(this);
        System.out.println(stateOffset+"-"+state);
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }
    public static Unsafe getUnsafe()  {
        
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
    
    
    
    @Test
    public void testPark() throws InterruptedException{
        final CountDownLatch latch = new CountDownLatch(2);
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                lock(Thread.currentThread());
                System.out.println("我是："+Thread.currentThread().getName());
                latch.countDown();
            }
        });
       Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
               System.out.println("这是"+Thread.currentThread().getName()+t1.getState());
               System.out.println("我是："+Thread.currentThread().getName()+"-"+unlock(t1)+"1"+Thread.currentThread().getState());
               latch.countDown();
            }
            
        });
       t1.start();
       t2.start();
       latch.await();
    }
    
    public boolean lock(Thread thread){
        LockSupport.park();
        return Thread.interrupted();
    }
    
    public boolean unlock(Thread thread){
        LockSupport.unpark(thread);
        return Thread.interrupted();
    }
    
    @Test
    public void testReentation(){
        String s1 = "S1";
        String s2 = s1;
        s2 +="s2" ;
        System.out.println(s1);
        System.out.println(s2);
         
        StringBuilder sb1 = new StringBuilder("String");
        StringBuilder sb2 = sb1;
        sb2 = sb2.append("s2");
        System.out.println(sb1.toString());
        System.out.println(sb2.toString());
        
        int num1 = 100000;
        int num2 = num1;
        num2++;
        System.out.println(num1);
        System.out.println(num2);
        
        Integer integer1 = new Integer(10000000);
        Integer integer2 = integer1;
        integer2++;
        System.out.println(integer1);
        System.out.println(integer2);
        
        BigDecimal decimal1 = new BigDecimal(1.75f);
        BigDecimal decimal2 = decimal1;
        decimal2 = decimal2.add(BigDecimal.ONE);
        System.out.println(decimal1);
        System.out.println(decimal2);
    }
}
