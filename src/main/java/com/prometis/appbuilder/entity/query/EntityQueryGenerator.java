package com.prometis.appbuilder.entity.query;

import com.prometis.appbuilder.entity.EntityColumn;

import java.util.List;

public interface EntityQueryGenerator {

    String generate(String tableName, List<EntityColumn> entityColumns);
}
