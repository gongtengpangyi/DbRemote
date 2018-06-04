package com.frz.frame.dbcreate.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private String url;
    private String account;
    private String password;
    private Connection connection;
    private Statement statement;
    private String driver = "com.mysql.jdbc.Driver";

    public static final Logger logger = LoggerFactory.getLogger(Connection.class);

    public ConnectionManager(String url, String account, String password) {
        this.url = url;
        this.account = account;
        this.password = password;
    }

    public void connect() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, account, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            logger.error("driver not found", e);
        } catch (SQLException e) {
            logger.error("statement error", e);
        }
    }

    public void close() {
        try {
            connection.close();
            statement.close();
            logger.info("database connection is closed");
        } catch (SQLException e) {
            logger.error("database connection close error", e);
        }
    }

    public Statement getStatement() {
        return statement;
    }
}
