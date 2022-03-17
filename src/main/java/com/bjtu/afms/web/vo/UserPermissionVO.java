package com.bjtu.afms.web.vo;

import com.bjtu.afms.model.Permission;
import com.bjtu.afms.model.User;
import lombok.Data;

@Data
public class UserPermissionVO {
    private Permission permission;
    private User user;
}
