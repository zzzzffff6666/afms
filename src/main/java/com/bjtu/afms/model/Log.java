package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
    private Integer id;

    private Integer type;

    private Integer relateId;

    private Integer userId;

    private String operation;

    private String oldValue;

    private String newValue;

    private Date addTime;

    private static final long serialVersionUID = 1L;

    public Log(Integer id, Integer type, Integer relateId, Integer userId, String operation, String oldValue, String newValue, Date addTime) {
        this.id = id;
        this.type = type;
        this.relateId = relateId;
        this.userId = userId;
        this.operation = operation;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.addTime = addTime;
    }

    public Log() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRelateId() {
        return relateId;
    }

    public void setRelateId(Integer relateId) {
        this.relateId = relateId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue == null ? null : oldValue.trim();
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue == null ? null : newValue.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}