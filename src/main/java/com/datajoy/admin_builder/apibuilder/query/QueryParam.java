package com.datajoy.admin_builder.apibuilder.query;

import com.datajoy.admin_builder.apibuilder.query.code.AutoValueType;
import com.datajoy.admin_builder.apibuilder.query.code.InOut;
import com.datajoy.admin_builder.apibuilder.query.code.ParamType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="ENTITY_COLUMN_UQ",columnNames={"ENTITY_ID","COLUMN_NAME"})})
@Entity
public class QueryParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="QUERY_ID", nullable = false)
    private Long queryId;

    @Column(nullable = false, length = 200)
    private String paramName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private ParamType paramType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private AutoValueType autoValueType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private InOut inOut;
}
