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
public class Chef implements Runnable{
    private Restaurant restaurant;
    private int count = 0;
    public Chef(Restaurant r) {
        this.restaurant = r;
    }
    public void run(){
        try{
            while(!Thread.interrupted()) {
                //如果食物还没被消费，厨师不必开始做
                synchronized(this) {
                    while(restaurant.meal != null) {
                        wait();
                    }
                }
                //厨师可以做菜了
                System.out.println("Order up!");
                if(count++ == 10) {
                    System.out.println("Out of food, closing");
                    restaurant.exec.shutdownNow();
                }
                synchronized(restaurant.waitPerson) {
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
            }
        }catch(InterruptedException e){
            System.out.println("Chef interrupted");
        }
    }
}
