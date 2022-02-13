package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
    private Integer id;

    private Integer type;

    private Integer storeId;

    private String name;

    private Integer amount;

    private Date expireTime;

    private Date maintainTime;

    private Integer maintainInterval;

    private Integer status;

    private String url;

    private Date addTime;

    private Integer addUser;

    private Date modTime;

    private Integer modUser;

    private static final long serialVersionUID = 1L;

    public Item(Integer id, Integer type, Integer storeId, String name, Integer amount, Date expireTime, Date maintainTime, Integer maintainInterval, Integer status, String url, Date addTime, Integer addUser, Date modTime, Integer modUser) {
        this.id = id;
        this.type = type;
        this.storeId = storeId;
        this.name = name;
        this.amount = amount;
        this.expireTime = expireTime;
        this.maintainTime = maintainTime;
        this.maintainInterval = maintainInterval;
        this.status = status;
        this.url = url;
        this.addTime = addTime;
        this.addUser = addUser;
        this.modTime = modTime;
        this.modUser = modUser;
    }

    public Item() {
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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getMaintainTime() {
        return maintainTime;
    }

    public void setMaintainTime(Date maintainTime) {
        this.maintainTime = maintainTime;
    }

    public Integer getMaintainInterval() {
        return maintainInterval;
    }

    public void setMaintainInterval(Integer maintainInterval) {
        this.maintainInterval = maintainInterval;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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