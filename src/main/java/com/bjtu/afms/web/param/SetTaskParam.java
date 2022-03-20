package com.bjtu.afms.web.param;

import lombok.Data;

import java.util.Date;

@Data
public class SetTaskParam {
    private Integer taskId;
    private Integer userId;
    private Date startPre;
    private Date endPre;
}
