package com.prometis.appbuilder.entity.query;

import com.prometis.appbuilder.entity.code.EntityStatus;

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
