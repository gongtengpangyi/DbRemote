package com.frz.frame.dbremote;

import org.mybatis.spring.SqlSessionFactoryBean;

import javax.sql.DataSource;

public abstract class DataSourceChanger<T, E extends DataSource> {

    private SqlSessionFactoryBean sqlSessionFactory;

    private DataSourceCache<T, E> dataSourceCache;

    public DataSourceChanger() {
        this.sqlSessionFactory = getSqlSessionFactory();
        this.dataSourceCache = getDataSourceCache();
    }

    protected abstract SqlSessionFactoryBean getSqlSessionFactory();

    protected abstract DataSourceCache<T, E> getDataSourceCache();

    public boolean addDataSourceToCache(T key, E dataSource) {
        if (dataSource != null && dataSourceCache != null) {
            dataSourceCache.addDataSource(key, dataSource);
            return true;
        }
        return false;
    }


    public boolean setDataSource(E dataSource) {
        if (dataSource != null) {
            sqlSessionFactory.setDataSource(dataSource);
            return true;
        }
        return false;
    }

    public boolean setDataSource(DataSourceBean<E> dataSourceBean) {
        return setDataSource(dataSourceBean.getDataSource());
    }

    public boolean setDataSourceFromCache(T key) {
        if (dataSourceCache != null) {
            return this.setDataSource(dataSourceCache.getDataSourceBean(key));
        }
        return false;
    }

}
