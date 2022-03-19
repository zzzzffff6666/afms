package com.bjtu.afms.web.param.query;

import lombok.Data;

import java.util.Date;

@Data
public class LogQueryParam {
    private Integer type;
    private Integer relateId;
    private Integer userId;
    private Integer operationId;
    private Date addBegin;
    private Date addLast;
    private String orderBy;
}
