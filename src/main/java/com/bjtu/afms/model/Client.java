package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class Client implements Serializable {
    private Integer id;

    private String name;

    private String phone;

    private String wechat;

    private String addr;

    private String nameEp;

    private String addrEp;

    private Date addTime;

    private Integer addUser;

    private Date modTime;

    private Integer modUser;

    private static final long serialVersionUID = 1L;

    public Client(Integer id, String name, String phone, String wechat, String addr, String nameEp, String addrEp, Date addTime, Integer addUser, Date modTime, Integer modUser) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.wechat = wechat;
        this.addr = addr;
        this.nameEp = nameEp;
        this.addrEp = addrEp;
        this.addTime = addTime;
        this.addUser = addUser;
        this.modTime = modTime;
        this.modUser = modUser;
    }

    public Client() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getNameEp() {
        return nameEp;
    }

    public void setNameEp(String nameEp) {
        this.nameEp = nameEp == null ? null : nameEp.trim();
    }

    public String getAddrEp() {
        return addrEp;
    }

    public void setAddrEp(String addrEp) {
        this.addrEp = addrEp == null ? null : addrEp.trim();
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