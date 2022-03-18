package com.bjtu.afms.web.pojo;

import lombok.Data;

@Data
public class PlanTask {
    private Integer taskId;
    private Long startOffset;
    private Long endOffset;
}
