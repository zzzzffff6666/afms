package com.bjtu.afms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Pool implements Serializable {
    private Integer id;

    private Integer place;

    private Integer ordinal;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    private Integer type;

    private String usage;

    private Integer status;

    private String detail;

    private String url;

    private Date addTime;

    private Integer addUser;

    private Date modTime;

    private Integer modUser;

    private static final long serialVersionUID = 1L;

    public Pool(Integer id, Integer place, Integer ordinal, BigDecimal length, BigDecimal width, BigDecimal height, Integer type, String usage, Integer status, String detail, String url, Date addTime, Integer addUser, Date modTime, Integer modUser) {
        this.id = id;
        this.place = place;
        this.ordinal = ordinal;
        this.length = length;
        this.width = width;
        this.height = height;
        this.type = type;
        this.usage = usage;
        this.status = status;
        this.detail = detail;
        this.url = url;
        this.addTime = addTime;
        this.addUser = addUser;
        this.modTime = modTime;
        this.modUser = modUser;
    }

    public Pool() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage == null ? null : usage.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
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