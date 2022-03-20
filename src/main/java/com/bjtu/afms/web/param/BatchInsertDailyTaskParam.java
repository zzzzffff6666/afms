package com.bjtu.afms.web.param;

import lombok.Data;

import java.util.List;

@Data
public class BatchInsertDailyTaskParam {
    private Integer userId;
    private List<SetTaskParam> taskList;
}
