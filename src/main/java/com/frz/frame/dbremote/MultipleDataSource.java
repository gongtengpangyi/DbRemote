package com.frz.frame.dbremote;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultipleDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<Object> dataSourceKey = new InheritableThreadLocal<Object>();

    public static void setDataSourceKey(Object dataSource) {
        dataSourceKey.set(dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceKey.get();
    }

    public static void removeDataSourceKey() {
        dataSourceKey.remove();
    }

}
