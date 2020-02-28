 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread;

 import org.jboss.netty.util.internal.NonReentrantLock;
import org.junit.Test;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Function;

 /**
 *
 * @author MRB
 */
public class TestLock {
    private StampedLock stampedLock = new StampedLock();

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock rtl = new ReentrantLock();

    private void testReadWriteLock(){
        try{
            readWriteLock.readLock().lock();
        }finally{
            readWriteLock.readLock().unlock();
        }
    }

    @Test
    public void testCountDownLatch() throws InterruptedException{
        CountDownLatch latch = new CountDownLatch(1);
        latch.countDown();
        latch.await();
    }

    @Test
    public void testSemaphore() throws InterruptedException{
        Semaphore semaphore = new Semaphore(0);
        semaphore.release();
        semaphore.acquire();
    }

    @Test
    public void testReentrantLock(){
        TestReentrantLock testReentrantLock = new TestReentrantLock();
        testReentrantLock.addOne();
    }


    class TestReentrantLock {
        private final Lock rtl = new NonReentrantLock();
                //new ReentrantLock();
        int value = 0;
        public int get() {
            // 获取锁
            rtl.lock();
            try {
                return value;
            } finally {
                // 保证锁能释放
                rtl.unlock();
            }
        }
        public void addOne() {
            // 获取锁
            rtl.lock();
            try {
                value = 1 + get();
                System.out.println(value);
            } finally {
                // 保证锁能释放
                rtl.unlock();
            }
        }
    }

    class ObjPool<T, R> {
        final List<T> pool;
        // 用信号量实现限流器
        final Semaphore sem;
        // 构造函数
        ObjPool(int size, T t){
            pool = new Vector<T>(){};
            for(int i=0; i<size; i++){
                pool.add(t);
            }
            sem = new Semaphore(size);
        }
        // 利用对象池的对象，调用 func
        R exec(Function<T,R> func) throws InterruptedException {
            T t = null;
            sem.acquire();
            try {
                t = pool.remove(0);
                return func.apply(t);
            } finally {
                pool.add(t);
                sem.release();
            }
        }
    }
//    // 创建对象池
//    ObjPool<Long, String> pool =
//                new ObjPool<Long, String>(10, 2L);
//    // 通过对象池获取 t，之后执行
//    pool.exec(t -> {
//            System.out.println(t);
//            return t.toString();
//        })

 }
