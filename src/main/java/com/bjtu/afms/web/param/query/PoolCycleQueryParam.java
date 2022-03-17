package com.bjtu.afms.web.param.query;

import lombok.Data;

import java.util.Date;

@Data
public class PoolCycleQueryParam {
    private Integer poolId;
    private Integer cycle;
    private Integer userId;
    private Integer status;
    private Date startBegin;
    private Date startLast;
    private Date endBegin;
    private Date endLast;
    private String orderBy;
}
