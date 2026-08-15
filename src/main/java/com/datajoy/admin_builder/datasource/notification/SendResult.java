package com.datajoy.admin_builder.datasource.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Builder @Getter
public class SendResult {
    private SendResultType resultType;
    private String errorMessage;
}
