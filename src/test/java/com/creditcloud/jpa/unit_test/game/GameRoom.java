/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.game;

import com.creditcloud.jpa.unit_test.Thread.MyUnSafe;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Data;
import org.junit.Test;
/**
 *
 * @author MRB
 */
public class GameRoom {
    
    static final int MAX_COUNT = 10;
    ExecutorService executorService = Executors.newFixedThreadPool(MAX_COUNT);
    
    volatile int count;
    
    private static final long countOffset;
    
     static {
        try {
            countOffset = MyUnSafe.getInstance().objectFieldOffset
                (GameRoom.class.getDeclaredField("count"));

        } catch (Exception ex) { throw new Error(ex); }
    }
    
     public static void main(String args[]) throws InterruptedException{
         int eSum=0;
         int aSum=0;
         for(int i=0;i<MAX_COUNT;i++){
            GameRate rate = testGame();
            System.out.println(String.format("有效执行任务比为：%d:%d",
                     rate.getEffect(),rate.getSum()));
            eSum+=rate.getEffect();
            aSum+=rate.getSum();
         }
         System.out.println(String.format("总执行任务比为：%d:%d",
                     eSum,aSum));
     }
     
    
    public static GameRate testGame() throws InterruptedException{
        List<Player> players = new ArrayList<Player>();
        for(int i=1;i<=MAX_COUNT;i++){
            players.add(new Player(i));
        }
        GameRoom room = new GameRoom();
        int index=0;
        
        for (int i=0;i<100;i++) {            
            int rad1 = new Random().nextInt(MAX_COUNT);
            int rad2 = new Random().nextInt(MAX_COUNT);
            if(rad1==rad2){
                continue;
            }
            index++;
            room.executorService.submit(new GameRunner(room,players.get(rad1),players.get(rad2)));
        }
        Thread.sleep(1000);
        room.executorService.shutdown();
        GameRate rate = new GameRate();
        rate.setEffect(room.count);
        rate.setSum(index);
        return rate;
    }
    
    @Data
    static class GameRate{
        int sum;
        int effect;
    }
    
    static class GameRunner implements Runnable{
        GameRoom room;
        Player pyA;
        Player pyB;
        
        public GameRunner(GameRoom room,Player pyA,Player pyB){
            this.room = room;
            this.pyA = pyA;
            this.pyB = pyB;
        }
        
        @Override
        public void run() {
           ReentrantLock a =pyA.getLock();
           ReentrantLock b = pyB.getLock();
            try {
                if(a.tryLock() && b.tryLock()){
                   for(;;){
                       if(MyUnSafe.getInstance()
                           .compareAndSwapInt(room, countOffset, room.count, room.count+1)){
                           break;
                       }
                   }
                   //System.out.println("游戏开始:"+pyA.getId()+"vs"+pyB.getId());
                   Thread.sleep(1);
                }
            } catch (Exception e) {
                
            }finally{
                a.unlock();
                b.unlock();
            }
        }
    }
}
