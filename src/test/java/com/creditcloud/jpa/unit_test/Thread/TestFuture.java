/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread;
import com.creditcloud.jpa.unit_test.proxy.RunTimeProxy;
import com.creditcloud.jpa.unit_test.proxy.Ship;
import com.creditcloud.jpa.unit_test.utils.DateUtils;
import com.creditcloud.jpa.unit_test.utils.ThreadStatisticsUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
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


    @Test
    public void testDoss() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future> futures = new ArrayList<>();
        for(int i=0;i<500;i++){
            //Thread.sleep(1000*i);
            //Callable<String> call = new HttpCallable(String.format("http://localhost:8700/user/autoAdd"));
            //Callable<String> call = new HttpCallable("http://www.baidu.com");
            Callable<String> call = new HttpCallable(
                    String.format("http://localhost:8700/user/save?userName=%d",(System.currentTimeMillis()-(int)Math.random()*1000)));
            FutureTask task = new FutureTask(call);
            futures.add(task);
            futures.add(executorService.submit(task));
        }

        int count =0;
        for(Future<String> future : futures){
            String result = future.get();
            if(result.equals("success")){
                count++;
            }
            //System.out.println(result);
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("the request cost %d ms,success %d",(end-start),count));

    }

    @Slf4j
    static class HttpCallable implements  Callable<String>{

        private String url;

        public HttpCallable(String url){
            this.url = url;
        }

        @Override
        public String call() throws Exception {
            long start = System.currentTimeMillis();
            log.info("=====>start the http request,now time : {} ", DateUtils.format(new Date(start)));
            String result = doRequest(this.url);
            long end = System.currentTimeMillis();
            long cost = end - start;

            ThreadStatisticsUtils.Statistics statistics = ThreadStatisticsUtils.getStatistics();
            statistics.addCost(cost);
            statistics.addCount(1);
            log.info("======>this task cost:{} and all cost:{},count:{}",cost,statistics.getCost(),statistics.getCount());

            return result;
        }
    }

    @Test
    public void testOnce() throws IOException {
        long start = System.currentTimeMillis();
        doRequest("http://www.baidu.com");
        long end = System.currentTimeMillis();
        System.out.println(String.format("do once request cost:%d ms",end-start));
    }

    public static  String doRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Test
    public void testProxy(){
        Ship ship = (Ship)new RunTimeProxy().getProxy(Ship.class);
        ship.travel();
    }
}
