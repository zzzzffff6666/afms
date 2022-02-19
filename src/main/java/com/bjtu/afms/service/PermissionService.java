package com.bjtu.afms.service;

import com.bjtu.afms.enums.PermissionType;
import com.bjtu.afms.mapper.PermissionMapper;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.model.PermissionExample;
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
        List<Permission> permissions = permissionMapper.selectByExample(example);
        return !permissions.isEmpty();
    }

    public boolean isOwner(int userId, int relateId) {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andTypeEqualTo(PermissionType.OWNER.getId())
                .andRelateIdEqualTo(relateId);
        List<Permission> permissions = permissionMapper.selectByExample(example);
        return !permissions.isEmpty();
    }
}
