/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.restaurant;

/**
 * 淋上果酱
 * @author MRB
 */
public class Jammed implements Runnable{
    private ToastQueue butteredQueue, finishedQueue;
    public Jammed(ToastQueue finished, ToastQueue buttered) {
        finishedQueue = finished;
        butteredQueue = buttered;
    }
    public void run(){
        try{
            //给涂上的黄油的面包淋上果酱
            while(!Thread.interrupted()) {
                Toast t = butteredQueue.take();
                t.jam();
                System.out.println(t);
                finishedQueue.put(t);
            }
        }catch(InterruptedException e) {
            System.out.println("Jammer interrupted");
        }
        System.out.println("Jammer off");
    }
}
