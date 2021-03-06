package com.bjtu.afms.web.param;

import lombok.Data;

import java.util.List;

@Data
public class BatchInsertPoolTaskParam {
    private Integer poolCycleId;
    private Integer poolId;
    private Integer cycle;
    private Integer userId;
    private List<SetTaskParam> taskList;
}
