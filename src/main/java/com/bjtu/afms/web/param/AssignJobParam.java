package com.bjtu.afms.web.param;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class AssignJobParam {
    private Integer type;
    private Integer relateId;
    private Integer taskId;
    private Date deadline;
    private Set<Integer> userSet;
}
