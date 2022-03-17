package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.model.User;
import com.bjtu.afms.web.vo.UserPermissionVO;

import java.util.List;

public interface PermissionBiz {

    Page<User> getResourceOwnerList(String type, int relateId, Integer page);

    boolean addResourceOwner(String type, int relateId, int userId);

    boolean addPermission(Permission permission);

    Page<Permission> getUserPermissionList(int userId, Integer page);

    Page<UserPermissionVO> getPermissionUserList(List<Integer> auths, Integer page);

    boolean initResourceOwner(int type, int relateId, int userId);

    boolean initUserPermission(int userId);
}
