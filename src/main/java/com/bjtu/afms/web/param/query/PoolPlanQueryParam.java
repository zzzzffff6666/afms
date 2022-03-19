package com.bjtu.afms.web.param.query;

import lombok.Data;

import java.util.Date;

@Data
public class PoolPlanQueryParam {
    private Integer planId;
    private Integer poolId;
    private Integer cycle;
    private Integer finish;
    private Date startActBegin;
    private Date startActLast;
    private Date endActBegin;
    private Date endActLast;
    private String orderBy;
}
