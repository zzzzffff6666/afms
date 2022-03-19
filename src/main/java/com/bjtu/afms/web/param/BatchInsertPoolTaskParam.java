package com.bjtu.afms.web.param;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BatchInsertPoolTaskParam {
    private Integer poolCycleId;
    private Integer poolId;
    private Integer cycle;
    private Integer userId;
    private List<InnerTask> taskList;

    @Data
    public class InnerTask {
        private Integer taskId;
        private Integer userId;
        private Date startPre;
        private Date endPre;
    }
}
