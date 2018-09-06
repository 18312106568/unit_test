/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author MRB
 */
public class TestQueue {

    private ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();

    private LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
    private ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(10);
    private SynchronousQueue synchronousQueue = new SynchronousQueue();
    private PriorityBlockingQueue priorityBlockingQueue = new PriorityBlockingQueue();
    private LinkedTransferQueue linkedTransferQueue = new LinkedTransferQueue();
    private CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Test
    public void testConcurrentLinkQueue(){
        for (int i = 0; i < 10; i++) {
          concurrentLinkedQueue.add(i);
        }
    }
    
    @Test
    public void testCopyOnWrite(){
        for (int i = 0; i < 10; i++) {
           copyOnWriteArrayList.add(i);
           System.out.println(copyOnWriteArrayList.set(i, i+1));
           System.out.println(copyOnWriteArrayList.get(i));
        }
    }
    
    @Test
    public void testPriorittyBlockingQueue() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            priorityBlockingQueue.add(i);
           // arrayBlockingQueue.add(i);
        }
        while (!priorityBlockingQueue.isEmpty()) {
            System.out.println(priorityBlockingQueue.take());
        }
    }
    

    /**
     * 测试0容量阻塞队列
     *
     * @throws InterruptedException
     */
    @Test
    public void testSynchrousQueue() throws InterruptedException {

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                       System.out.println(synchronousQueue.take());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TestQueue.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
        for (int i = 0; i < 10; i++) {
            try {
                synchronousQueue.put(i);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestQueue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * 测试累加器
     *
     * @throws InterruptedException
     */
    @Test
    public void testIntegerAdder() throws InterruptedException {
        //有界队列 arrayBlockingQueue
        //无界优先队列 priorityBlockingQueue
        //默认无界队列 linkedTransferQueue
        BlockingQueue queue = priorityBlockingQueue;
        for (int i = 1; i <= 20; i++) {
            executorService.submit(new IntegerAdder(queue, i));
        }
        Thread.sleep(100);
        while (!queue.isEmpty()) {
            System.out.println(queue.take());
        }
        //System.out.println(queue.size());
    }

    static class IntegerAdder implements Runnable {

        private BlockingQueue queue;

        private Integer obj;

        public IntegerAdder(BlockingQueue queue, Integer obj) {
            this.queue = queue;
            this.obj = obj;
        }

        @Override
        public void run() {
            queue.add(obj);
        }

    }
    
    @Test
    public void testShift(){
        System.out.println(17>>>1);
        System.out.println(0>>1);
        System.out.println("s1".compareTo("s2"));
        List<Integer> intList = new ArrayList();
        for(int i=0;i<100;i++){
            intList.add(i);
        }
        System.out.println(intList.get(0));
        System.out.println((56) & 13);
        int b=0;
        int a = b;
        b=2;
        System.out.println(a);
    }

}
