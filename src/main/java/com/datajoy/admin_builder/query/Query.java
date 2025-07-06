package com.datajoy.admin_builder.query;

import com.datajoy.admin_builder.sql.SqlParameter;
import com.datajoy.admin_builder.sql.SqlQuery;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="QUERY_UQ",columnNames={"queryName"})})
@Entity
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String queryName;

    @Column(nullable = false, length = 200)
    private String displayName;

    @Column(nullable = false, length = 100)
    private String dataSourceName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUERY_ID")
    private List<QueryParam> queryParams = new ArrayList<>();

    @Lob
    @Column
    private String query;

    public SqlQuery generateQuery(QueryRequest params) {
        List<SqlParameter> sqlParameters = new ArrayList<>();
        int index = 0;
        for(QueryParam p : this.queryParams) {
            //TODO 자동값 기능 추가
            Map<String, Object> content = params.getContents();
            Object value = content.get(p.getParamName());

            sqlParameters.add(SqlParameter.builder()
                    .parameterName(p.getParamName())
                    .parameterIndex(index)
                    .value(value)
                    .build());

            index++;
        }

        return SqlQuery.builder()
                .sql(this.query)
                .sqlParameters(sqlParameters)
                .build();
    }
}
