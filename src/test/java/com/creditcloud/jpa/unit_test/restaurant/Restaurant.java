/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.restaurant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author MRB
 */
public class Restaurant {

    public Meal meal;
    WaitPerson waitPerson = new WaitPerson(this);
    public Chef chef = new Chef(this);
    ExecutorService exec = Executors.newCachedThreadPool();

    public Restaurant(){
        exec.execute(waitPerson);
        exec.execute(chef);
    }

    public static void main(String[] args) {
        new Restaurant();
    }

}
