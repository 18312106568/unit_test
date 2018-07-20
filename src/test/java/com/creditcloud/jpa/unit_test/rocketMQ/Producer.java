/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.rocketMQ;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import java.util.List;

/**
 *
 * @author MRB
 */
public class Producer {

    public static void main(String[] args) {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("order_Producer");
            producer.setNamesrvAddr(""
                    + "192.168.1.171:9876;"
                    + "192.168.100.145:9876;"
                    + "192.168.100.146:9876;"
                    + "192.168.100.149:9876;"
                    + "192.168.100.239:9876");

            producer.start();

            // String[] tags = new String[] { "TagA", "TagB", "TagC", "TagD",
            // "TagE" };
            for (int i = 1; i <= 5; i++) {
                final int num = i;
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        Message msg = new Message("TopicOrderTest", "order_1", "KEY" + num, ("order_1 " + num).getBytes());
                        try {
                            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                                    Integer id = (Integer) arg;
                                    int index = id % mqs.size();
                                    return mqs.get(index);
                                }
                            }, 0);
                            System.out.println(sendResult);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            for (int i = 1; i <= 5; i++) {

                Message msg = new Message("TopicOrderTest", "order_1", "KEY" + i, ("order_1 " + i).getBytes());

                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Integer id = (Integer) arg;
                        int index = id % mqs.size();
                        return mqs.get(index);
                    }
                }, 1);

                System.out.println(sendResult);
            }

            for (int i = 1; i <= 5; i++) {

                final int num = i;
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        Message msg = new Message("TopicOrderTest", "order_1", "KEY" + num, ("order_1 " + num).getBytes());
                        try {
                            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                                    Integer id = (Integer) arg;
                                    int index = id % mqs.size();
                                    return mqs.get(index);
                                }
                            }, 2);
                            System.out.println(sendResult);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            producer.shutdown();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
