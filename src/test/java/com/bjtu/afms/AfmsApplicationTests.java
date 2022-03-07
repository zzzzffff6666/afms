package com.bjtu.afms;

import com.bjtu.afms.mapper.LogMapper;
import com.bjtu.afms.mapper.PermissionMapper;
import com.bjtu.afms.model.Log;
import com.bjtu.afms.model.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AfmsApplicationTests {

    @Resource
    private LogMapper logMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Test
    void contextLoads() {
        Log log = new Log();
        log.setType(0);
        log.setRelateId(0);
        log.setUserId(0);
        log.setOperation("do");
        log.setNewValue("11");
        logMapper.insert(log);
    }

    @Test
    void test1() {
        Permission permission = new Permission();
        permission.setUserId(0);
        permission.setType(0);
        permissionMapper.insert(permission);
    }

}
