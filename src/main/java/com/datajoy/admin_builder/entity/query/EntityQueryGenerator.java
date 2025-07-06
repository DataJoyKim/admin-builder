package com.datajoy.admin_builder.entity.query;

import com.datajoy.admin_builder.entity.EntityColumn;

import java.util.List;

public interface EntityQueryGenerator {

    String generate(String tableName, List<EntityColumn> entityColumns);
}
