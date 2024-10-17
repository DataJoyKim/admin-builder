package com.datajoy.web_builder.apibuilder.entity.query;

import com.datajoy.web_builder.apibuilder.entity.EntityColumn;
import com.datajoy.web_builder.apibuilder.entity.code.NullResolveType;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntityQuery implements EntityQueryGenerator {

    protected String tabSeparator = "\t";

    @Override
    public String generate(String tableName, List<EntityColumn> entityColumns) {
        return generateQuery(tableName, entityColumns);
    }

    protected abstract String generateQuery(String tableName, List<EntityColumn> entityColumns) ;

    protected static String separator(int i) {
        return (i > 0) ? "," : "";
    }

    protected String generateParams(String columnName) {
        return "#{" + columnName + "}";
    }

    protected List<EntityColumn> resolveNull(List<EntityColumn> entityColumns) {
        List<EntityColumn> resolvedEntityColumns = new ArrayList<>();

        for(EntityColumn col : entityColumns) {
            if(col.getValue() == null) {
                if(NullResolveType.SET_NULL.equals(col.getInsertNullResolveType())) {
                    resolvedEntityColumns.add(col);
                }
                else if(NullResolveType.EXCEPTION.equals(col.getInsertNullResolveType())) {
                    throw new EntityParamNullException();
                }
                else if(NullResolveType.EXCLUDE_QUERY.equals(col.getInsertNullResolveType())) {
                    // exclude
                }
                else {
                    resolvedEntityColumns.add(col);
                }
            }
            else {
                resolvedEntityColumns.add(col);
            }
        }
        return resolvedEntityColumns;
    }
}
