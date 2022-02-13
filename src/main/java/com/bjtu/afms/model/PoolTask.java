package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class PoolTask implements Serializable {
    private Integer id;

    private Integer poolId;

    private Integer cycle;

    private Integer userId;

    private Integer taskId;

    private Date startPre;

    private Date endPre;

    private Date startAct;

    private Date endAct;

    private Integer status;

    private Date addTime;

    private Integer addUser;

    private Date modTime;

    private Integer modUser;

    private static final long serialVersionUID = 1L;

    public PoolTask(Integer id, Integer poolId, Integer cycle, Integer userId, Integer taskId, Date startPre, Date endPre, Date startAct, Date endAct, Integer status, Date addTime, Integer addUser, Date modTime, Integer modUser) {
        this.id = id;
        this.poolId = poolId;
        this.cycle = cycle;
        this.userId = userId;
        this.taskId = taskId;
        this.startPre = startPre;
        this.endPre = endPre;
        this.startAct = startAct;
        this.endAct = endAct;
        this.status = status;
        this.addTime = addTime;
        this.addUser = addUser;
        this.modTime = modTime;
        this.modUser = modUser;
    }

    public PoolTask() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPoolId() {
        return poolId;
    }

    public void setPoolId(Integer poolId) {
        this.poolId = poolId;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Date getStartPre() {
        return startPre;
    }

    public void setStartPre(Date startPre) {
        this.startPre = startPre;
    }

    public Date getEndPre() {
        return endPre;
    }

    public void setEndPre(Date endPre) {
        this.endPre = endPre;
    }

    public Date getStartAct() {
        return startAct;
    }

    public void setStartAct(Date startAct) {
        this.startAct = startAct;
    }

    public Date getEndAct() {
        return endAct;
    }

    public void setEndAct(Date endAct) {
        this.endAct = endAct;
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