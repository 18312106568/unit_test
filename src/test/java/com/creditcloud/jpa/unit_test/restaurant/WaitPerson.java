/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.restaurant;

/**
 *
 * @author MRB
 */
public class WaitPerson implements Runnable{
    private Restaurant restaurant;
    public WaitPerson(Restaurant r) {
        this.restaurant = r;
    }
    public void run() {
        try{
            while(!Thread.interrupted()) {
                //当食物被消费了，就阻塞当前线程
                synchronized(this){
                    while(restaurant.meal == null) {
                        wait();
                    }
                }
                //获得运行的客人线程表示可以消费食物
                System.out.println("WaitPerson got " + restaurant.meal);
                synchronized(restaurant.chef) {
                    restaurant.meal = null;
                    //唤醒厨师做菜
                    restaurant.chef.notifyAll();
                }
            }
        }catch(InterruptedException e){
            System.out.println("WaitPerson interrupted");
        }
    }
}
