package com.datajoy.admin_builder.notification;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="NOTIFICATION_UQ",columnNames={"notificationName"})})
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String notificationName;

    @Column(nullable = false, length = 200)
    private String displayName;

    @Column(nullable = false, length = 100)
    private String dataSourceName;

    @Column(length = 1000)
    private String subject;

    @Lob
    @Column
    private String content;

    public void update(
            String notificationName,
            String displayName,
            String dataSourceName,
            String subject,
            String content
    ) {
        this.notificationName = notificationName;
        this.displayName = displayName;
        this.dataSourceName = dataSourceName;
        this.subject = subject;
        this.content = content;
    }
}
