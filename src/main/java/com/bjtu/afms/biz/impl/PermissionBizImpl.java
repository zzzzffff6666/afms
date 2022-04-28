package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.mapper.PermissionMapper;
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
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PermissionBizImpl implements PermissionBiz {

    @Resource
    private PermissionService permissionService;

    @Resource
    private UserService userService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    private LogBiz logBiz;

    @Override
    public Page<User> getResourceOwnerList(String type, int relateId, Integer page) {
        if (page == null) {
            page = 0;
        }
        DataType dataType = DataType.findDataType(type);
        Assert.notNull(dataType, APIError.UNKNOWN_DATA_TYPE);
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
            return new Page<>(pageInfo, userService.selectUserByIdList(userIdList));
        }
    }

    @Override
    @Transactional
    public boolean addResourceOwner(String type, int relateId, int userId) {
        DataType dataType = DataType.findDataType(type);
        Assert.notNull(dataType, APIError.UNKNOWN_DATA_TYPE);
        PermissionQueryParam param = new PermissionQueryParam();
        param.setAuth(AuthType.OWNER.getId());
        param.setUserId(LoginContext.getUserId());
        param.setType(dataType.getId());
        param.setRelateId(relateId);
        List<Permission> permissionList = permissionService.selectPermissionList(param);
        Assert.notEmpty(permissionList, APIError.NO_PERMISSION);

        param.setUserId(userId);
        List<Permission> permissionList1 = permissionService.selectPermissionList(param);
        Assert.isEmpty(permissionList1, APIError.PERMISSION_ALREADY_EXIST);

        Permission permission = new Permission();
        permission.setAuth(AuthType.OWNER.getId());
        permission.setType(dataType.getId());
        permission.setRelateId(relateId);
        permission.setUserId(userId);
        if (permissionService.insertPermission(permission) == 1) {
            logBiz.saveLog(DataType.PERMISSION, permission.getId(), OperationType.INSERT_PERMISSION,
                    null, JSON.toJSONString(permission));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean addPermission(Permission permission) {
        PermissionQueryParam param = new PermissionQueryParam();
        param.setUserId(permission.getUserId());
        param.setAuth(permission.getAuth());
        if (permission.getAuth() == AuthType.OWNER.getId()) {
            Assert.isTrue(permission.getType() != null && permission.getRelateId() != null, APIError.PARAMETER_ERROR);
            param.setType(permission.getType());
            param.setRelateId(permission.getRelateId());
        }
        List<Permission> permissionList = permissionService.selectPermissionList(param);
        Assert.isEmpty(permissionList, APIError.PERMISSION_ALREADY_EXIST);

        Permission record = new Permission();
        record.setAuth(permission.getAuth());
        record.setUserId(permission.getUserId());
        if (permission.getAuth() == AuthType.OWNER.getId()) {
            record.setType(permission.getType());
            record.setRelateId(permission.getRelateId());
        }
        if (permissionService.insertPermission(record) == 1) {
            logBiz.saveLog(DataType.PERMISSION, record.getId(), OperationType.INSERT_PERMISSION,
                    null, JSON.toJSONString(record));
            return true;
        } else {
            return false;
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
            List<User> userList = userService.selectUserByIdList(userIdList);
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
    @Transactional
    public void initUserPermission(int userId) {
        PermissionQueryParam param = new PermissionQueryParam();
        param.setAuth(AuthType.NORMAL.getId());
        param.setUserId(userId);
        List<Permission> permissionList = permissionService.selectPermissionList(param);
        if (CollectionUtils.isEmpty(permissionList)) {
            Permission permission = new Permission();
            permission.setAuth(AuthType.NORMAL.getId());
            permission.setUserId(userId);
            Assert.isTrue(permissionService.insertPermission(permission) == 1, APIError.INSERT_ERROR);
            logBiz.saveLog(DataType.PERMISSION, permission.getId(), OperationType.INSERT_PERMISSION,
                    null, JSON.toJSONString(permission));
        }
    }

    @Override
    @Transactional
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
            Assert.isTrue(permissionService.insertPermission(permission) == 1, APIError.INSERT_ERROR);
            logBiz.saveLog(DataType.PERMISSION, permission.getId(), OperationType.INSERT_PERMISSION,
                    null, JSON.toJSONString(permission));
        }
    }

    @Override
    @Transactional
    public void initResourceOwner(int type, int relateId, Set<Integer> userIdSet) {
        PermissionQueryParam param = new PermissionQueryParam();
        param.setAuth(AuthType.OWNER.getId());
        param.setType(type);
        param.setRelateId(relateId);
        List<Integer> existUserIdList = permissionService.selectPermissionList(param).stream()
                .map(Permission::getUserId)
                .collect(Collectors.toList());
        userIdSet.removeAll(existUserIdList);
        List<Permission> permissionList = new ArrayList<>();
        Date date = new Date();
        userIdSet.forEach(userId -> {
            Permission permission = new Permission();
            permission.setAuth(AuthType.OWNER.getId());
            permission.setType(type);
            permission.setRelateId(relateId);
            permission.setUserId(userId);
            permission.setAddTime(date);
            permission.setAddUser(LoginContext.getUserId());
            permissionList.add(permission);
        });
        batchInsertPermission(permissionList);
    }

    @Override
    @Transactional
    public void batchInsertPermission(List<Permission> permissionList) {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        PermissionMapper permissionMapper1 = sqlSession.getMapper(PermissionMapper.class);
        permissionList.forEach(permissionMapper1::insertSelective);
        sqlSession.commit();
        sqlSession.clearCache();
        sqlSession.close();
        logBiz.saveLog(DataType.PERMISSION, LoginContext.getUserId(), OperationType.BATCH_INSERT_PERMISSION,
                null, JSON.toJSONString(permissionList));
    }

    @Override
    @Transactional
    public void batchDeletePermission(List<Permission> permissionList) {
        List<Integer> idList = permissionList.stream().map(Permission::getId).collect(Collectors.toList());
        PermissionExample example = new PermissionExample();
        example.createCriteria().andIdIn(idList);
        permissionService.deleteByExample(example);
        logBiz.saveLog(DataType.PERMISSION, LoginContext.getUserId(), OperationType.BATCH_DELETE_PERMISSION,
                JSON.toJSONString(permissionList), null);
    }

    @Override
    @Transactional
    public void deleteUserPermission(int userId) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<Permission> permissionList = permissionService.selectByExample(example);
        permissionService.deleteByExample(example);
        logBiz.saveLog(DataType.PERMISSION, LoginContext.getUserId(), OperationType.BATCH_DELETE_PERMISSION,
                JSON.toJSONString(permissionList), null);
    }

    @Override
    @Transactional
    public void deleteResource(int type, int relateId) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andAuthEqualTo(AuthType.OWNER.getId()).andTypeEqualTo(type).andRelateIdEqualTo(relateId);
        List<Permission> permissionList = permissionService.selectByExample(example);
        permissionService.deleteByExample(example);
        logBiz.saveLog(DataType.PERMISSION, LoginContext.getUserId(), OperationType.BATCH_DELETE_PERMISSION,
                JSON.toJSONString(permissionList), null);
    }

    @Override
    @Transactional
    public void deleteResource(int type, List<Integer> relateIdList) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andAuthEqualTo(AuthType.OWNER.getId()).andTypeEqualTo(type).andRelateIdIn(relateIdList);
        List<Permission> permissionList = permissionService.selectByExample(example);
        permissionService.deleteByExample(example);
        logBiz.saveLog(DataType.PERMISSION, LoginContext.getUserId(), OperationType.BATCH_DELETE_PERMISSION,
                JSON.toJSONString(permissionList), null);
    }

    @Override
    @Transactional
    public void deleteResourceOwner(int type, int relateId, int userId) {
        PermissionExample example = new PermissionExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andAuthEqualTo(AuthType.OWNER.getId())
                .andTypeEqualTo(type)
                .andRelateIdEqualTo(relateId);
        List<Permission> permissionList = permissionService.selectByExample(example);
        if (permissionList.size() == 0) {
            return;
        }
        permissionService.deleteByExample(example);
        logBiz.saveLog(DataType.PERMISSION, permissionList.get(0).getId(), OperationType.DELETE_PERMISSION,
                JSON.toJSONString(permissionList.get(0)), null);
    }
}
