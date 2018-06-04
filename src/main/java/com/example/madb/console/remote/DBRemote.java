package com.example.madb.console.remote;

import com.alibaba.druid.pool.DruidDataSource;
import com.frz.frame.dbremote.DataSourceCache;
import com.frz.frame.dbremote.DataSourceChanger;
import org.mybatis.spring.SqlSessionFactoryBean; 
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 远端控制器
 */
public class DBRemote extends DataSourceChanger<Integer, DruidDataSource> {

    @Autowired
    SqlSessionFactoryBean sqlSessionFactory;

    @Override
    protected SqlSessionFactoryBean getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    @Override
    protected DataSourceCache<Integer, DruidDataSource> getDataSourceCache() {
        return new DataSourceCache<Integer, DruidDataSource>();
    }

    /**
     * 添加一个水厂
     * @param waterObject
     * @return
     */
    public boolean addWaterObject(WaterObject waterObject) {
        if (waterObject != null) {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.addConnectionProperty("initialSize", "5");
            dataSource.addConnectionProperty("maxActive", "10");
            dataSource.addConnectionProperty("minIdle", "1");
            dataSource.addConnectionProperty("maxWait", "60000");
            dataSource.addConnectionProperty("driverClassName", "com.mysql.jdbc.Driver");
            dataSource.addConnectionProperty("url", "jdbc:mysql://" + waterObject.getIp() +
                    ":" + waterObject.getPort() + "/" + waterObject.getDbName() + "?characterEncoding=UTF-8");
            dataSource.addConnectionProperty("username", waterObject.getUser());
            dataSource.addConnectionProperty("password", waterObject.getPassword());
            return this.addDataSourceToCache(waterObject.getObjectId(), dataSource);
        }
        return false;
    }

    public boolean changeWaterObject(Integer objectId) {
        return setDataSourceFromCache(objectId);
    }
}
