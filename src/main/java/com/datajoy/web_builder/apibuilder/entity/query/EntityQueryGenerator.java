package com.datajoy.web_builder.apibuilder.entity.query;

import com.datajoy.web_builder.apibuilder.entity.EntityColumn;

import java.util.List;

public interface EntityQueryGenerator {

    String generate(String tableName, List<EntityColumn> entityColumns);
}
