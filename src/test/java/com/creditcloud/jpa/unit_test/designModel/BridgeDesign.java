package com.creditcloud.jpa.unit_test.designModel;

public class BridgeDesign {

    public enum NotificationEmergencyLevel {
        SEVERE, URGENCY, NORMAL, TRIVIAL
    }
//    public class Notification {
//        private List<String> emailAddresses;
//        private List<String> telephones;
//        private List<String> wechatIds;
//        public Notification() {}
//        public void setEmailAddress(List<String> emailAddress) {
//            this.emailAddresses = emailAddress;
//        }
//        public void setTelephones(List<String> telephones) {
//            this.telephones = telephones;
//        }
//        public void setWechatIds(List<String> wechatIds) {
//            this.wechatIds = wechatIds;
//        }
//        public void notify(NotificationEmergencyLevel level, String message) {
//            if (level.equals(NotificationEmergencyLevel.SEVERE)) {
//                //...自动语音电话
//            } else if (level.equals(NotificationEmergencyLevel.URGENCY)) {
//                //...发微信
//            } else if (level.equals(NotificationEmergencyLevel.NORMAL)) {
//                //...发邮件
//            } else if (level.equals(NotificationEmergencyLevel.TRIVIAL)) {
//                //...发邮件
//            }
//        }
//    }
    //在API监控告警的例子中，我们如下方式来使用Notification类：
   /* public class ErrorAlertHandler extends AlertHandler {
        public ErrorAlertHandler(AlertRule rule, Notification notification){
            super(rule, notification);
        }
        @Override
        public void check(ApiStatInfo apiStatInfo) {
            if (apiStatInfo.getErrorCount() > rule.getMatchedRule(apiStatInfo.getApi()).getMaxErrorCount()) {
                notification.notify(NotificationEmergencyLevel.SEVERE, "...");
            }
        }
    }


    public interface MsgSender {
        void send(String message);
    }
    public class TelephoneMsgSender implements MsgSender {
        private List<String> telephones;
        public TelephoneMsgSender(List<String> telephones) {
            this.telephones = telephones;
        }
        @Override
        public void send(String message) {
            //...
        }
    }
    public class EmailMsgSender implements MsgSender {
        @Override
        public void send(String message) {

        }
        // 与TelephoneMsgSender代码结构类似，所以省略...
    }
    public class WechatMsgSender implements MsgSender {
        @Override
        public void send(String message) {

        }
        // 与TelephoneMsgSender代码结构类似，所以省略...
    }
    public abstract class Notification {
        protected MsgSender msgSender;
        public Notification(MsgSender msgSender) {
            this.msgSender = msgSender;
        }
        public abstract void notify(String message);
    }
    public class SevereNotification extends Notification {
        public SevereNotification(MsgSender msgSender) {
            super(msgSender);
        }
        @Override
        public void notify(String message) {
            msgSender.send(message);
        }
    }
    public class UrgencyNotification extends Notification {
        public UrgencyNotification(MsgSender msgSender) {
            super(msgSender);
        }

        @Override
        public void notify(String message) {

        }
        // 与SevereNotification代码结构类似，所以省略...
    }
    public class NormalNotification extends Notification {
        public NormalNotification(MsgSender msgSender) {
            super(msgSender);
        }

        @Override
        public void notify(String message) {

        }
        // 与SevereNotification代码结构类似，所以省略...
    }
    public class TrivialNotification extends Notification {
        public TrivialNotification(MsgSender msgSender) {
            super(msgSender);
        }

        @Override
        public void notify(String message) {

        }
        // 与SevereNotification代码结构类似，所以省略...
    }

*/

}
