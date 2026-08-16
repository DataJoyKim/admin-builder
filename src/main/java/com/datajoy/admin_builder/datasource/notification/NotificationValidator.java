package com.datajoy.admin_builder.datasource.notification;

import com.datajoy.admin_builder.datasource.ConnectValidation;
import com.datajoy.admin_builder.datasource.LookupKey;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationValidator {
    public ConnectValidation validateConnect(NotificationProvider metadata, Map<LookupKey, NotificationSender> dataSourceMap) {
        ConnectValidation validate = new ConnectValidation();

        LookupKey lookupKey = LookupKey.generateKey(metadata.getDataSourceName());

        NotificationSender notification = dataSourceMap.get(lookupKey);

        try {
            SendResult result = notification.validate();

            if(SendResultType.SUCCESS.equals(result.getResultType())) {
                validate.setResult(true);
            }
            else {
                validate.setResult(false);
                validate.setErrorStack(new RuntimeException("Notification Send Failed..."));
            }
        }
        catch (Exception e) {
            validate.setResult(false);
            validate.setErrorStack(e);
        }

        return validate;
    }
}
