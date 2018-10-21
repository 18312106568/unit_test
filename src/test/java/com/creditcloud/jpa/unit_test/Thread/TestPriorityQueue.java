/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MRB
 */
public class TestPriorityQueue {
    static PriorityBlockingQueue<PrepareThread> queue = new PriorityBlockingQueue();
    public static void main(String args[]) throws InterruptedException{
        PrepareThread p1 = new PrepareThread("p1", 0, 3);
        PrepareThread p2 = new PrepareThread("p2", 2, 6);
        PrepareThread p3 = new PrepareThread("p3", 4, 4);
        PrepareThread p4 = new PrepareThread("p4", 6, 11);
        PrepareThread p5 = new PrepareThread("p5", 8, 10);
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
       // Thread.sleep(1);
        //System.out.println(queue.size());
        int count = 0;
        while(true){
            if(!queue.isEmpty()){
                PrepareThread t = queue.take();
                Thread.sleep(t.serviceTime);
                System.out.println(t.name);
                count++;
            }
            if(count==5){
                break;
            }
        }
    }
    
    
    static class PrepareThread extends Thread implements Comparable<PrepareThread>{
        String name;
        int readyTime;
        int serviceTime;
        
        public PrepareThread(String name,int readyTime,int serviceTime){
            this.name = name;
            this.readyTime = readyTime;
            this.serviceTime = serviceTime;
        }
        
        @Override
        public int compareTo(PrepareThread o) {
            return serviceTime<o.serviceTime?-1:(serviceTime==o.serviceTime)?0:1;
        }
        
        @Override
        public void run(){
            try {
                Thread.sleep(readyTime);
                queue.add(this);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestPriorityQueue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
