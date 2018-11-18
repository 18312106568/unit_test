/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.rocketMQ;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
 
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
/**
 *
 * @author MRB
 */
public class Consumer1 {
 
	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_Consumer");
		consumer.setNamesrvAddr("192.168.1.113:9876");
 
		/**
		 * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
		 * 如果非第一次启动，那么按照上次消费的位置继续消费
		 */
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
 
		consumer.subscribe("TopicOrderTest", "*");
 
		consumer.registerMessageListener(new MessageListenerOrderly() {
			AtomicLong consumeTimes = new AtomicLong(0);
 
			public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
				// 设置自动提交
				context.setAutoCommit(true);
				for (MessageExt msg : msgs) {
					System.out.println(msg + ",内容：" + new String(msg.getBody()));
				}
 
				try {
					TimeUnit.SECONDS.sleep(5L);
				} catch (InterruptedException e) {
 
					e.printStackTrace();
				}
				;
 
				return ConsumeOrderlyStatus.SUCCESS;
			}
		});
 
		consumer.start();
 
		System.out.println("Consumer1 Started.");
	}
 
}