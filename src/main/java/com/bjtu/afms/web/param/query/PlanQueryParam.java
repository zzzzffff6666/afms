package com.bjtu.afms.web.param.query;

import lombok.Data;

import java.util.Date;

@Data
public class PlanQueryParam {
    private String name;
    private Integer finish;
    private Date applyBegin;
    private Date applyLast;
    private Date finishBegin;
    private Date finishLast;
    private String orderBy;
}
