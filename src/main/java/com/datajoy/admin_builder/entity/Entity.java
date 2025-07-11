package com.datajoy.admin_builder.entity;

import com.datajoy.admin_builder.entity.code.EntityStatus;
import com.datajoy.admin_builder.entity.query.EntityQueryGenerator;
import com.datajoy.admin_builder.entity.query.EntityQueryGeneratorFactory;
import com.datajoy.admin_builder.entity.query.FailedQueryGenerationException;
import com.datajoy.admin_builder.sql.SqlParameter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="ENTITY_UQ",columnNames={"entityName"})})
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

    public List<EntitySqlQuery> generateQuery(EntityConfig config, EntityRequest params) {
        List<Map<String, Object>> contents = params.getContents();

        List<EntitySqlQuery> entitySqlQueryList = new ArrayList<>();

        for(Map<String, Object> content : contents) {
            String seq = (String) content.get(config.getSeqParamKeyName());
            EntityStatus status = EntityStatus.valueOf((String) content.get(config.getStatusParamKeyName()));

            String sql = createSql(status);

            List<SqlParameter> sqlParameters = createSqlParameters(content);

            entitySqlQueryList.add(EntitySqlQuery.createEntitySqlQuery(seq, sql, sqlParameters));
        }

        return entitySqlQueryList;
    }

    private String createSql(EntityStatus status) {
        EntityQueryGenerator entityQueryGenerator = EntityQueryGeneratorFactory.instance(status);

        try {
            return entityQueryGenerator.generate(this.tableName, this.entityColumns);
        }
        catch (FailedQueryGenerationException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<SqlParameter> createSqlParameters(Map<String, Object> content) {
        List<SqlParameter> sqlParameters = new ArrayList<>();
        int i=0;
        for(String parameterName : content.keySet()) {
            sqlParameters.add(SqlParameter.createSqlParameter(
                    parameterName,
                    i,
                    content.get(parameterName)
            ));
            i++;
        }
        return sqlParameters;
    }
}
