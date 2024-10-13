package com.datajoy.web_builder.apibuilder.datasource;

import lombok.Getter;

import java.util.Objects;

@Getter
public class LookupKey {
    private String dataSourceName;

    public static LookupKey generateKey(DataSourceMeta meta) {
        LookupKey lookupKey = new LookupKey();
        lookupKey.dataSourceName = meta.getDataSourceName();

        return lookupKey;
    }

    public static LookupKey generateKey(String dataSourceName) {
        LookupKey lookupKey = new LookupKey();
        lookupKey.dataSourceName = dataSourceName;

        return lookupKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        LookupKey lookupKey = (LookupKey) obj;

        return Objects.equals(dataSourceName, lookupKey.dataSourceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSourceName);
    }
}
