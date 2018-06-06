package com.frz.frame.dbremote;

import com.frz.frame.ssm.ToolSpring;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.sql.DataSource;
import java.util.Map;

public abstract class DataSourceChanger<T, E extends DataSource> {

    private MultipleDataSource multipleDataSource;

    private DataSourceCache<T, E> dataSourceCache;

    public DataSourceChanger() {
        this.dataSourceCache = getDataSourceCache();
        this.multipleDataSource = getMultipleDataSource();
    }

    protected abstract DataSourceCache<T, E> getDataSourceCache();

    protected abstract MultipleDataSource getMultipleDataSource();

    public boolean addDataSourceToCache(T key, E dataSource) {
        if (dataSource != null && dataSourceCache != null) {
            dataSourceCache.addDataSource(key, dataSource);
            multipleDataSource.setTargetDataSources((Map<Object, Object>) dataSourceCache.getDataSourceMap());
            multipleDataSource.afterPropertiesSet();
            return true;
        }
        return false;
    }

    public boolean setDataSourceFromCache(T key) {
        MultipleDataSource.setDataSourceKey(key);
        return true;
    }

}
