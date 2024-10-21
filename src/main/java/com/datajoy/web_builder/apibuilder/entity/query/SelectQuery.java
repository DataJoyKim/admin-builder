package com.datajoy.web_builder.apibuilder.entity.query;

import com.datajoy.web_builder.apibuilder.entity.EntityColumn;
import com.datajoy.web_builder.apibuilder.entity.code.SortOrder;

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

        String sql = "";
        sql += "select " + System.lineSeparator();
        sql += "from " + tableName + System.lineSeparator();
        sql += "where 1=1 " + System.lineSeparator();

        if(!orderColumns.isEmpty()) {
            sql += "order by " + orderBy(orderColumns) + System.lineSeparator();
        }

        sql += ";";

        return sql;
    }

    private String orderBy(List<EntityColumn> orderColumns) {
        StringBuilder orderBy = new StringBuilder();

        int i=0;
        for(EntityColumn col : orderColumns) {
            if(SortOrder.ASC.equals(col.getSelectOrderBySortOrder())) {
                orderBy.append(separator(i))
                        .append(col.getColumnName())
                        .append(" asc");
            }
            else if(SortOrder.DESC.equals(col.getSelectOrderBySortOrder())) {
                orderBy.append(separator(i))
                        .append(col.getColumnName())
                        .append(" desc");
            }
            else {
                orderBy.append(separator(i))
                        .append(col.getColumnName());
            }

            i++;
        }

        return orderBy.toString();
    }
}
