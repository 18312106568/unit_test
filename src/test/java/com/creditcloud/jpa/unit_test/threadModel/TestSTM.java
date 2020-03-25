package com.creditcloud.jpa.unit_test.threadModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class TestSTM {

    // 带版本号的对象引用
    static public final class VersionedRef<T> {
        final T value;
        final long version;
        // 构造方法
        public VersionedRef(T value, long version) {
            this.value = value;
            this.version = version;
        }
    }
    // 支持事务的引用
    static public class TxnRef<T> {
        // 当前数据，带版本号
        volatile VersionedRef curRef;
        // 构造方法
        public TxnRef(T value) {
            this.curRef = new VersionedRef(value, 0L);
        }
        // 获取当前事务中的数据
        public T getValue(Txn txn) {
            return txn.get(this);
        }
        // 在当前事务中设置数据
        public void setValue(T value, Txn txn) {
            txn.set(this, value);
        }
    }


    // 事务接口
    public interface Txn {
        <T> T get(TxnRef<T> ref);
        <T> void set(TxnRef<T> ref, T value);
    }
    //STM 事务实现类
    static public final class STMTxn implements Txn {
        // 事务 ID 生成器
        private static AtomicLong txnSeq = new AtomicLong(0);

        // 当前事务所有的相关数据
        private Map<TxnRef, VersionedRef> inTxnMap = new HashMap<>();
        // 当前事务所有需要修改的数据
        private Map<TxnRef, Object> writeMap = new HashMap<>();
        // 当前事务 ID
        private long txnId;

        // 构造函数，自动生成当前事务 ID
        STMTxn() {
            txnId = txnSeq.incrementAndGet();
        }

        // 获取当前事务中的数据
        @Override
        public <T> T get(TxnRef<T> ref) {
            // 将需要读取的数据，加入 inTxnMap
            if (!inTxnMap.containsKey(ref)) {
                inTxnMap.put(ref, ref.curRef);
            }
            return (T) inTxnMap.get(ref).value;
        }

        // 在当前事务中修改数据
        @Override
        public <T> void set(TxnRef<T> ref, T value) {
            // 将需要修改的数据，加入 inTxnMap
            if (!inTxnMap.containsKey(ref)) {
                inTxnMap.put(ref, ref.curRef);
            }
            writeMap.put(ref, value);
        }

        // 提交事务
        boolean commit() {
            synchronized (STM.commitLock) {
                // 是否校验通过
                boolean isValid = true;
                // 校验所有读过的数据是否发生过变化
                for (Map.Entry<TxnRef, VersionedRef> entry : inTxnMap.entrySet()) {
                    VersionedRef curRef = entry.getKey().curRef;
                    VersionedRef readRef = entry.getValue();
                    // 通过版本号来验证数据是否发生过变化
                    if (curRef.version != readRef.version) {
                        isValid = false;
                        break;
                    }
                }
                // 如果校验通过，则所有更改生效
                if (isValid) {
                    writeMap.forEach((k, v) -> {
                        k.curRef = new VersionedRef(v, txnId);
                    });
                }
                return isValid;
            }
        }
    }


    @FunctionalInterface
    public interface TxnRunnable {
        void run(Txn txn);
    }
    //STM
    static public final class STM {
        // 提交数据需要用到的全局锁
        static final Object commitLock = new Object();
        // 私有化构造方法
        private STM() {

        }

        // 原子化提交方法
        public static void atomic(TxnRunnable action){
            boolean committed = false;
            // 如果没有提交成功，则一直重试
            while (!committed) {
                // 创建新的事务
                STMTxn txn = new STMTxn();
                // 执行业务逻辑
                action.run(txn);
                // 提交事务
                committed = txn.commit();
            }
        }
    }

    class Account {
        // 余额
        private TxnRef<Integer> balance;
        // 构造方法
        public Account(int balance) {
            this.balance = new TxnRef<Integer>(balance);
        }
        // 转账操作
        public void transfer(Account target, int amt){
            STM.atomic((txn)->{
                Integer from = balance.getValue(txn);
                balance.setValue(from-amt, txn);
                Integer to = target.balance.getValue(txn);
                target.balance.setValue(to+amt, txn);
            });
        }
    }
}
