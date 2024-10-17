package com.datajoy.web_builder.apibuilder.entity;

import com.datajoy.web_builder.apibuilder.entity.code.EntityStatus;
import com.datajoy.web_builder.apibuilder.entity.query.EntityQueryGenerator;
import com.datajoy.web_builder.apibuilder.entity.query.EntityQueryGeneratorFactory;
import com.datajoy.web_builder.apibuilder.entity.query.FailedQueryGenerationException;
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
    @JoinColumn(name = "ENTITY_ID")
    private List<EntityColumn> entityColumns = new ArrayList<>();

    public List<SqlQuery> generateQuery(EntityConfig config, EntityParameter params) {
        List<Map<String, Object>> contents = params.getContents();

        List<SqlQuery> sqlQueryList = new ArrayList<>();
        for(Map<String, Object> content : contents) {
            EntityStatus status = EntityStatus.valueOf((String) content.get(config.getStatusParamKeyName()));

            EntityQueryGenerator entityQueryGenerator = EntityQueryGeneratorFactory.instance(status);

            String sql;
            try {
                sql = entityQueryGenerator.generate(this.tableName, this.entityColumns);
            }
            catch (FailedQueryGenerationException e) {
                throw new RuntimeException(e);
            }

            sqlQueryList.add(SqlQuery.builder()
                    .sql(sql)
                    .build());
        }

        return sqlQueryList;
    }
}
