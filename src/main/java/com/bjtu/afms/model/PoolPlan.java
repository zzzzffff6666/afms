package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class PoolPlan implements Serializable {
    private Integer id;

    private Integer planId;

    private Integer poolId;

    private Integer cycle;

    private Date startPre;

    private Date endPre;

    private Date startAct;

    private Date endAct;

    private Integer finish;

    private Date addTime;

    private Integer addUser;

    private Date modTime;

    private Integer modUser;

    private static final long serialVersionUID = 1L;

    public PoolPlan(Integer id, Integer planId, Integer poolId, Integer cycle, Date startPre, Date endPre, Date startAct, Date endAct, Integer finish, Date addTime, Integer addUser, Date modTime, Integer modUser) {
        this.id = id;
        this.planId = planId;
        this.poolId = poolId;
        this.cycle = cycle;
        this.startPre = startPre;
        this.endPre = endPre;
        this.startAct = startAct;
        this.endAct = endAct;
        this.finish = finish;
        this.addTime = addTime;
        this.addUser = addUser;
        this.modTime = modTime;
        this.modUser = modUser;
    }

    public PoolPlan() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
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

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
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