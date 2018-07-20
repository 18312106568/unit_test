/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Tcc;

import java.util.List;
 
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
 
/**
 * Consumer，订阅消息
 */
public class Consumer {
 
	public static void main(String[] args) throws InterruptedException, MQClientException {
            
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(50);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；单位毫秒
            //小于零:阻塞不确定的时间,  默认-1
            config.setMaxWaitMillis(1000*100);
            //在borrow(引入)一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            //return 一个jedis实例给pool时，是否检查连接可用性（ping()）
            config.setTestOnReturn(true);
            //connectionTimeout 连接超时（默认2000ms）
            //soTimeout 响应超时（默认2000ms）
            JedisPool pool = new JedisPool(config, "127.0.0.1", 6379,  2000);
            
               
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("transaction_Consumer"); 
		consumer.setNamesrvAddr("127.0.0.1:9876;"
                        + "192.168.100.145:9876;"
                        + "192.168.100.146:9876;"
                        + "192.168.100.149:9876;"
                        + "192.168.100.239:9876");
		consumer.setConsumeMessageBatchMaxSize(10);
		/**
		 * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
		 * 如果非第一次启动，那么按照上次消费的位置继续消费
		 */
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
 
		consumer.subscribe("TopicTransactionTest", "*");
                
                
 
		consumer.registerMessageListener(new MessageListenerConcurrently() {
 
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
 
				try {
 
					for (MessageExt msg : msgs) {
                                            System.out.println("key值：" + msg.getKeys());
                                            Jedis jedis = pool.getResource();
                                            if(!jedis.exists(msg.getKeys())){
                                                jedis.set(msg.getKeys(), "1");
                                            }
                                            System.out.println(msg + ",内容：" + new String(msg.getBody()));
                                            jedis.close();
					}
 
				} catch (Exception e) {
					e.printStackTrace();
 
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;// 重试
 
				}
 
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;// 成功
			}
		});
 
		consumer.start();
 
		System.out.println("transaction_Consumer Started.");
	}
}