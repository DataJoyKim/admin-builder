package com.datajoy.admin_builder.notification;

import com.datajoy.admin_builder.datasource.LookupKey;
import com.datajoy.admin_builder.datasource.notification.DataSourceNotificationRegister;
import com.datajoy.admin_builder.executor.notification.NotificationSender;
import com.datajoy.admin_builder.executor.notification.SendResult;
import com.datajoy.admin_builder.executor.notification.SendResultType;
import com.datajoy.admin_builder.expression.ParameterExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationResult execute(String notificationName, NotificationRequest params) {

        Notification notification = notificationRepository.findByNotificationName(notificationName)
                .orElseThrow();

        NotificationMessage message = notification.createMessage(new ParameterExpression(), params);

        NotificationSender notificationSender = DataSourceNotificationRegister.getDataSource(LookupKey.generateKey(notification.getDataSourceName()));

        SendResult sendResult = notificationSender.send(message.getTo(), message.getSubject(), message.getContent());

        return NotificationResult.builder()
                .resultCode(sendResult.getResultType())
                .message((sendResult.getResultType() == SendResultType.SUCCESS) ? "전송 성공하였습니다." :sendResult.getErrorMessage())
                .build();
    }
}
