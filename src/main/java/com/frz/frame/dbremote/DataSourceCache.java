package com.frz.frame.dbremote;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * dataSource的存储
 * @param <T> map 的key类型
 * @param <E> 数据库dataSource的类型
 */
public class DataSourceCache<T, E extends DataSource> {

    private Map<T, DataSourceBean<E>> dataSourceBeanMap;

    public DataSourceCache() {
        this.dataSourceBeanMap = new HashMap<>();
    }

    public void addDataSourceBean(T key, DataSourceBean<E> dataSourceBean) {
        dataSourceBeanMap.put(key, dataSourceBean);
    }

    public DataSourceBean<E> getDataSourceBean(T key) {
        return dataSourceBeanMap.get(key);
    }

    public void addDataSource(T key, E dataSource) {
        addDataSourceBean(key, new DataSourceBean<E>(dataSource));
    }

    public E getDataSource(T key) {
        DataSourceBean<E> dataSourceBean = getDataSourceBean(key);
        if (dataSourceBean != null) {
            return dataSourceBean.getDataSource();
        }
        return null;
    }

    public Map<Object, Object> getDataSourceMap() {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        for (T key :  dataSourceBeanMap.keySet()) {
            dataSourceMap.put(key, dataSourceBeanMap.get(key).getDataSource());
        }
        return dataSourceMap;
    }
}
