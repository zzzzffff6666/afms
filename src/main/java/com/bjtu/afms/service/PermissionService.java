package com.bjtu.afms.service;

import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.mapper.PermissionMapper;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.model.PermissionExample;
import com.bjtu.afms.web.param.query.PermissionQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService {
    
    @Resource
    private PermissionMapper permissionMapper;
    
    public boolean hasPermission(int userId, List<Integer> authIdList) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andUserIdEqualTo(userId).andAuthIn(authIdList);
        long count = permissionMapper.countByExample(example);
        return count > 0;
    }

    public boolean isOwner(int userId, int type, int relateId) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andAuthEqualTo(AuthType.OWNER.getId())
                .andTypeEqualTo(type)
                .andRelateIdEqualTo(relateId);
        long count = permissionMapper.countByExample(example);
        return count > 0;
    }

    public int insertPermission(Permission permission) {
        return permissionMapper.insertSelective(permission);
    }

    public int deletePermission(int permissionId) {
        return permissionMapper.deleteByPrimaryKey(permissionId);
    }

    public void deleteUserPermission(int userId) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andUserIdEqualTo(userId);
        permissionMapper.deleteByExample(example);
    }

    public Permission selectPermission(int permissionId) {
        return permissionMapper.selectByPrimaryKey(permissionId);
    }

    public List<Permission> selectPermissionList(PermissionQueryParam param) {
        PermissionExample example = new PermissionExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        PermissionExample.Criteria criteria = example.createCriteria();
        if (param.getUserId() != null) {
            criteria.andUserIdEqualTo(param.getUserId());
        }
        if (param.getAuth() != null) {
            criteria.andAuthEqualTo(param.getAuth());
        }
        if (param.getType() != null) {
            criteria.andTypeEqualTo(param.getType());
        }
        if (param.getRelateId() != null) {
            criteria.andRelateIdEqualTo(param.getRelateId());
        }
        return permissionMapper.selectByExample(example);
    }

    public List<Permission> selectPermissionListByAuth(List<Integer> auths) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andAuthIn(auths);
        return permissionMapper.selectByExample(example);
    }
}
