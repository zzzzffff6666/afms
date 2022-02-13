package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer id;

    private String phone;

    private String password;

    private String salt;

    private String name;

    private String cardId;

    private Integer status;

    private Date addTime;

    private Integer addUser;

    private Date modTime;

    private Integer modUser;

    private static final long serialVersionUID = 1L;

    public User(Integer id, String phone, String password, String salt, String name, String cardId, Integer status, Date addTime, Integer addUser, Date modTime, Integer modUser) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.salt = salt;
        this.name = name;
        this.cardId = cardId;
        this.status = status;
        this.addTime = addTime;
        this.addUser = addUser;
        this.modTime = modTime;
        this.modUser = modUser;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
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