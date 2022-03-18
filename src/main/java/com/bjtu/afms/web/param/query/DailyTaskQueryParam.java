package com.bjtu.afms.web.param.query;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class DailyTaskQueryParam {
    private Integer userId;
    private Integer taskId;
    private Integer status;
    private Date startActBegin;
    private Date startActLast;
    private Date endActBegin;
    private Date endActLast;
    private String orderBy;

    public DailyTaskQueryParam(PoolTaskQueryParam param) {
        this.userId = param.getUserId();
        this.taskId = param.getTaskId();
        this.status = param.getStatus();
        this.startActBegin = param.getStartActBegin();
        this.startActLast = param.getStartActLast();
        this.endActBegin = param.getEndActBegin();
        this.endActLast = param.getEndActLast();
        this.orderBy = param.getOrderBy();
    }
}
