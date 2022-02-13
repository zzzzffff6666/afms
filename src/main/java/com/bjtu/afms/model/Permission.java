package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class Permission implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer type;

    private Date addTime;

    private Integer addUser;

    private static final long serialVersionUID = 1L;

    public Permission(Integer id, Integer userId, Integer type, Date addTime, Integer addUser) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.addTime = addTime;
        this.addUser = addUser;
    }

    public Permission() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getAddUser() {
        return addUser;
    }

    public void setAddUser(Integer addUser) {
        this.addUser = addUser;
    }
}