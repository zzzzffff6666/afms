package com.bjtu.afms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PoolCycle implements Serializable {
    private Integer id;

    private Integer poolId;

    private Integer cycle;

    private Integer userId;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private BigDecimal cost;

    private BigDecimal income;

    private Date addTime;

    private Integer addUser;

    private Date modTime;

    private Integer modUser;

    private static final long serialVersionUID = 1L;

    public PoolCycle(Integer id, Integer poolId, Integer cycle, Integer userId, Date startTime, Date endTime, Integer status, BigDecimal cost, BigDecimal income, Date addTime, Integer addUser, Date modTime, Integer modUser) {
        this.id = id;
        this.poolId = poolId;
        this.cycle = cycle;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.cost = cost;
        this.income = income;
        this.addTime = addTime;
        this.addUser = addUser;
        this.modTime = modTime;
        this.modUser = modUser;
    }

    public PoolCycle() {
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
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