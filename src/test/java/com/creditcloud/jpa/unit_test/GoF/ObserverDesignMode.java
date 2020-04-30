package com.creditcloud.jpa.unit_test.GoF;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ObserverDesignMode {

    /**
     * 观察者模式模板
     */
    public interface Subject {
        void registerObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers(Message message);
    }

    public interface Observer {
        void update(Message message);
    }

    public class ConcreteSubject implements Subject {
        private List<Observer> observers = new ArrayList<>();
        @Override
        public void registerObserver(Observer observer) {
            observers.add(observer);
        }
        @Override
        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }
        @Override
        public void notifyObservers(Message message) {
            for (Observer observer : observers) {
                observer.update(message);
            }
        }
    }
    public class ConcreteObserverOne implements Observer {
        @Override
        public void update(Message message) {
            //TODO: 获取消息通知，执行自己的逻辑...
            System.out.println("ConcreteObserverOne is notified.");
        }
    }
    public class ConcreteObserverTwo implements Observer {
        @Override
        public void update(Message message) {
            //TODO: 获取消息通知，执行自己的逻辑...
            System.out.println("ConcreteObserverTwo is notified.");
        }
    }

    public  class Message{

    }


    @Test
    public  void testObserver(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        subject.registerObserver(new ConcreteObserverOne());
        subject.registerObserver(new ConcreteObserverTwo());
        subject.notifyObservers(new Message());
    }


    /**
     * 注册业务
     */
    public interface RegObserver {
        void handleRegSuccess(long userId);
    }


    public interface  PromotionService{
        boolean issueNewUserExperienceCash(long userId);
    }

    public interface NotificationService{
        void sendInboxMessage(long userId,String message);
    }

    public interface UserService{
        long register(String phone,String pwd);
    }
    public class RegPromotionObserver implements RegObserver {
        private PromotionService promotionService; // 依赖注入

        @Subscribe
        @Override
        public void handleRegSuccess(long userId) {
            promotionService.issueNewUserExperienceCash(userId);
        }
    }


    public class RegNotificationObserver implements RegObserver {
        private NotificationService notificationService;

        @Subscribe
        @Override
        public void handleRegSuccess(long userId) {
            notificationService.sendInboxMessage(userId, "Welcome...");
        }
    }
    public class UserController {
        private UserService userService; // 依赖注入

        private EventBus eventBus;
        private static final int DEFAULT_EVENTBUS_THREAD_POOL_SIZE = 20;

        public UserController(){
            //eventBus = new EventBus(); // 同步阻塞模式
            //异步非阻塞模式
            eventBus = new AsyncEventBus(
                    Executors.newFixedThreadPool(DEFAULT_EVENTBUS_THREAD_POOL_SIZE));
        }

        private List<RegObserver> regObservers = new ArrayList<>();
        // 一次性设置好，之后也不可能动态的修改
        public void setRegObservers(List<RegObserver> observers) {
            //regObservers.addAll(observers);
            for(RegObserver regObserver : observers){
                eventBus.register(regObserver);
            }
        }
        public Long register(String telephone, String password) {
            //省略输入参数的校验代码
            //省略userService.register()异常的try-catch代码
            long userId = userService.register(telephone, password);
//            for (RegObserver observer : regObservers) {
//                observer.handleRegSuccess(userId);
//            }
            eventBus.post(userId);
            return userId;
        }
    }
}
