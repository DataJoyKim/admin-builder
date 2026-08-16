package com.datajoy.admin_builder.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor @Builder
public class NotificationRequest {
    private Map<String, Object> params;
}
