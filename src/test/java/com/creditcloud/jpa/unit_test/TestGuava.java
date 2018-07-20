/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.entity.Product;
import com.creditcloud.jpa.unit_test.entity.base.EntityId;
import com.creditcloud.jpa.unit_test.repository.ProductRepository;
import com.google.common.util.concurrent.Striped;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author MRB
 */
public class TestGuava extends UnitTestApplicationTests {
    
    private static final Striped<Lock> striped = Striped.lazyWeakLock(127);

    @Autowired
    ProductRepository productRepository;

    /**
     * 购买产品
     *
     * @param user 用户
     * @param buyAmount 购买金额
     * @param productId 产品编号
     */
    public void buy(String user, Integer buyAmount, String productId) throws InterruptedException {
        Lock lock = striped.get(productId);//获取锁
        try {
            lock.lock();
            System.out.println(user + ":开始购买【" + productId + "】的产品");
            Product product = productRepository.findOne(productId);
            Thread.sleep(2000);//睡眠5秒
            if (product.getTotalAmount() > 0 && product.getTotalAmount() >= buyAmount) {
                int residual = product.getTotalAmount() - buyAmount;
                product.setTotalAmount(residual);//更新数据库
                System.out.println(user + ":成功购买【" + productId + "】产品,产品剩余价值为【" + residual + "】");
                productRepository.save(product);
            } else {
                System.out.println(user + ":购买【" + productId + "】产品失败，产品剩余价值为【" + product.getTotalAmount() + "】");
            }
        } finally{
            lock.unlock();//释放锁
        }
        
    }

    @Test
    public void testGuava() throws InterruptedException {
        //运行开始时间
        long startTime = System.currentTimeMillis();
        //这个类主要是，使多个线程同时进行工作,如果不了解建议网上搜索相关的文章进行学习
        final CyclicBarrier barrier = new CyclicBarrier(2);
        //不限制大小的线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        final String user1 = "张三";
        final String user2 = "李四";
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    barrier.await();
                    buy(user1, 10000, new String("1"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    barrier.await();
                    buy(user2, 10000,new String("1"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        pool.shutdown();
        while (!pool.isTerminated()) {
            Thread.sleep(1);
        }
        System.out.println("运行时间为：【" + (System.currentTimeMillis() - startTime) + "】毫秒");
    }

}
