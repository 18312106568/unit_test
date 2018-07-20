/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.restaurant;

/**
 * 消费面包
 * @author MRB
 */
public class Eater implements Runnable {
    private ToastQueue finishedQueue;
    private int counter = 0;
    public Eater(ToastQueue finished) {
        finishedQueue = finished;
    }
    public void run(){
        try{
            while(!Thread.interrupted()) {
                Toast t = finishedQueue.take();
                //看看有没有吃到坏面包
                if(t.getId() != counter++ || t.getStatus() != Toast.Status.JAMMED) {
                    System.out.println("---->Error: " + t);
                }
                //吃面包
                else{
                    System.out.println("Chomp!" + t);
                }
            }
        }catch(InterruptedException e){
            System.out.println("Eater interrupted");
        }
        System.out.println("Eater off");
    }
}
