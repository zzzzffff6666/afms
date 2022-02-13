package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class Alert implements Serializable {
    private Integer id;

    private String name;

    private Integer type;

    private Integer relateId;

    private Integer userId;

    private String content;

    private Integer level;

    private Date startTime;

    private Date handleTime;

    private Date endTime;

    private Integer status;

    private Date addTime;

    private Integer addUser;

    private Date modTime;

    private Integer modUser;

    private static final long serialVersionUID = 1L;

    public Alert(Integer id, String name, Integer type, Integer relateId, Integer userId, String content, Integer level, Date startTime, Date handleTime, Date endTime, Integer status, Date addTime, Integer addUser, Date modTime, Integer modUser) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.relateId = relateId;
        this.userId = userId;
        this.content = content;
        this.level = level;
        this.startTime = startTime;
        this.handleTime = handleTime;
        this.endTime = endTime;
        this.status = status;
        this.addTime = addTime;
        this.addUser = addUser;
        this.modTime = modTime;
        this.modUser = modUser;
    }

    public Alert() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    public Integer getModUser() {
        return modUser;
    }

    public void setModUser(Integer modUser) {
        this.modUser = modUser;
    }
}