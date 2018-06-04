package com.frz.frame.dbcreate;

import com.frz.frame.dbcreate.model.Field;

import java.util.List;

/**
 * 数据表创建器
 * @author GongTengPangYi
 */
public abstract class TableCreator {

    protected String ip;
    protected int port;
    protected String account;
    protected String password;
    protected String databaseName;

    public TableCreator(String ip, int port, String account, String password, String databaseName) {
        this.ip = ip;
        this.port = port;
        this.account = account;
        this.password = password;
        this.databaseName = databaseName;
    }

    /**
     * 创建数据表
     * @param tableName 表名
     * @param fields 字段列表
     * @return
     */
    public abstract boolean createTable(String tableName, Field[] fields);

    /**
     * 删除数据表
     * @param tableName 表名
     * @return
     */
    public abstract boolean deleteTable(String tableName);

    /**
     * 添加表字段
     * @param tableName 表名
     * @param fields 字段列表
     * @return
     */
    public abstract boolean addTableFields(String tableName, Field[] fields);

    /**
     * 删除表字段
     * @param tableName 表名
     * @param fieldNames 字段列表
     * @return
     */
    public abstract boolean deleteTableFields(String tableName, String[] fieldNames);


    public abstract boolean executeSql(String sql);
}
