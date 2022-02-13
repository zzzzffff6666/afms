package com.bjtu.afms.model;

import java.io.Serializable;
import java.util.Date;

public class Verify implements Serializable {
    private String phone;

    private String code;

    private Date expireTime;

    private Date verifyTime;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public Verify(String phone, String code, Date expireTime, Date verifyTime, Integer status) {
        this.phone = phone;
        this.code = code;
        this.expireTime = expireTime;
        this.verifyTime = verifyTime;
        this.status = status;
    }

    public Verify() {
        super();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}