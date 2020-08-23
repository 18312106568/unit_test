package com.creditcloud.jpa.unit_test.designModel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class OpenClosePrinciple {
    public class Alert {
        private List<AlertHandler> alertHandlers = new ArrayList<>();

        public void addAlertHandler(AlertHandler alertHandler) {
            this.alertHandlers.add(alertHandler);
        }
        public void check(ApiStatInfo apiStatInfo) {
            for (AlertHandler handler : alertHandlers) {
                handler.check(apiStatInfo);
            }
        }
    }

    @Data
    public class ApiStatInfo {// 省略 constructor/getter/setter 方法
        private String api;
        private long requestCount;
        private long errorCount;
        private long durationOfSeconds;
    }

    @Data
    public class ApiLimitConfig{
        private long maxErrorCount;
        private long maxTps;
    }


    public abstract class AlertHandler {
        protected AlertRule rule;
        protected Notification notification;
        public AlertHandler(AlertRule rule, Notification notification) {
            this.rule = rule;
            this.notification = notification;
        }
        public abstract void check(ApiStatInfo apiStatInfo);
    }

    public class TpsAlertHandler extends AlertHandler {
        public TpsAlertHandler(AlertRule rule, Notification notification) {
            super(rule, notification);
        }
        @Override
        public void check(ApiStatInfo apiStatInfo) {
            long tps = apiStatInfo.getRequestCount()/ apiStatInfo.getDurationOfSeconds();
            if (tps > rule.getMatchedRule(apiStatInfo.getApi()).getMaxTps()) {
                    notification.notify(NotificationEmergencyLevel.URGENCY, "...");
            }
        }
    }

    public class ErrorAlertHandler extends AlertHandler {
        public ErrorAlertHandler(AlertRule rule, Notification notification) {
            super(rule, notification);
        }

        @Override
        public void check(ApiStatInfo apiStatInfo) {
            if (apiStatInfo.getErrorCount() > rule.getMatchedRule(apiStatInfo.getApi()).getMaxErrorCount()) {
                notification.notify(NotificationEmergencyLevel.SEVERE, "...");
            }
        }
    }


    public abstract class AlertRule{
        abstract ApiLimitConfig getMatchedRule(String api);
    }

    public abstract class Notification{
        abstract void notify(NotificationEmergencyLevel level,String msg);
    }

    public enum NotificationEmergencyLevel{
        URGENCY,
        SEVERE;
    }


//    public class ApplicationContext {
//        private AlertRule alertRule;
//        private Notification notification;
//        private Alert alert;
//
//        public void initializeBeans() {
//            alertRule = new AlertRule(/*. 省略参数.*/); // 省略一些初始化代码
//            notification = new Notification(/*. 省略参数.*/); // 省略一些初始化代码
//            alert = new Alert();
//            alert.addAlertHandler(new TpsAlertHandler(alertRule, notification));
//            alert.addAlertHandler(new ErrorAlertHandler(alertRule, notification));
//        }
//        public Alert getAlert() { return alert; }
//        // 饿汉式单例
//        private static final ApplicationContext instance = new ApplicationContext();
//        private ApplicationContext() {
//            instance.initializeBeans();
//        }
//        public static ApplicationContext getInstance() {
//            return instance;
//        }
//    }
}
