package com.frz.frame.dbremote;

import javax.sql.DataSource;

/**
 *
 * @param <T> 数据源的类型
 */
public class DataSourceBean<T extends DataSource> {

    private T dataSource;

    public DataSourceBean(T dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(T dataSource) {
        this.dataSource = dataSource;
    }

    public T getDataSource() {
        return dataSource;
    }
}
