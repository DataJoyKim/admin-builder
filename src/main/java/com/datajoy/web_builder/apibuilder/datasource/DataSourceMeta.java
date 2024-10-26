package com.datajoy.web_builder.apibuilder.datasource;

import com.datajoy.web_builder.apibuilder.datasource.database.DatabaseKind;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="DATA_SOURCE_META_UQ",columnNames={"DATA_SOURCE_NAME"})})
@Entity
public class DataSourceMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String dataSourceName;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 100)
    private DatabaseKind databaseKind;

    @Column(nullable = false, length = 100)
    private String displayName;

    @Column(length = 500)
    private String note;

    @Column(nullable = false, length = 300)
    private String url;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column
    private Integer maximumPoolSize = 100;

    @Column
    private Integer minimumIdle = 100;

    @Column
    private Integer connectionTimeout = 30000;

    @Column
    private Integer validationTimeout = 5000;
}
