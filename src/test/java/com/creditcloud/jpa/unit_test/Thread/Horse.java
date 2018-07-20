/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread;

/**
 *
 * @author MRB
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * CyclicBarrier 的字面意思是可循环使用（Cyclic）的屏障（Barrier）。它要做的事情是，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，
 * 直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活。CyclicBarrier默认的构造方法是CyclicBarrier(int parties)，
 * 其参数表示屏障拦截的线程数量，每个线程调用await方法告诉CyclicBarrier我已经到达了屏障，然后当前线程被阻塞。
 */
//赛马
public class Horse implements Runnable {
    //赛马的编号
    private static int counter = 0;
    private final int id = counter++;
    //跑过的步数
    private int strides = 0;
    private static Random rand = new Random(47);
    private static CyclicBarrier barrier;
    public Horse(CyclicBarrier barrier) {this.barrier = barrier;}
    public synchronized int getStrides() {return strides;}
    public void run() {
        try{
            //当现成获得运行权限时
            while(!Thread.interrupted()) {
                synchronized(this) {
                    strides += rand.nextInt(10);
                }
                //当前线程达到屏障点
                barrier.await();
            }
        }catch(InterruptedException e) {

        }catch(BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
    public String toString(){return "Horse " + id + "";}
    //用*号打印出赛马跑的轨迹
    public String tracks() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < getStrides(); i++) {
            s.append("*");
        }
        s.append(id);
        return s.toString();
    }
}

