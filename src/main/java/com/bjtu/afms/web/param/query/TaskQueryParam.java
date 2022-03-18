package com.bjtu.afms.web.param.query;

import lombok.Data;

@Data
public class TaskQueryParam {
    private Integer type;
    private String name;
    private Integer level;
    private String orderBy;
}
