package com.datajoy.admin_builder.notification;

import com.datajoy.admin_builder.executor.notification.SendResultType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder @AllArgsConstructor
public class NotificationResult {
    private SendResultType resultCode;
    private String message;
}
