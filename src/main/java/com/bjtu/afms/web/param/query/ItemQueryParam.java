package com.bjtu.afms.web.param.query;

import lombok.Data;

import java.util.Date;

@Data
public class ItemQueryParam {
    private Integer type;
    private Integer storeId;
    private String name;
    private Integer status;
    private Date expireStart;
    private Date expireEnd;
    private Date maintainStart;
    private Date maintainEnd;
    private String orderBy;
}
