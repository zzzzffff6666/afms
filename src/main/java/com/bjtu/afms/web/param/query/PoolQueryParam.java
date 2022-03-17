package com.bjtu.afms.web.param.query;

import lombok.Data;

@Data
public class PoolQueryParam {
    private Integer place;
    private Integer type;
    private Integer status;
    private String orderBy;
}
