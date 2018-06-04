package com.frz.frame.dbcreate;

/**
 * 数据库创建器接口
 * @author GongTengPangYi
 */
public abstract class DBCreator {

    protected String ip;
    protected int port;
    protected String account;
    protected String password;

    public DBCreator(String ip, int port, String account, String password) {
        this.ip = ip;
        this.port = port;
        this.account = account;
        this.password = password;
    }

    /**
     * 创建数据库
     * @param databaseName 数据库名
     * @return 数据库创建器
     */
    public abstract TableCreator createDatabase(String databaseName);

    /**
     * 删除数据库
     * @param databaseName 数据库名
     * @return 成功与否
     */
    public abstract boolean deleteDatabase(String databaseName);

    /**
     * 选择数据库
     * @param databaseName 数据库名
     * @return 数据库创建器
     */
    public abstract TableCreator chooseDatabase(String databaseName);
}
