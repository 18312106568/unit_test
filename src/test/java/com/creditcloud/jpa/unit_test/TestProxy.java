/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.proxy.dynamic.CustomInvocationHandler;
import com.creditcloud.jpa.unit_test.proxy.dynamic.HelloWorld;
import org.junit.Test;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

import java.util.Iterator;
import java.util.Queue;

/**
 *
 * @author MRB
 */
public class TestProxy {

    /**
     * 动态代理
     */
    @Test
    public  void testDynamic() {  
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");  
        HelloWorld proxy = (HelloWorld)new CustomInvocationHandler().getInstance(HelloWorld.class);
       
        proxy.sayHello("Mikan");  
        System.out.println(RepositoryFactoryBeanSupport.class);
        System.out.println(SimpleJpaRepository.class);
        System.out.println(Repository.class);
    }

    /**
     * 动态规划
     */
    public void testDynamicPlan(){


    }


    public int knap(int[] arr,int w){

        boolean[][] states = new boolean[arr.length][w+1];
        states[0][0]=true;
        if(arr[0]<=w){
            states[0][arr[0]]=true;
        }
        for(int i=1;i<arr.length;i++){
            for(int j=0;j<=w;j++){
                if(states[i-1][j]){
                    states[i][j]=true;
                }
            }
            for(int k=0;k<=w-arr[i];k++){
                if(states[i-1][k]){
                    states[i-1][k+arr[i]]=true;
                }
            }
        }

        for(int i=w;i>0;i--){
            if(states[n-1][i]){
                return i;
            }
        }
        return 0;
    }

    public class FlashBack{
        final int LIMIT = 10;
        int MAX;
        int LEN;
        boolean state[][];

        public FlashBack(int[] arr){
            LEN = arr.length;
            state = new boolean[LEN][LIMIT+1];
        }

        public void select(int[] arr,int cursor,int weight){
            if(cursor==LEN ||weight==LIMIT){
                if(weight<=LIMIT && weight>MAX) {
                    MAX = weight;
                }
                return;
            }
            if( state[cursor][weight]){
                return;
            }
            state[cursor][weight]=true;
            select(arr,cursor+1,weight);
            if(weight+arr[cursor]<=LIMIT) {
                select(arr, cursor + 1, weight + arr[cursor]);
            }
        }

        public void deepSelect(int[] arr, int start, int weight, Queue<Integer> queue){

            if(weight>=LIMIT||start==arr.length){
                if(weight==LIMIT){
                    Iterator it = queue.iterator();
                    while(it.hasNext()){
                        System.out.print(it.next()+" ");
                    }
                    System.out.println();
                }
                return;
            }
            int lastSelect = 0;
            for(int i=start;i<arr.length;i++){
                if(arr[i]==lastSelect){
                    continue;
                }
                queue.add(arr[start]);
                deepSelect(arr,i+1,weight+arr[i],queue);
                lastSelect = queue.remove();
            }

        }
    }
    
}
