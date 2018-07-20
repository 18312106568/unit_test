/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Tcc;

 
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;
 
/**
 * 执行本地事务
 */
public class TransactionExecuterImpl implements LocalTransactionExecuter {
	// private AtomicInteger transactionIndex = new AtomicInteger(1);
 
	public LocalTransactionState executeLocalTransactionBranch(final Message msg, final Object arg) {
 
		System.out.println(String.format("执行本地事务msg = %s ,tag = %s", new String(msg.getBody()),msg.getTags()) );
                System.out.println(msg.getTags().equals("transaction2"));
		System.out.println("执行本地事务arg = " + arg);
 
		String tags = msg.getTags();
 
		if (tags.equals("transaction2")) {
			System.out.println("======我的操作============，失败了  -进行ROLLBACK");
			return LocalTransactionState.ROLLBACK_MESSAGE;
		}
                System.out.println("执行成功了！");
		return LocalTransactionState.COMMIT_MESSAGE;
		// return LocalTransactionState.UNKNOW;
	}
}