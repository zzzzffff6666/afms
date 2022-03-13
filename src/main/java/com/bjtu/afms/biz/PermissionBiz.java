package com.bjtu.afms.biz;

import com.bjtu.afms.model.Permission;
import com.bjtu.afms.model.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PermissionBiz {

    PageInfo<User> getResourceOwnerList(String type, int relateId, Integer page);

    boolean addResourceOwner(int type, int relateId, int userId);

    boolean addPermission(Permission permission);

    PageInfo<Permission> getUserPermissionList(int userId, Integer page);

    PageInfo<User> getPermissionUserList(List<Integer> auths, Integer page);
}
