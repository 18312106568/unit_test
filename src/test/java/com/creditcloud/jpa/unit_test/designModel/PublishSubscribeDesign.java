package com.creditcloud.jpa.unit_test.designModel;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class PublishSubscribeDesign {

    public class Message{}

    public interface Subject {
        void registerObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers(Message message);
    }
    public interface Observer {
        void update(Message message);
    }
    public class ConcreteSubject implements Subject {
        private List<Observer> observers = new ArrayList<Observer>();
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


    public interface PromotionService{
        void issueNewUserExperienceCash(long userId);
    }

    public interface NotificationService{
        void sendInboxMessage(long userId, String msg);
    }

    public interface UserService{
        Long register(String telephone, String password);
    }

    public class UserController {
        private UserService userService; // 依赖注入
        private EventBus eventBus;
        private static final int DEFAULT_EVENTBUS_THREAD_POOL_SIZE = 20;
        public UserController() {
            //eventBus = new EventBus(); // 同步阻塞模式
            eventBus = new AsyncEventBus(Executors
                    .newFixedThreadPool(DEFAULT_EVENTBUS_THREAD_POOL_SIZE)); // 异步非阻塞模式
        }
        public void setRegObservers(List<Object> observers) {
            for (Object observer : observers) {
                eventBus.register(observer);
            }
        }
        public Long register(String telephone, String password) {
            //省略输入参数的校验代码
            //省略userService.register()异常的try-catch代码
            long userId = userService.register(telephone, password);
            eventBus.post(userId);
            return userId;
        }
    }

    /**
     * 用注解@Subscribe替代Observer
     */
    public class RegPromotionObserver {
        private PromotionService promotionService; // 依赖注入
        @Subscribe
        public void handleRegSuccess(long userId) {
            promotionService.issueNewUserExperienceCash(userId);
        }
    }
    public class RegNotificationObserver {
        private NotificationService notificationService;
        @Subscribe
        public void handleRegSuccess(long userId) {
            notificationService.sendInboxMessage(userId, "...");
        }
    }



}
