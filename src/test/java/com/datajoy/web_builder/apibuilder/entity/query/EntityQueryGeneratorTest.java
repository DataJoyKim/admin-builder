package com.datajoy.web_builder.apibuilder.entity.query;

import com.datajoy.web_builder.apibuilder.entity.EntityColumn;
import com.datajoy.web_builder.apibuilder.entity.code.ColumnType;
import com.datajoy.web_builder.apibuilder.entity.code.NullResolveType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class EntityQueryGeneratorTest {

    @Test
    void generateInsertQuery() {
        // Given
        String tableName = "goal";

        List<EntityColumn> entityColumns = new ArrayList<>();
        entityColumns.add(EntityColumn.builder()
                .columnName("goalId")
                .value(null)
                .columnType(ColumnType.NUMBER)
                .insertNullResolveType(NullResolveType.EXCEPTION)
                .build());

        entityColumns.add(EntityColumn.builder()
                .columnName("goalName")
                .value("테스트")
                .columnType(ColumnType.STRING)
                .insertNullResolveType(NullResolveType.SET_NULL)
                .build());

        entityColumns.add(EntityColumn.builder()
                .columnName("goalKind")
                .value("EVAL")
                .columnType(ColumnType.STRING)
                .insertNullResolveType(NullResolveType.SET_NULL)
                .build());

        // When
        EntityQueryGenerator generator = new InsertQuery();

        String query = generator.generate(tableName, entityColumns);

        // Then
        System.out.println(query);
    }

    @Test
    void generateUpdateQuery() {
        // Given
        String tableName = "goal";

        List<EntityColumn> entityColumns = new ArrayList<>();
        entityColumns.add(EntityColumn.builder()
                .useKey(true)
                .columnName("goalId")
                .value(1L)
                .columnType(ColumnType.NUMBER)
                .build());

        entityColumns.add(EntityColumn.builder()
                .useKey(false)
                .columnName("goalName")
                .value("테스트")
                .columnType(ColumnType.STRING)
                .build());

        entityColumns.add(EntityColumn.builder()
                .useKey(false)
                .columnName("goalKind")
                .value("EVAL")
                .columnType(ColumnType.STRING)
                .build());

        // When
        EntityQueryGenerator generator = new UpdateQuery();

        String query = generator.generate(tableName, entityColumns);

        // Then
        System.out.println(query);
    }

}