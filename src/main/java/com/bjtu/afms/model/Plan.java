package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class Plan implements Serializable {
    private Integer id;

    private String name;

    private Integer useNum;

    private String taskList;

    private Date addTime;

    private Integer addUser;

    private Date modTime;

    private Integer modUser;

    private static final long serialVersionUID = 1L;

    public Plan(Integer id, String name, Integer useNum, String taskList, Date addTime, Integer addUser, Date modTime, Integer modUser) {
        this.id = id;
        this.name = name;
        this.useNum = useNum;
        this.taskList = taskList;
        this.addTime = addTime;
        this.addUser = addUser;
        this.modTime = modTime;
        this.modUser = modUser;
    }

    public Plan() {
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

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    public String getTaskList() {
        return taskList;
    }

    public void setTaskList(String taskList) {
        this.taskList = taskList == null ? null : taskList.trim();
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