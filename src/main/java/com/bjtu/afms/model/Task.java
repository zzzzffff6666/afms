package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private Integer id;

    private Integer type;

    private String name;

    private Integer level;

    private String content;

    private Date addTime;

    private Integer addUser;

    private Date modTime;

    private Integer modUser;

    private static final long serialVersionUID = 1L;

    public Task(Integer id, Integer type, String name, Integer level, String content, Date addTime, Integer addUser, Date modTime, Integer modUser) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.level = level;
        this.content = content;
        this.addTime = addTime;
        this.addUser = addUser;
        this.modTime = modTime;
        this.modUser = modUser;
    }

    public Task() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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