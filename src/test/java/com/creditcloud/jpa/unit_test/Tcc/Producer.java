/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Tcc;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.message.Message;
 
/**
 * 发送事务消息例子
 * 
 */
public class Producer {
	public static void main(String[] args) throws MQClientException, InterruptedException {
 
		TransactionCheckListener transactionCheckListener = new TransactionCheckListenerImpl();
		TransactionMQProducer producer = new TransactionMQProducer("transaction_Producer");
		producer.setNamesrvAddr("127.0.0.1:9876;"
                        + "192.168.100.145:9876;"
                        + "192.168.100.146:9876;"
                        + "192.168.100.149:9876;"
                        + "192.168.100.239:9876");
		// 事务回查最小并发数
		producer.setCheckThreadPoolMinSize(2);
		// 事务回查最大并发数
		producer.setCheckThreadPoolMaxSize(5);
		// 队列数
		producer.setCheckRequestHoldMax(2000);
		producer.setTransactionCheckListener(transactionCheckListener);
		producer.start();
 
		// String[] tags = new String[] { "TagA", "TagB", "TagC", "TagD", "TagE"
		// };
		TransactionExecuterImpl tranExecuter = new TransactionExecuterImpl();
//                Message msg = new Message("TopicTransactionTest", 
//                                        "transaction" + 0, "KEY" + 0,
//					("Hello RocketMQ " + 0).getBytes());
//                SendResult sendResult =
//                        producer.sendMessageInTransaction(msg, tranExecuter, null);
//		System.out.println(sendResult);
		for (int i = 1; i <= 1000; i++) {
                    final int num =(i%4==0)?4:i;
                    
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            
                            try {
				Message msg = new Message("TopicTransactionTest", 
                                        "transaction" + num, "KEY" + num,
					("Hello RocketMQ " + num).getBytes());
				SendResult sendResult =
                                        producer.sendMessageInTransaction(msg, tranExecuter, null);
				System.out.println(sendResult);
                            } catch (MQClientException e) {
                                    e.printStackTrace();
                            }
                        }
                    }).start();
			
		}
 
//		for (int i = 0; i < 100000; i++) {
//			Thread.sleep(1000);
//		}
                Thread.sleep(5000);
		producer.shutdown();
 
	}
}
