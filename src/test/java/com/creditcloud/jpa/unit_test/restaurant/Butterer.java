/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.restaurant;

/**
 * 涂上黄油
 * @author MRB
 */
public class Butterer implements Runnable{
    private ToastQueue dryQueue, butteredQueue;
    public Butterer(ToastQueue dry, ToastQueue butterer) {
        dryQueue = dry;
        butteredQueue = butterer;
    }
    public void run() {
        try{
            //从干面包中取出并涂上黄油
            while(!Thread.interrupted()) {
                Toast t = dryQueue.take();
                t.butter();
                System.out.println(t);
                butteredQueue.put(t);
            }
        }catch(InterruptedException e) {
            System.out.println("Butterer interrupted");
        }
        System.out.println("Butterer off");
    }
}
