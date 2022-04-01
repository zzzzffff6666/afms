package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.PoolCycleBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Pool;
import com.bjtu.afms.model.PoolCycle;
import com.bjtu.afms.service.PoolCycleService;
import com.bjtu.afms.service.PoolService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.PoolCycleQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class PoolCycleBizImpl implements PoolCycleBiz {

    @Resource
    private PoolCycleService poolCycleService;

    @Resource
    private PoolService poolService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private LogBiz logBiz;

    @Override
    public PoolCycle getPoolCurrentCycle(int poolId) {
        Pool pool = poolService.selectPool(poolId);
        Assert.notNull(pool, APIError.NOT_FOUND);

        PoolCycleQueryParam param = new PoolCycleQueryParam();
        param.setOrderBy("cycle desc");
        param.setPoolId(poolId);
        PageHelper.startPage(0, 1);
        List<PoolCycle> poolCycleList = poolCycleService.selectPoolCycleList(param);
        Assert.notEmpty(poolCycleList, APIError.NOT_FOUND);
        return poolCycleList.get(0);
    }

    @Override
    public Page<PoolCycle> getPoolCycleList(PoolCycleQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<PoolCycle> pageInfo = new PageInfo<>(poolCycleService.selectPoolCycleList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional
    public boolean insertPoolCycle(PoolCycle poolCycle) {
        if (poolCycleService.insertPoolCycle(poolCycle) == 1) {
            permissionBiz.initResourceOwner(DataType.POOL_CYCLE.getId(), poolCycle.getId(), LoginContext.getUserId());
            permissionBiz.initResourceOwner(DataType.POOL_CYCLE.getId(), poolCycle.getId(), poolCycle.getUserId());
            logBiz.saveLog(DataType.POOL_CYCLE, poolCycle.getId(), OperationType.INSERT_POOL_CYCLE,
                    null, JSON.toJSONString(poolCycle));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyPoolCycleUser(int id, int userId) {
        PoolCycle poolCycle = poolCycleService.selectPoolCycle(id);
        Assert.notNull(poolCycle, APIError.NOT_FOUND);
        PoolCycle record = new PoolCycle();
        record.setId(id);
        record.setUserId(userId);
        if (poolCycleService.updatePoolCycle(record) == 1) {
            permissionBiz.initResourceOwner(DataType.POOL_CYCLE.getId(), id, userId);
            permissionBiz.deleteResourceOwner(DataType.POOL_CYCLE.getId(), id, poolCycle.getUserId());
            logBiz.saveLog(DataType.POOL_CYCLE, id, OperationType.UPDATE_POOL_CYCLE_USER,
                    JSON.toJSONString(poolCycle), JSON.toJSONString(record));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyPoolCycleStatus(int id, int status) {
        PoolCycle poolCycle = poolCycleService.selectPoolCycle(id);
        Assert.notNull(poolCycle, APIError.NOT_FOUND);
        Assert.isTrue(status != TaskStatus.OVERDUE.getId() && TaskStatus.changeCheck(poolCycle.getStatus(), status),
                APIError.TASK_STATUS_CHANGE_ERROR);

        PoolCycle record = new PoolCycle();
        record.setId(id);
        record.setStatus(status);
        if (TaskStatus.isFinish(status)) {
            record.setEndTime(new Date());
        } else if (TaskStatus.isStart(status)) {
            record.setStartTime(new Date());
        }
        if (poolCycleService.updatePoolCycle(record) == 1) {
            logBiz.saveLog(DataType.POOL_CYCLE, id, OperationType.UPDATE_POOL_CYCLE_STATUS,
                    JSON.toJSONString(poolCycle), JSON.toJSONString(record));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deletePoolCycle(int poolCycleId) {
        PoolCycle poolCycle = poolCycleService.selectPoolCycle(poolCycleId);
        Assert.notNull(poolCycle, APIError.NOT_FOUND);

        if (poolCycleService.deletePoolCycle(poolCycleId) == 1) {
            permissionBiz.deleteResource(DataType.POOL_CYCLE.getId(), poolCycleId);
            logBiz.saveLog(DataType.POOL_CYCLE, poolCycleId, OperationType.DELETE_POOL_CYCLE,
                    JSON.toJSONString(poolCycle), null);
            return true;
        } else {
            return false;
        }
    }
}
