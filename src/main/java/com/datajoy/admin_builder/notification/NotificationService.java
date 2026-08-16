package com.datajoy.admin_builder.notification;

import com.datajoy.admin_builder.datasource.LookupKey;
import com.datajoy.admin_builder.datasource.notification.DataSourceNotificationRegister;
import com.datajoy.admin_builder.datasource.notification.NotificationSender;
import com.datajoy.admin_builder.datasource.notification.SendResult;
import com.datajoy.admin_builder.datasource.notification.SendResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationResult execute(String notificationName, NotificationRequest params) {

        Notification notification = notificationRepository.findByNotificationName(notificationName)
                .orElseThrow();

        NotificationSender notificationSender = DataSourceNotificationRegister.getDataSource(LookupKey.generateKey(notification.getDataSourceName()));

        SendResult sendResult = notificationSender.send((String) params.getParams().get("to"), notification.getSubject(), notification.getContent());

        return NotificationResult.builder()
                .resultCode(sendResult.getResultType())
                .message((sendResult.getResultType() == SendResultType.SUCCESS) ? "전송 성공하였습니다." :sendResult.getErrorMessage())
                .build();
    }
}
