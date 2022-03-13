package com.bjtu.afms.web.param.query;

import lombok.Data;

@Data
public class PermissionQueryParam {
    private Integer userId;
    private Integer auth;
    private Integer type;
    private Integer relateId;
    private String orderBy;
}
