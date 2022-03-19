package com.bjtu.afms.web.param.query;

import lombok.Data;

import java.util.Date;

@Data
public class JobQueryParam {
    private Integer userId;
    private Integer type;
    private Integer relateId;
    private Integer status;
    private Date deadlineBegin;
    private Date deadlineLast;
    private String orderBy;
}
