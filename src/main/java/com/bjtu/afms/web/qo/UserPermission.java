package com.bjtu.afms.web.qo;

import com.bjtu.afms.model.Permission;
import com.bjtu.afms.model.User;
import lombok.Data;

@Data
public class UserPermission {
    private Permission permission;
    private User user;
}
