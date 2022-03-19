package com.bjtu.afms.web.param.query;

import lombok.Data;

@Data
public class CommentQueryParam {
    private Integer userId;
    private Integer type;
    private Integer relateId;
    private String orderBy;
}
