package com.frz.frame.dbcreate.jdbc;

import com.frz.frame.dbcreate.model.Field;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class FieldAnalyze {
    public static final Logger logger = LoggerFactory.getLogger(Connection.class);
    public static String getFieldAttribute(Field field) {
        StringBuilder sql = new StringBuilder();
        String fk;
        JSONObject fieldJson = field.toJson();

        sql.append(fieldJson.getString("name") + " ")
                .append(fieldJson.getString("type"));

        try {
            if (null != fieldJson.getString("length")) {
                sql.append("(" + fieldJson.getString("length") + ")");
            }
        } catch (JSONException e) {
            logger.info("length is null");
        }

        try {
            if (fieldJson.getString("pk") != null) {
                sql.append(" " + "PRIMARY KEY");
            }
        } catch (JSONException e) {
            logger.info("pk is null");
        }

        try {
            if (fieldJson.getString("auto") != null) {
                sql.append(" " + "AUTO_INCREMENT");
            }
        } catch (JSONException e) {
            logger.info("auto is null");
        }

        try {
            if (fieldJson.getString("notnull") != null) {
                sql.append(" " + "NOT NULL");
            }
        } catch (JSONException e) {
            logger.info("notnull is null");
        }

        try {
            if (fieldJson.getString("default") != null) {
                sql.append(" " + "DEFAULT'" + fieldJson.getString("default") + "'");
            }
        } catch (JSONException e) {
            logger.info("default is null");
        }

        sql.append(",");

        try {
            if (fieldJson.getString("fk") != null) {
                fk = "CONSTRAINT FOREIGN KEY (" + fieldJson.getString("name") + ") REFERENCES " + fieldJson.getString("fk") + ",";
                sql.append(fk);
            }
        } catch (JSONException e) {
            logger.info("fk is null");
        }

        return sql.toString();
    }

}
