package com.datajoy.admin_builder.entity.query;

import com.datajoy.admin_builder.entity.EntityColumn;

import java.util.ArrayList;
import java.util.List;

public class DeleteQuery extends AbstractEntityQuery {
    @Override
    public String generateQuery(String tableName, List<EntityColumn> entityColumns) {
        List<EntityColumn> resolvedEntityColumns = resolveNull(entityColumns);

        List<EntityColumn> keyColumns = new ArrayList<>();

        for(EntityColumn col : resolvedEntityColumns) {
            if(col.getUseKey()) {
                keyColumns.add(col);
            }
        }

        return "delete " + tableName + System.lineSeparator() +
                "where 1=1 " + System.lineSeparator() +
                where(keyColumns) +
                ";";
    }

    private String where(List<EntityColumn> keyColumns) {
        StringBuilder queryStr = new StringBuilder();

        for(EntityColumn col : keyColumns) {
            queryStr.append(tabSeparator)
                    .append("and ")
                    .append(col.getColumnName())
                    .append(" = ")
                    .append(parameter(col.getColumnName()))
                    .append(System.lineSeparator());
        }

        return queryStr.toString();
    }
}
