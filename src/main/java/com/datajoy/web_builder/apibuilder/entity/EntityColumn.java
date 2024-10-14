package com.datajoy.web_builder.apibuilder.entity;

import com.datajoy.web_builder.apibuilder.entity.code.AutoValueType;
import com.datajoy.web_builder.apibuilder.entity.code.ColumnType;
import com.datajoy.web_builder.apibuilder.entity.code.NullResolveType;
import com.datajoy.web_builder.apibuilder.entity.code.SelectWhereType;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="ENTITY_COLUMN_UQ",columnNames={"COLUMN_NAME"})})
@Entity
public class EntityColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String columnName;

    @Column(nullable = false, length = 200)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private ColumnType columnType;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private NullResolveType nullResolveType;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private SelectWhereType selectWhereType;

    @Column(length = 10)
    private String selectWhereCompareOperator;

    @Column
    private Integer selectOrderByNum;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private AutoValueType insertAutoValueType;

    @Column(length = 300)
    private String insertAutoValue;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private AutoValueType updateAutoValueType;

    @Column(length = 300)
    private String updateAutoValue;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private AutoValueType deleteAutoValueType;

    @Column(length = 300)
    private String deleteAutoValue;

    private Object value;
}
