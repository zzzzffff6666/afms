package com.bjtu.afms.web.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PlanTask {
    private Integer taskId;
    private Integer userId;
    private Date startTime;
    private Integer taskDuration;
    private Integer applyInterval;
    private List<Integer> poolIdList;
}
