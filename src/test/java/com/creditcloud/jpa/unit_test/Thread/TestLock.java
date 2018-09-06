/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import org.junit.Test;

/**
 *
 * @author MRB
 */
public class TestLock {
    private StampedLock stampedLock = new StampedLock();
    
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    
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
}
