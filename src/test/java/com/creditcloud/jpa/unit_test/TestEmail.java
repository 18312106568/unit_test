package com.creditcloud.jpa.unit_test;


import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class TestEmail {

    @Test
    public void testQQmail() throws MessagingException {
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.qq.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(prop);
        session.setDebug(true);
        Transport ts = session.getTransport();
        ts.connect("smtp.qq.com", "1795045875@qq.com", "dknevantmdkfejgd");
        //4、创建邮件
        Message message = createMixedMail(session);
         //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    private Message createMixedMail(Session session) throws MessagingException {
        Message message = new MimeMessage(session);
         //设置邮件的基本信息
         message.setFrom(new InternetAddress("1795045875@qq.com"));
         message.setRecipient(Message.RecipientType.TO, new InternetAddress("315077558@qq.com"));
         message.setSubject("普通邮件");
        //正文
         MimeBodyPart text = new MimeBodyPart();
         message.setText("元宵节快乐!");
         return message;

    }
}
