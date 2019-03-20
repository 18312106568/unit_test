/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Thread;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import com.creditcloud.jpa.unit_test.proxy.RunTimeProxy;
import com.creditcloud.jpa.unit_test.proxy.Ship;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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


    @Test
    public void testDoss() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        List<FutureTask<String>> futureTasks = new ArrayList<>();
        for(int i=0;i<10;i++){
            Thread.sleep(1000*i);
            Callable<String> call = new HttpCallable("http://localhost:8700/user/autoAdd");
            //Callable<String> call = new HttpCallable("http://www.baidu.com");
            FutureTask task = new FutureTask(call);
            futureTasks.add(task);
            executorService.submit(task);
        }

        for(FutureTask<String> task : futureTasks){
            System.out.println(task.get());
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("the request cost %d ms",(end-start)));

    }

    static class HttpCallable implements  Callable<String>{

        private String url;

        public HttpCallable(String url){
            this.url = url;
        }

        @Override
        public String call() throws Exception {

            return doRequest(this.url);
        }
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
