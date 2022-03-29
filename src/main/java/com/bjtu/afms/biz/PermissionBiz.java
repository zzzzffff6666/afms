package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.model.User;
import com.bjtu.afms.web.vo.UserPermissionVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PermissionBiz {

    Page<User> getResourceOwnerList(String type, int relateId, Integer page);

    boolean addResourceOwner(String type, int relateId, int userId);

    boolean addPermission(Permission permission);

    Page<Permission> getUserPermissionList(int userId, Integer page);

    Page<UserPermissionVO> getPermissionUserList(List<Integer> auths, Integer page);

    void initUserPermission(int userId);

    void initResourceOwner(int type, int relateId, int userId);

    void initResourceOwner(int type, int relateId, Set<Integer> userIdSet);

    void batchInsertPermission(List<Permission> permissionList);

    void batchDeletePermission(List<Permission> permissionList);

    void deleteUserPermission(int userId);

    void deleteResource(int type, int relateId);

    void deleteResource(int type, List<Integer> relateIdList);

    void deleteResourceOwner(int type, int relateId, int userId);
}
