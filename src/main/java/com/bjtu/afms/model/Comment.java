package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer type;

    private Integer relateId;

    private Integer score;

    private String content;

    private Date addTime;

    private Integer addUser;

    private Date modTime;

    private Integer modUser;

    private static final long serialVersionUID = 1L;

    public Comment(Integer id, Integer userId, Integer type, Integer relateId, Integer score, String content, Date addTime, Integer addUser, Date modTime, Integer modUser) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.relateId = relateId;
        this.score = score;
        this.content = content;
        this.addTime = addTime;
        this.addUser = addUser;
        this.modTime = modTime;
        this.modUser = modUser;
    }

    public Comment() {
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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