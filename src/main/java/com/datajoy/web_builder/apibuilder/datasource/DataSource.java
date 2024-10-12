package com.datajoy.web_builder.apibuilder.datasource;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="DATA_SOURCE_UQ",columnNames={"DATA_SOURCE_NAME"})})
public class DataSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String dataSourceName;

    @Column(nullable = false, length = 100)
    private String displayName;

    @Column(length = 500)
    private String note;

    @Column(nullable = false, length = 300)
    private String url;

    @Column
    private Integer port;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    public static DataSource createDataSource(Map<String, Object> params) {
        return DataSource.builder()
                .dataSourceName((String) params.get("dataSourceName"))
                .displayName((String) params.get("displayName"))
                .note((String) params.get("note"))
                .url((String) params.get("url"))
                .port(Integer.valueOf((String) params.get("port")))
                .username((String) params.get("username"))
                .password((String) params.get("password"))
                .build();
    }

    public void update(Map<String, Object> params) {
        this.dataSourceName = (String) params.get("dataSourceName");
        this.displayName = (String) params.get("displayName");
        this.note = (String) params.get("note");
        this.url = (String) params.get("url");
        this.port = Integer.valueOf((String) params.get("port"));
        this.username = (String) params.get("username");
        this.password = (String) params.get("password");
    }
}
