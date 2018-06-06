package com.example.madb.console.remote;

import com.alibaba.druid.pool.DruidDataSource;
import com.frz.frame.dbremote.DataSourceCache;
import com.frz.frame.dbremote.DataSourceChanger;
import com.frz.frame.dbremote.MultipleDataSource;
import com.frz.frame.ssm.ToolSpring;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 远端控制器
 */
@Service("DBRemote")
public class DBRemote extends DataSourceChanger<String, DruidDataSource> {

    private MultipleDataSource multipleDataSource;


    @Override
    protected DataSourceCache<String, DruidDataSource> getDataSourceCache() {
        return new DataSourceCache<String, DruidDataSource>();
    }

    @Override
    protected MultipleDataSource getMultipleDataSource() {
        multipleDataSource = (MultipleDataSource) ToolSpring.getBean("multipleDataSource");
        return multipleDataSource;
    }

    /**
     * 添加一个水厂
     * @param waterObject
     * @return
     */
    public boolean addWaterObject(WaterObject waterObject) {
        if (waterObject != null) {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setInitialSize(5);
            dataSource.setMaxActive(10);
            dataSource.setMinIdle(1);
            dataSource.setMaxWait(60000);
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://" + waterObject.getIp() +
                    ":" + waterObject.getPort() + "/" + waterObject.getDbName() + "?characterEncoding=UTF-8");
            dataSource.setUsername(waterObject.getUser());
            dataSource.setPassword(waterObject.getPassword());
            return this.addDataSourceToCache(String.valueOf(waterObject.getObjectId()), dataSource);
        }
        return false;
    }

    public boolean changeWaterObject(Integer objectId) {
        return setDataSourceFromCache(String.valueOf(objectId));
    }
}
