/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.restaurant;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author MRB
 */
public class Toaster implements Runnable{
    private ToastQueue toastQueue;
    private int count = 0;
    private Random rand = new Random(47);
    public Toaster(ToastQueue tq){toastQueue = tq;}
    public void run() {
        try{
            while(!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(50));
                Toast t = new Toast(count++);
                System.out.println(t);
                toastQueue.put(t);
            }
        }catch(InterruptedException e) {
            System.out.println("Toaster interrupted");
        }
        System.out.println("Toaster off");
    }
}
