package com.datajoy.web_builder.apibuilder.entity.query;

import com.datajoy.web_builder.apibuilder.entity.EntityColumn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SelectQuery extends AbstractEntityQuery {
    @Override
    public String generateQuery(String tableName, List<EntityColumn> entityColumns) {
        List<EntityColumn> resolvedEntityColumns = resolveNull(entityColumns);

        List<EntityColumn> whereColumns = new ArrayList<>();
        List<EntityColumn> orderColumns = new ArrayList<>();

        for(EntityColumn col : resolvedEntityColumns) {
            if(col.getSelectWhereType() != null) {
                whereColumns.add(col);
            }

            if(col.getSelectOrderByNum() != null) {
                orderColumns.add(col);
            }
        }

        orderColumns = orderColumns.stream()
                .sorted(Comparator.comparing(EntityColumn::getSelectOrderByNum, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());

        return "select " + System.lineSeparator() +
                "from " + tableName + System.lineSeparator() +
                "where 1=1 " + System.lineSeparator() +
                ";";
    }
}
