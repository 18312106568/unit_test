/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.restaurant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author MRB
 */
public  class ToastOMatic {

    public static void main(String[] args) throws Exception{
        ToastQueue dryQueue = new ToastQueue(),
                   butteredQueue = new ToastQueue(),
                   finishedQueue = new ToastQueue();
        ExecutorService exec = Executors.newCachedThreadPool(); 
        exec.execute(new Toaster(dryQueue));
        exec.execute(new Butterer(dryQueue, butteredQueue));
        exec.execute(new Jammed( finishedQueue, butteredQueue));
        exec.execute(new Eater(finishedQueue));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
//          Toast toast = dryQueue.take();
//          System.out.println(toast);
//          System.out.println("end");
    }

}