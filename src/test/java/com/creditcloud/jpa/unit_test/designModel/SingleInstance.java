package com.creditcloud.jpa.unit_test.designModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SingleInstance {

    /**
     * 饿汉式
     */
    public static class IdGenerator1 {
        private AtomicLong id = new AtomicLong(0);
        private static final IdGenerator1 instance = new IdGenerator1();
        private IdGenerator1() {}
        public static IdGenerator1 getInstance() {
            return instance;
        }
        public long getId() {
            return id.incrementAndGet();
        }
    }

    /**
     * 懒汉式
     */
    /**
     *
     * public class IdGenerator {
     *   private AtomicLong id = new AtomicLong(0);
     *   private static IdGenerator instance;
     *   private IdGenerator() {}
     *   public static synchronized IdGenerator getInstance() {
     *     if (instance == null) {
     *       instance = new IdGenerator();
     *     }
     *     return instance;
     *   }
     *   public long getId() {
     *     return id.incrementAndGet();
     *   }
     * }
     */

    /**
     * 双重检测
     */
    public static class IdGenerator {
        private AtomicLong id = new AtomicLong(0);
        private static IdGenerator instance;
        private IdGenerator() {}
        public static IdGenerator getInstance() {
            if (instance == null) {
                synchronized(IdGenerator.class) { // 此处为类级别的锁
                    if (instance == null) {
                        instance = new IdGenerator();
                    }
                }
            }
            return instance;
        }
        public long getId() {
            return id.incrementAndGet();
        }
    }


    /**
     * 静态内部类
     */
    /**
     * public class IdGenerator {
     *   private AtomicLong id = new AtomicLong(0);
     *   private IdGenerator() {}
     *   private static class SingletonHolder{
     *     private static final IdGenerator instance = new IdGenerator();
     *   }
     *
     *   public static IdGenerator getInstance() {
     *     return SingletonHolder.instance;
     *   }
     *
     *   public long getId() {
     *     return id.incrementAndGet();
     *   }
     * }
     */


    /**
     * 枚举
     */
    /**
     * public enum IdGenerator {
     *   INSTANCE;
     *   private AtomicLong id = new AtomicLong(0);
     *
     *   public long getId() {
     *     return id.incrementAndGet();
     *   }
     * }
     */

    public interface SharedObjectStorage{
        IdGeneratorShare load(Class clazz);
        <T> void save(T t ,Class<T> clazz);
    }

    public static class FileSharedObjectStorage implements SharedObjectStorage{
        @Override
        public IdGeneratorShare load(Class clazz) {
            return null;
        }

        @Override
        public <T> void save(T t, Class<T> clazz) {

        }

    }

    public static class DistributedLock{
        void lock(){};
        void unlock(){};
    }

    public static class IdGeneratorShare {
        private AtomicLong id = new AtomicLong(0);
        private static IdGeneratorShare instance;
        private static SharedObjectStorage storage = new  FileSharedObjectStorage(/*入参省略，比如文件地址*/);
        private static DistributedLock lock = new DistributedLock();

        private IdGeneratorShare() {}

        public synchronized static IdGeneratorShare getInstance(){
            if (instance == null) {
                    lock.lock();
                    instance = storage.load(IdGeneratorShare.class);
            }
            return instance;
        }
        public synchronized void freeInstance() {
            storage.save(this, IdGeneratorShare.class);
            instance = null; //释放对象
            lock.unlock();
        }

        public long getId() {
            return id.incrementAndGet();
        }

    }


    public static class BackendServer {
        private long serverNo;
        private String serverAddress;
        private static final int SERVER_COUNT = 3;
        private static final Map<Long, BackendServer> serverInstances = new HashMap<>();
        static {
            serverInstances.put(1L, new BackendServer(1L, "192.134.22.138:8080"));
            serverInstances.put(2L, new BackendServer(2L, "192.134.22.139:8080"));
            serverInstances.put(3L, new BackendServer(3L, "192.134.22.140:8080"));
        }
        private BackendServer(long serverNo, String serverAddress) {
            this.serverNo = serverNo;
            this.serverAddress = serverAddress;
        }
        public BackendServer getInstance(long serverNo) {
            return serverInstances.get(serverNo);
        }
        public BackendServer getRandomInstance() {
            Random r = new Random();
            int no = r.nextInt(SERVER_COUNT)+1;
            return serverInstances.get(no);
        }
    }

}
