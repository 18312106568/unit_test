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
import java.util.*;
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


    @Test
    public void testTask() throws ExecutionException, InterruptedException {
        // 创建任务 T2 的 FutureTask
        FutureTask<String> ft2
                = new FutureTask<>(new T2Task());
        // 创建任务 T1 的 FutureTask
        FutureTask<String> ft1
                = new FutureTask<>(new T1Task(ft2));
        // 线程 T1 执行任务 ft1
        Thread T1 = new Thread(ft1);
        T1.start();
        // 线程 T2 执行任务 ft2
        Thread T2 = new Thread(ft2);
        T2.start();
        // 等待线程 T1 执行结果
        System.out.println(ft1.get());
    }


    class T1Task implements Callable<String>{
        FutureTask<String> ft2;
        // T1 任务需要 T2 任务的 FutureTask
        T1Task(FutureTask<String> ft2){
            this.ft2 = ft2;
        }
        @Override
        public String call() throws Exception {
            System.out.println("T1: 洗水壶...");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("T1: 水壶洗好！");

            System.out.println("T1: 烧开水...");
            TimeUnit.SECONDS.sleep(15);
            System.out.println("T1: 开水烧好！");
            // 获取 T2 线程的茶叶
            String tf = ft2.get();
            System.out.println("T1: 拿到茶叶:"+tf);

            System.out.println("T1: 泡茶...");
            return " 上茶:" + tf;
        }



    }
    // T2Task 需要执行的任务:
    // 洗茶壶、洗茶杯、拿茶叶
    class T2Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("T2: 洗茶壶...");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("T2: 茶壶洗好！");

            System.out.println("T2: 洗茶杯...");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("T2: 茶杯洗好！");

            System.out.println("T2: 拿茶叶...");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("T2: 拿到茶叶！");
            return " 龙井 ";
        }
    }





    @Test
    public void testForkJoin(){
        String[] fc = {"hello world",
                "hello me",
                "hello fork",
                "hello join",
                "fork join in world"};
        // 创建 ForkJoin 线程池
        ForkJoinPool fjp =
                new ForkJoinPool(3);
        // 创建任务
        TestFuture.MR mr = new TestFuture.MR(
                fc, 0, fc.length);
        // 启动任务
        Map<String, Long> result =
                fjp.invoke(mr);
        // 输出结果
        result.forEach((k, v)->
                System.out.println(k+":"+v));
    }




    //MR 模拟类
    static class MR extends
            RecursiveTask<Map<String, Long>> {
        private String[] fc;
        private int start, end;
        // 构造函数
        MR(String[] fc, int fr, int to){
            this.fc = fc;
            this.start = fr;
            this.end = to;
        }
        @Override protected
        Map<String, Long> compute(){
            if (end - start == 1) {
                return calc(fc[start]);
            } else {
                int mid = (start+end)/2;
                MR mr1 = new MR(
                        fc, start, mid);
                mr1.fork();
                MR mr2 = new MR(
                        fc, mid, end);
                // 计算子任务，并返回合并的结果
                return merge(mr2.compute(),
                        mr1.join());
            }
        }
        // 合并结果
        private Map<String, Long> merge(
                Map<String, Long> r1,
                Map<String, Long> r2) {
            Map<String, Long> result =
                    new HashMap<>();
            result.putAll(r1);
            // 合并结果
            r2.forEach((k, v) -> {
                Long c = result.get(k);
                if (c != null)
                    result.put(k, c+v);
                else
                    result.put(k, v);
            });
            return result;
        }
        // 统计单词数量
        private Map<String, Long>
        calc(String line) {
            Map<String, Long> result =
                    new HashMap<>();
            // 分割单词
            String [] words =
                    line.split("\\s+");
            // 统计单词数量
            for (String w : words) {
                Long v = result.get(w);
                if (v != null)
                    result.put(w, v+1);
                else
                    result.put(w, 1L);
            }
            return result;
        }
    }
}
