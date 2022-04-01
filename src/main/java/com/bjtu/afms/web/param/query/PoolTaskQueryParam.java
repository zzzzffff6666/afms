package com.bjtu.afms.web.param.query;

import lombok.Data;

import java.util.Date;

@Data
public class PoolTaskQueryParam {
    private Integer poolId;
    private Integer cycle;
    private Integer planId;
    private Integer userId;
    private Integer taskId;
    private Integer status;
    private Date startActBegin;
    private Date startActLast;
    private Date endActBegin;
    private Date endActLast;
    private String orderBy;
}
