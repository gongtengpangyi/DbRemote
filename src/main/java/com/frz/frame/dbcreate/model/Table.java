package com.frz.frame.dbcreate.model;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Table {
    private String tableName;
    private Field[] fields;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public String getTableName() {
        return tableName;
    }

    public Field[] getFields() {
        return fields;
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject.put("tablename", getTableName());

        for (Field field:
             fields) {
            jsonArray.add(field.toJson());
        }

        jsonObject.put("fields", jsonArray);
        return jsonObject;
    }
}
