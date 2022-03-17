package com.bjtu.afms.web.param.query;

import lombok.Data;

import java.util.Date;

@Data
public class ItemQueryParam {
    private Integer type;
    private Integer storeId;
    private String name;
    private Integer status;
    private Date expireBegin;
    private Date expireLast;
    private Date maintainBegin;
    private Date maintainLast;
    private String orderBy;
}
