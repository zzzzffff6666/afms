package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class Job implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer type;

    private Integer relateId;

    private Date deadline;

    private Integer status;

    private Date addTime;

    private Integer addUser;

    private static final long serialVersionUID = 1L;

    public Job(Integer id, Integer userId, Integer type, Integer relateId, Date deadline, Integer status, Date addTime, Integer addUser) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.relateId = relateId;
        this.deadline = deadline;
        this.status = status;
        this.addTime = addTime;
        this.addUser = addUser;
    }

    public Job() {
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

    public Integer getRelateId() {
        return relateId;
    }

    public void setRelateId(Integer relateId) {
        this.relateId = relateId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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