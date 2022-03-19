package com.bjtu.afms.web.param.query;

import lombok.Data;

import java.util.Date;

@Data
public class FundQueryParam {
    private String name;
    private Integer type;
    private Date addBegin;
    private Date addLast;
    private String orderBy;
}
