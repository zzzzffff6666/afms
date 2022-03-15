package com.bjtu.afms.web.param.query;

import lombok.Data;

@Data
public class StoreQueryParam {
    private String name;
    private Integer manager;
    private String orderBy;
}
