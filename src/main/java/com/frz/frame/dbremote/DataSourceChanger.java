package com.frz.frame.dbremote;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class DataSourceChanger<T, E extends DataSource> {

    @Autowired
    SqlSessionFactoryBean sqlSessionFactory;

    private DataSourceCache<T, E> dataSourceCache;

    public DataSourceChanger() {
    }

    public DataSourceChanger(DataSourceCache<T, E> dataSourceCache) {
        this.dataSourceCache = dataSourceCache;
    }

    public boolean setDataSource(DataSource dataSource) {
        if (dataSource != null) {
            sqlSessionFactory.setDataSource(dataSource);
            return true;
        }
        return false;
    }

    public boolean setDataSource(DataSourceBean dataSourceBean) {
        return setDataSource(dataSourceBean.getDataSource());
    }

    public boolean setDataSourceFromCache(T key) {
        if (dataSourceCache != null) {
            return this.setDataSource(dataSourceCache.getDataSourceBean(key));
        }
        return false;
    }

}
