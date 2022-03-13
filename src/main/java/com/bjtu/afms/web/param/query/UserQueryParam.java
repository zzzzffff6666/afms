package com.bjtu.afms.web.param.query;

import lombok.Data;

@Data
public class UserQueryParam {
    private String name;
    private String phone;
    private Integer status;
    private String orderBy;
}
