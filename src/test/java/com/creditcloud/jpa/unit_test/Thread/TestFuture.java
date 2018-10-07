/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.junit.Test;
/**
 *
 * @author MRB
 */
public class TestFuture {
    
    @Test
    public void testFuture() throws InterruptedException, ExecutionException{
        Callable<Integer> call = new Callable(){
            @Override
            public Object call() throws Exception {
                Thread.sleep(200);
                return 1;
            }
        };
        FutureTask<Integer> task = new FutureTask<>(call);
        new Thread(task).start();
        Integer num1=0 ,num2=0;
        if(!task.isDone()){
            System.out.println("wait......");
        }
        num2=task.get();
        System.out.println(num1+num2);
    }
}
