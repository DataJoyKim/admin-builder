package com.datajoy.admin_builder.datasource.notification;

public interface NotificationSender {
    SendResult send(String to, String subject, String content);

    SendResult validate();
}
