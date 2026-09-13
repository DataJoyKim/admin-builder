package com.prometis.appbuilder.notification;

import com.prometis.appbuilder.executor.notification.SendResultType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder @AllArgsConstructor
public class NotificationResult {
    private SendResultType resultCode;
    private String message;
}
