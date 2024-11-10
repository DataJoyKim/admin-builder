package com.datajoy.admin_builder.apibuilder.entity.query;

import com.datajoy.admin_builder.apibuilder.entity.code.EntityStatus;

public class EntityQueryGeneratorFactory {

    public static EntityQueryGenerator instance(EntityStatus status) {
        return switch (status) {
            case C -> new InsertQuery();
            case R -> new SelectQuery();
            case U -> new UpdateQuery();
            case D -> new DeleteQuery();
        };
    }
}
