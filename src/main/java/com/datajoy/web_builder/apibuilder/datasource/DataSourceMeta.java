package com.datajoy.web_builder.apibuilder.datasource;

import com.datajoy.web_builder.apibuilder.datasource.database.DatabaseKind;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="DATA_SOURCE_META_UQ",columnNames={"DATA_SOURCE_NAME"})})
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

    public static DataSourceMeta createDataSource(Map<String, Object> params) {
        return DataSourceMeta.builder()
                .dataSourceName((String) params.get("dataSourceName"))
                .displayName((String) params.get("displayName"))
                .note((String) params.get("note"))
                .url((String) params.get("url"))
                .username((String) params.get("username"))
                .password((String) params.get("password"))
                .databaseKind(DatabaseKind.valueOf((String) params.get("databaseKind")))
                .maximumPoolSize(Integer.valueOf((String) params.get("maximumPoolSize")))
                .minimumIdle(Integer.valueOf((String) params.get("minimumIdle")))
                .connectionTimeout(Integer.valueOf((String) params.get("connectionTimeout")))
                .validationTimeout(Integer.valueOf((String) params.get("validationTimeout")))
                .build();
    }

    public void update(Map<String, Object> params) {
        this.dataSourceName = (String) params.get("dataSourceName");
        this.displayName = (String) params.get("displayName");
        this.note = (String) params.get("note");
        this.url = (String) params.get("url");
        this.username = (String) params.get("username");
        this.password = (String) params.get("password");
        this.databaseKind = DatabaseKind.valueOf((String) params.get("databaseKind"));
        this.maximumPoolSize = Integer.valueOf((String) params.get("maximumPoolSize"));
        this.minimumIdle = Integer.valueOf((String) params.get("minimumIdle"));
        this.connectionTimeout = Integer.valueOf((String) params.get("connectionTimeout"));
        this.validationTimeout = Integer.valueOf((String) params.get("validationTimeout"));

    }
}
