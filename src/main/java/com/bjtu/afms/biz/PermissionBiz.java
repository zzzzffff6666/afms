package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.model.User;
import com.bjtu.afms.web.qo.UserPermission;

import java.util.List;

public interface PermissionBiz {

    Page<User> getResourceOwnerList(String type, int relateId, Integer page);

    boolean addResourceOwner(int type, int relateId, int userId);

    boolean addPermission(Permission permission);

    Page<Permission> getUserPermissionList(int userId, Integer page);

    Page<UserPermission> getPermissionUserList(List<Integer> auths, Integer page);
}
