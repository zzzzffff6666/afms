package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.PoolTaskBiz;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.service.JobService;
import com.bjtu.afms.service.PoolTaskService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.PoolTaskQueryParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PoolTaskBizImpl implements PoolTaskBiz {

    @Resource
    private PoolTaskService poolTaskService;

    @Resource
    private JobService jobService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Override
    public Page<PoolTask> getPoolTaskList(PoolTaskQueryParam param, Integer page) {
        return null;
    }

    @Override
    public boolean insertPoolTask(PoolTask poolTask) {
        return false;
    }

    @Override
    public boolean deletePoolTask(int poolTaskId) {
        return false;
    }
}
