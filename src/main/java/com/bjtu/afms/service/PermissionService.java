package com.bjtu.afms.service;

import com.bjtu.afms.enums.PermissionType;
import com.bjtu.afms.mapper.PermissionMapper;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.model.PermissionExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService {
    
    @Resource
    private PermissionMapper permissionMapper;
    
    public boolean hasPermission(int userId, List<Integer> types) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andUserIdEqualTo(userId).andTypeIn(types);
        long count = permissionMapper.countByExample(example);
        return count > 0;
    }

    public boolean isOwner(int userId, int relateId) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andTypeEqualTo(PermissionType.OWNER.getId())
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

//    public int updatePermission(Permission permission) {
//        return permissionMapper.updateByPrimaryKeySelective(permission);
//    }

    public Permission selectPermission(int permissionId) {
        return permissionMapper.selectByPrimaryKey(permissionId);
    }

    public List<Permission> selectPermissionList(Permission permission, String orderByClause) {
        PermissionExample example = new PermissionExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        PermissionExample.Criteria criteria = example.createCriteria();
        if (permission.getUserId() != null) {
            criteria.andUserIdEqualTo(permission.getUserId());
        }
        if (permission.getType() != null) {
            criteria.andTypeEqualTo(permission.getType());
        }
        if (permission.getRelateId() != null) {
            criteria.andRelateIdEqualTo(permission.getRelateId());
        }
        return permissionMapper.selectByExample(example);
    }
}
