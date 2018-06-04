package com.frz.frame.dbcreate.model;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Field {
    /* 字段名 */
    private String name;
    /* 类型 */
    private String type;
    /* 长度 */
    private String length;
    /* 主键 */
    private String pk;
    /* 非空 */
    private String notnull;
    /* 自增 */
    private String auto;
    /* 默认值 */
    private String defaultData;
    /* 外键 */
    private String fk;

    public void setDefaultData(String defaultData) {
        this.defaultData = defaultData;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public void setNotnull(String notnull) {
        this.notnull = notnull;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public void setFk(String fk) {
        this.fk = fk;
    }

    public String getDefaultData() {
        return defaultData;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLength() {
        return length;
    }

    public String getPk() {
        return pk;
    }

    public String getNotnull() {
        return notnull;
    }

    public String getAuto() {
        return auto;
    }

    public String getFk() {
        return fk;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", getName());
        jsonObject.put("type", getType());
        jsonObject.put("length", getLength());
        jsonObject.put("pk", getPk());
        jsonObject.put("notnull", getNotnull());
        jsonObject.put("auto", getAuto());
        jsonObject.put("default", getDefaultData());
        jsonObject.put("fk", getFk());

        return jsonObject;
    }

}
