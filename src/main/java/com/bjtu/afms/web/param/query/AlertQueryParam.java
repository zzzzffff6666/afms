package com.bjtu.afms.web.param.query;

import lombok.Data;

import java.util.Date;

@Data
public class AlertQueryParam {
    private String name;
    private Integer type;
    private Integer relateId;
    private Integer userId;
    private Integer level;
    private Integer status;
    private Date startBegin;
    private Date startLast;
    private Date handleBegin;
    private Date handleLast;
    private Date endBegin;
    private Date endLast;
    private String orderBy;
}
