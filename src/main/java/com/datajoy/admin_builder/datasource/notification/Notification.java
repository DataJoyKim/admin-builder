package com.datajoy.admin_builder.datasource.notification;

public interface Notification {
    SendResult send(String to, String subject, String content);

    SendResult validate();
}
