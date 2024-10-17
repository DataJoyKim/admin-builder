package com.datajoy.web_builder.apibuilder.entity;

import com.datajoy.web_builder.apibuilder.entity.code.EntityStatus;
import com.datajoy.web_builder.apibuilder.sql.SqlQuery;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="ENTITY_UQ",columnNames={"COLUMN_NAME"})})
@jakarta.persistence.Entity
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String entityName;

    @Column(nullable = false, length = 200)
    private String displayName;

    @Column(nullable = false, length = 100)
    private String dataSourceName;

    @Column(nullable = false, length = 100)
    private String tableName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ENTITY_NAME")
    private List<EntityColumn> entityColumns = new ArrayList<>();

    public List<SqlQuery> generateQuery(EntityParameter params, EntityConfig config) {
        List<Map<String, Object>> contents = params.getContents();
        for(Map<String, Object> content : contents) {
            EntityStatus status = EntityStatus.valueOf((String) content.get(config.getStatusParamKeyName()));
            //TODO query 생성기
        }
    }
}
