/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MRB
 */
public class TestLockSupport {
    static List<Thread> threadList = new ArrayList<>();
    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadList.add(Thread.currentThread());
                System.out.println("====》1.放弃执行权限："+Thread.currentThread().getName());
                LockSupport. park();
                System.out.println("=====》3.获取执行权限："+Thread.currentThread().getName());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(Thread thread : threadList){
                    System.out.println("===》2.激活线程："+thread.getName());
                    LockSupport.unpark(thread);
                }
            }
        }).start();
    }
}
