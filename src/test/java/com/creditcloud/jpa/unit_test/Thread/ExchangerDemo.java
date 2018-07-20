/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread;

/**
 *
 * @author MRB
 */
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Exchanger（交换者）是一个用于线程间协作的工具类。Exchanger用于进行线程间的数据交换。
 * 它提供一个同步点，在这个同步点两个线程可以交换彼此的数据。这两个线程通过exchange方法交换数据，
 * 如果第一个线程先执行exchange方法，它会一直等待第二个线程也执行exchange，当两个线程都到达同步点时，
 * 这两个线程就可以交换数据，将本线程生产出来的数据传递给对方。
 */
public class ExchangerDemo {

    private static final Exchanger<String> exgr = new Exchanger<String>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(new Runnable() {
            public void run(){
                try{
                    String A = "银行流水A";
                    String changeStr = exgr.exchange(A);
                    System.out.println(changeStr);
                }catch(InterruptedException e){}
            }
        });
        threadPool.execute(new Runnable() {
            public void run(){
                try{
                    String B = "银行流水B";
                    String A = exgr.exchange("1");
                    System.out.println(A.equals(B) + " A is: " + A + " B is: " + B);
                }catch(InterruptedException e){}
            }
        });
        threadPool.shutdown();
    }

}
