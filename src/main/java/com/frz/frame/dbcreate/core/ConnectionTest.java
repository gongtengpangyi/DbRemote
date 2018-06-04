package com.frz.frame.dbcreate.core;

import com.frz.frame.dbcreate.DBCreator;
import com.frz.frame.dbcreate.TableCreator;
import com.frz.frame.dbcreate.jdbc.ConnectionManager;
import com.frz.frame.dbcreate.jdbc.DbAction;
import com.frz.frame.dbcreate.model.Field;
import com.frz.frame.dbcreate.model.Table;

import java.sql.Statement;

public class ConnectionTest {
    public static void main(String[] args) {
//        ConnectionManager connectionManager = new ConnectionManager("jdbc:mysql://localhost:3306/testDatabase", "root", "debug5a621");
//        connectionManager.connect();
//        Statement statement = connectionManager.getStatement();
//        createTest(statement);

//        updateTest(statement);
//        destoryTest(statement);

//        connectionManager.close();
        System.out.println("xxxxxxxxxxx");
        DBCreator dbCreator = new StandardDBCreator("localhost", 3306, "root", "123456");
        System.out.println(dbCreator.deleteDatabase("test2"));
    }

    public static void createTest(Statement statement) {
//
//        DbAction.createDatabase(statement, "testDatabase");
//        DbAction.createUser(statement, "testUser3", "password");
//        DbAction.grantUser(statement, "testUser", "testDatabase");

        Table table = new Table();
        table.setTableName("fk1_test_table");

//        Field id = new Field();
//        id.setName("id");
//        id.setType("INT");
//        id.setPk("true");
//        id.setAuto("true");
//
//        Field name = new Field();
//        name.setName("name");
//        name.setType("varchar");
//        name.setLength("18");
//        name.setNotnull("true");
//
//        Field sex = new Field();
//        sex.setName("sex");
//        sex.setType("varchar");
//        sex.setLength("3");
//        sex.setDefaultData("man");

        Field fk_id = new Field();
        fk_id.setName("fk_id");
        fk_id.setType("INT");
        fk_id.setFk("test_table(id)");

        Field[] fields = new Field[1];
//        fields[0] = id;
//        fields[1] = name;
//        fields[2] = sex;
        fields[0] = fk_id;
        table.setFields(fields);

        DbAction.createTable(statement, table);

    }
    public static void updateTest(Statement statement) {
        Field address = new Field();
        address.setName("address");
        address.setType("varchar");
        address.setLength("100");

        DbAction.addField(statement, "test_table", address);

        DbAction.changeField(statement,"test_table", "address", "email", "varchar(30)");
    }
    public static void destoryTest(Statement statement) {

        DbAction.dropUser(statement, "testUser");
        DbAction.dropTable(statement, "test_table");
        DbAction.dropDatabase(statement, "testDatabase");

    }
}
