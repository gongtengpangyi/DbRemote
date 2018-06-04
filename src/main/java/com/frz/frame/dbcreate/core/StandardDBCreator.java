package com.frz.frame.dbcreate.core;

import com.frz.frame.dbcreate.DBCreator;
import com.frz.frame.dbcreate.TableCreator;
import com.frz.frame.dbcreate.jdbc.ConnectionManager;
import com.frz.frame.dbcreate.jdbc.DbAction;

import java.sql.Statement;

/**
 * @author GongTengPangYi
 */
public class StandardDBCreator extends DBCreator{
    private Statement statement;

    public StandardDBCreator(String ip, int port, String account, String password) {
        super(ip, port, account, password);
        String url = "jdbc:mysql://" + ip + ":" + port;
        ConnectionManager connectionManager = new ConnectionManager(url, account, password);
        connectionManager.connect();
        statement = connectionManager.getStatement();
    }

    @Override
    public TableCreator createDatabase(String databaseName) {
        if (DbAction.createDatabase(statement, databaseName)) {
            return chooseDatabase(databaseName);
        }
        return null;
    }

    @Override
    public boolean deleteDatabase(String databaseName) {
        return DbAction.dropDatabase(statement, databaseName);
    }

    @Override
    public TableCreator chooseDatabase(String databaseName) {
        return new StandardTableCreator(ip, port, account, password, databaseName);
    }
}
