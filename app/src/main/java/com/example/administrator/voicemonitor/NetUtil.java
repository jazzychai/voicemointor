package com.example.administrator.voicemonitor;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class NetUtil {
    private static NetUtil instance = null;
    private static final String TAG = "Net";
    Object mLock;
    private NetUtil() {
        mLock = new Object();
    }

    public static NetUtil getInstance() {
        if (null == instance) {
            instance = new NetUtil();
        }
        return instance;
    }

    public void sendEmail(final String email, final String content){
        new Thread(new Runnable(){
            @Override
            public void run() {
                // 收件人电子邮箱
                String to = email;
                // 发件人电子邮箱
                String from = "send_msg112233@126.com";
                // 获取系统属性
                Properties properties = new Properties();
                // 设置邮件服务器
                properties.setProperty("mail.transport.protocol", "SMTP");
                properties.setProperty("mail.smtp.host", "smtp.126.com");
                properties.setProperty("mail.smtp.port", "25");
                properties.setProperty("mail.smtp.auth", "true");

                // 获取默认session对象
                Session session = Session.getDefaultInstance(properties,
                        new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                // 登陆邮件发送服务器的用户名和密码
                                return new PasswordAuthentication("send_msg112233@126.com","p91p3eopm7123");
                            }
                        });

                // 创建默认的 MimeMessage 对象
                MimeMessage message = new MimeMessage(session);

                // Set From: 头部头字段
                try {
                    message.setFrom(new InternetAddress(from));
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                // Set To: 头部头字段
                try {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                // Set Subject: 头部头字段
                try {
                    message.setSubject(content);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                // 设置消息体
                try {
                    message.setText(content);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                // 发送消息
                try {
                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
