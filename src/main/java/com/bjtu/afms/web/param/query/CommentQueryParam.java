package com.bjtu.afms.web.param.query;

import lombok.Data;

import java.util.Date;

@Data
public class CommentQueryParam {
    private Integer userId;
    private Integer type;
    private Integer relateId;
    private Date addBegin;
    private Date addLast;
    private String orderBy;
}
