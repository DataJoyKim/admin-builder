package com.prometis.appbuilder.executor.notification;

public interface NotificationSender {
    SendResult send(String sendId, String to, String subject, String content);

    SendResult validate();
}
