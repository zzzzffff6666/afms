package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.model.PermissionExample;
import com.bjtu.afms.model.User;
import com.bjtu.afms.service.PermissionService;
import com.bjtu.afms.service.UserService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.PermissionQueryParam;
import com.bjtu.afms.web.vo.UserPermissionVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionBizImpl implements PermissionBiz {

    @Resource
    private PermissionService permissionService;

    @Resource
    private UserService userService;

    @Resource
    private ConfigUtil configUtil;

    @Override
    public Page<User> getResourceOwnerList(String type, int relateId, Integer page) {
        if (page == null) {
            page = 0;
        }
        DataType dataType = DataType.findDataType(type);
        PermissionQueryParam param = new PermissionQueryParam();
        param.setType(dataType.getId());
        param.setRelateId(relateId);
        param.setAuth(AuthType.OWNER.getId());

        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Permission> pageInfo = new PageInfo<>(permissionService.selectPermissionList(param));
        List<Integer> userIdList = pageInfo.getList().stream().map(Permission::getUserId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userIdList)) {
            return null;
        } else {
            return new Page<>(pageInfo, userService.selectUserListByIdList(userIdList));
        }
    }

    @Override
    public boolean addResourceOwner(String type, int relateId, int userId) {
        DataType dataType = DataType.findDataType(type);
        PermissionQueryParam param = new PermissionQueryParam();
        param.setAuth(AuthType.OWNER.getId());
        param.setUserId(LoginContext.getUserId());
        param.setType(dataType.getId());
        param.setRelateId(relateId);
        List<Permission> permissionList = permissionService.selectPermissionList(param);
        if (CollectionUtils.isEmpty(permissionList)) {
            throw new BizException(APIError.NO_PERMISSION);
        } else {
            param.setUserId(userId);
            permissionList = permissionService.selectPermissionList(param);
            if (CollectionUtils.isEmpty(permissionList)) {
                Permission permission = new Permission();
                permission.setAuth(AuthType.OWNER.getId());
                permission.setType(dataType.getId());
                permission.setRelateId(relateId);
                permission.setUserId(userId);
                return permissionService.insertPermission(permission) == 1;
            } else {
                throw new BizException(APIError.PERMISSION_ALREADY_EXIST);
            }

        }
    }

    @Override
    public boolean addPermission(Permission permission) {
        PermissionQueryParam param = new PermissionQueryParam();
        param.setUserId(permission.getUserId());
        param.setAuth(permission.getAuth());
        if (permission.getAuth() == AuthType.OWNER.getId()) {
            Assert.isTrue(permission.getType() != null && permission.getRelateId() != null,
                    APIError.PARAMETER_ERROR);
            param.setType(permission.getType());
            param.setRelateId(permission.getRelateId());
        }
        List<Permission> permissionList = permissionService.selectPermissionList(param);
        if (CollectionUtils.isEmpty(permissionList)) {
            Permission record = new Permission();
            record.setAuth(permission.getAuth());
            record.setUserId(permission.getUserId());
            if (permission.getAuth() == AuthType.OWNER.getId()) {
                record.setType(permission.getType());
                record.setRelateId(permission.getRelateId());
            }
            return permissionService.insertPermission(record) == 1;
        } else {
            throw new BizException(APIError.PERMISSION_ALREADY_EXIST);
        }
    }

    @Override
    public Page<Permission> getUserPermissionList(int userId, Integer page) {
        if (page == null) {
            page = 0;
        }
        PermissionQueryParam param = new PermissionQueryParam();
        param.setUserId(userId);
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Permission> pageInfo = new PageInfo<>(permissionService.selectPermissionList(param));
        return new Page<>(pageInfo);
    }

    @Override
    public Page<UserPermissionVO> getPermissionUserList(List<Integer> auths, Integer page) {
        if (CollectionUtils.isEmpty(auths)) {
            return null;
        } else {
            auths.removeIf(auth -> auth == AuthType.OWNER.getId());
            PageHelper.startPage(page, configUtil.getPageSize());
            PageInfo<Permission> pageInfo = new PageInfo<>(permissionService.selectPermissionListByAuth(auths));
            List<Integer> userIdList = pageInfo.getList()
                    .stream()
                    .map(Permission::getUserId)
                    .distinct()
                    .collect(Collectors.toList());
            List<User> userList = userService.selectUserListByIdList(userIdList);
            List<UserPermissionVO> userPermissionVOList = new ArrayList<>();
            for (Permission permission : pageInfo.getList()) {
                UserPermissionVO userPermissionVO = new UserPermissionVO();
                userPermissionVO.setPermission(permission);
                for (User user : userList) {
                    if (user.getId().equals(permission.getUserId())) {
                        userPermissionVO.setUser(user);
                        break;
                    }
                }
                userPermissionVOList.add(userPermissionVO);
            }
            return new Page<>(pageInfo, userPermissionVOList);
        }
    }

    @Override
    public void initUserPermission(int userId) {
        PermissionQueryParam param = new PermissionQueryParam();
        param.setAuth(AuthType.NORMAL.getId());
        param.setUserId(userId);
        List<Permission> permissionList = permissionService.selectPermissionList(param);
        if (CollectionUtils.isEmpty(permissionList)) {
            Permission permission = new Permission();
            permission.setAuth(AuthType.OWNER.getId());
            permission.setUserId(userId);
            if (permissionService.insertPermission(permission) != 1) {
                throw new BizException(APIError.INSERT_ERROR);
            }
        }
    }

    @Override
    public void initResourceOwner(int type, int relateId, int userId) {
        PermissionQueryParam param = new PermissionQueryParam();
        param.setAuth(AuthType.OWNER.getId());
        param.setUserId(userId);
        param.setType(type);
        param.setRelateId(relateId);
        List<Permission> permissionList = permissionService.selectPermissionList(param);
        if (CollectionUtils.isEmpty(permissionList)) {
            Permission permission = new Permission();
            permission.setAuth(AuthType.OWNER.getId());
            permission.setType(type);
            permission.setRelateId(relateId);
            permission.setUserId(userId);
            if (permissionService.insertPermission(permission) != 1) {
                throw new BizException(APIError.INSERT_ERROR);
            }
        }
    }

    @Override
    public void deleteUserPermission(int userId) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andUserIdEqualTo(userId);
        permissionService.deletePermissionByExample(example);
    }

    public void deleteResource(int type, int relateId) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andAuthEqualTo(AuthType.OWNER.getId()).andTypeEqualTo(type).andRelateIdEqualTo(relateId);
        permissionService.deletePermissionByExample(example);
    }

    @Override
    public void deleteResourceOwner(int type, int relateId, int userId) {
        PermissionExample example = new PermissionExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andAuthEqualTo(AuthType.OWNER.getId())
                .andTypeEqualTo(type)
                .andRelateIdEqualTo(relateId);
        permissionService.deletePermissionByExample(example);
    }
}
