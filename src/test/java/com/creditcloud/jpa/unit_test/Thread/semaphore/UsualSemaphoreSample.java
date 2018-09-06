/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread.semaphore;

import java.util.concurrent.Semaphore;

/*
 * Semaphore（信号量）是用来控制同时访问特定资源的线程数量，它通过协调各个线程，
 * 以保证合理的使用公共资源
 * 比如数据库连接。假如有一个需求，要读取几万个文件的数据，因为都是IO密集型任务，
 * 我们可以启动几十个线程并发的读取，但是如果读到内存后，还需要存储到数据库中，
 * 而数据库的连接数只有10个，这时我们必须控制只有十个线程同时获取数据库连接保存数据，
 * 否则会报错无法获取数据库连接。这个时候，我们就可以使用Semaphore来做流控
 */
public class UsualSemaphoreSample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Action...GO!");
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new SemaphoreWorker(semaphore));
            t.start();
        }
    }
}
class SemaphoreWorker implements Runnable {
    private String name;
    private Semaphore semaphore;
    public SemaphoreWorker(Semaphore semaphore) {
        this.semaphore = semaphore;
    }
    @Override
    public void run() {
        try {
            log("is waiting for a permit!");
           semaphore.acquire();
            log("acquired a permit!");
            log("executed!");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log("released a permit!");
            semaphore.release();
            System.out.println("===================");
        }
    }
    private void log(String msg){
        if (name == null) {
            name = Thread.currentThread().getName();
        }
        System.out.println(name + " " + msg);
    }
}

