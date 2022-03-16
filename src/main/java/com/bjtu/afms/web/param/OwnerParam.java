package com.bjtu.afms.web.param;

import lombok.Data;

@Data
public class OwnerParam {
    private String type;
    private int relateId;
    private int userId;
}
