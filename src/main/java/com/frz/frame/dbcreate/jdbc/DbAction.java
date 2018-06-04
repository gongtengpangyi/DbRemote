package com.frz.frame.dbcreate.jdbc;

import com.frz.frame.dbcreate.model.Field;
import com.frz.frame.dbcreate.model.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Statement;

public class DbAction {
    public static Logger logger = LoggerFactory.getLogger(DbAction.class);

    /**
     * 创建数据库
     *
     * @param statement
     * @param databaseName
     * @return
     */
    public static boolean createDatabase(Statement statement, String databaseName) {
        try {
            statement.executeUpdate("CREATE DATABASE " + databaseName);
            logger.info("create database " + databaseName + "success");
            return true;
        } catch (SQLException e) {
            logger.info("create database fail", e);
            return false;

        }
    }

    /**
     * 删除数据库
     *
     * @param statement
     * @param databaseName
     * @return
     */
    public static boolean dropDatabase(Statement statement, String databaseName) {
        try {
            statement.executeUpdate("DROP DATABASE " + databaseName);
            logger.info("drop database " + databaseName + "success");
            return true;
        } catch (SQLException e) {
            logger.error("drop database " + databaseName + "fail", e);
            return false;
        }
    }

    /**
     * 建表语句
     *
     * @param statement
     * @param table
     * @return
     */
    public static boolean createTable(Statement statement, Table table) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(table.getTableName()).append("(");

        for (Field field :
                table.getFields()) {
            sql.append(FieldAnalyze.getFieldAttribute(field));
        }

        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        logger.info("create table sql:" + sql.toString());

        try {
            statement.executeUpdate(sql.toString());
            logger.info("create table " + table.getTableName() + "success");
            return true;
        } catch (SQLException e) {
            logger.error("create table " + table.getTableName() + "fail", e);
            return false;
        }
    }

    /**
     * 删除表
     *
     * @param statement
     * @param tableName
     * @return
     */
    public static boolean dropTable(Statement statement, String tableName) {
        try {
            statement.executeUpdate("DROP TABLE " + tableName);
            logger.info("drop table " + tableName + "success");
            return true;
        } catch (SQLException e) {
            logger.error("drop table " + tableName + "fail", e);
            return false;
        }
    }

    /**
     * 更改字段
     *
     * @param statement
     * @param tableName
     * @param oldField
     * @param newField
     * @param type
     * @return
     */
    public static boolean changeField(Statement statement, String tableName, String oldField, String newField, String type) {
        try {
            statement.executeUpdate(" ALTER TABLE " + tableName + " CHANGE " + oldField + " " + newField + " " + type);
            logger.info("change field" + oldField + "as" + newField);
            return true;
        } catch (SQLException e) {
            logger.error("change field" + oldField + "fail", e);
            return false;
        }
    }

    /**
     * 加入字段
     *
     * @param statement
     * @param tableName
     * @param field
     * @return
     */
    public static boolean addField(Statement statement, String tableName, Field field) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("ALTER TABLE ").append(tableName).append(" ADD ").append(FieldAnalyze.getFieldAttribute(field));
            sql.deleteCharAt(sql.length() - 1);
            System.out.println(sql);
            statement.executeUpdate(sql.toString());
            logger.info("change field");
            return true;
        } catch (SQLException e) {
            logger.error("change field" + "fail", e);
            return false;
        }
    }

    /**
     * 删除字段
     *
     * @param statement
     * @param tableName
     * @param fieldName
     * @return
     */
    public static boolean dropField(Statement statement, String tableName, String fieldName) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("ALTER TABLE ").append(tableName).append(" DROP COLUMN ").append(fieldName);
            System.out.println(sql);
            statement.executeUpdate(sql.toString());
            logger.info("change field");
            return true;
        } catch (SQLException e) {
            logger.error("change field" + "fail", e);
            return false;
        }
    }

    /**
     * 创建用户
     *
     * @param statement
     * @param username
     * @param password
     * @return
     */
    public static boolean createUser(Statement statement, String username, String password) {
        try {
            statement.executeUpdate("CREATE USER '" + username + "'@'%' IDENTIFIED BY '" + password + "'");
            logger.info("create user " + username + "success");
            return true;
        } catch (SQLException e) {
            logger.error("create user " + username + "fail", e);
            return false;
        }
    }

    /**
     * 删除用户
     *
     * @param statement
     * @param username
     * @return
     */
    public static boolean dropUser(Statement statement, String username) {
        try {
            statement.executeUpdate("DROP USER '" + username + "'@'%'");
            logger.info("drop user " + username + "success");
            return true;
        } catch (SQLException e) {
            logger.error("drop user " + username + "fail", e);
            return false;
        }
    }

    /**
     * 给用户授权
     *
     * @param statement
     * @param username
     * @param databaseName
     * @return
     */
    public static boolean grantUser(Statement statement, String username, String databaseName) {
        try {
            statement.executeUpdate("GRANT all privileges ON " + databaseName + ".* TO '" + username + "'@'%' WITH GRANT OPTION");
            logger.info("grant user " + username + "success");
            return true;
        } catch (SQLException e) {
            logger.error("grant user " + username + "fail", e);
            return false;

        }
    }
}
