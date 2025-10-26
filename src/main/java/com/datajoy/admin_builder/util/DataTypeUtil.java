package com.datajoy.admin_builder.util;

public class DataTypeUtil {
    public static Long valueLongOf(Object value) {
        Long id = null;
        if(value instanceof Long) {
            id = (Long) value;
        }
        else if(value instanceof Integer) {
            id = Long.valueOf((Integer) value);
        }

        return id;
    }
}
