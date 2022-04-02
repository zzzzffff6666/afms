package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.*;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.enums.PoolStatus;
import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Pool;
import com.bjtu.afms.model.PoolCycle;
import com.bjtu.afms.service.PoolCycleService;
import com.bjtu.afms.service.PoolService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.PoolQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class PoolBizImpl implements PoolBiz {

    @Resource
    private PoolService poolService;

    @Resource
    private PoolCycleService poolCycleService;

    @Resource
    private PoolCycleBiz poolCycleBiz;

    @Resource
    private WorkplaceBiz workplaceBiz;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private LogBiz logBiz;

    @Override
    public Page<Pool> getPoolList(PoolQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Pool> pageInfo = new PageInfo<>(poolService.selectPoolList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional
    public boolean insertPool(Pool pool) {
        pool.setStatus(PoolStatus.EMPTY.getId());
        pool.setModTime(null);
        pool.setModUser(null);
        if (poolService.insertPool(pool) == 1) {
            workplaceBiz.modifyWorkplacePoolNum(pool.getPlace(), 1);
            permissionBiz.initResourceOwner(DataType.POOL.getId(), pool.getId(), LoginContext.getUserId());
            logBiz.saveLog(DataType.POOL, pool.getId(), OperationType.INSERT_POOL,
                    null, JSON.toJSONString(pool));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyPoolInfo(Pool pool) {
        Pool old = poolService.selectPool(pool.getId());
        Assert.notNull(old, APIError.NOT_FOUND);
        pool.setAddTime(null);
        pool.setAddUser(null);
        pool.setPlace(null);
        pool.setOrdinal(null);
        if (poolService.updatePool(pool) == 1) {
            logBiz.saveLog(DataType.POOL, pool.getId(), OperationType.UPDATE_POOL_INFO,
                    JSON.toJSONString(old), JSON.toJSONString(pool));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean startPoolNewCycle(int id, int userId) {
        Pool old = poolService.selectPool(id);
        Assert.notNull(old, APIError.NOT_FOUND);
        PoolCycle poolCycle = poolCycleService.selectPoolCycle(id, old.getCurrentCycle());
        Assert.notNull(old, APIError.NOT_FOUND);
        Assert.isTrue(TaskStatus.isFinish(poolCycle.getStatus()), APIError.CURRENT_CYCLE_NOT_FINISH);
        int currentCycle = old.getCurrentCycle() + 1;
        Pool record = new Pool();
        record.setId(id);
        record.setCurrentCycle(currentCycle);
        PoolCycle newCycle = new PoolCycle();
        newCycle.setPoolId(id);
        newCycle.setCycle(currentCycle);
        newCycle.setUserId(userId);
        newCycle.setStartTime(new Date());
        newCycle.setStatus(TaskStatus.HANDLING.getId());
        if (poolService.updatePool(record) == 1) {
            logBiz.saveLog(DataType.POOL, id, OperationType.UPDATE_POOL_CURRENT_CYCLE,
                    JSON.toJSONString(old), JSON.toJSONString(record));
            Assert.isTrue(poolCycleBiz.insertPoolCycle(newCycle), APIError.INSERT_ERROR);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deletePool(int poolId) {
        Pool old = poolService.selectPool(poolId);
        Assert.notNull(old, APIError.NOT_FOUND);
        if (poolService.deletePool(poolId) == 1) {
            workplaceBiz.modifyWorkplacePoolNum(old.getPlace(), -1);
            permissionBiz.deleteResource(DataType.POOL.getId(), poolId);
            logBiz.saveLog(DataType.POOL, poolId, OperationType.DELETE_POOL,
                    JSON.toJSONString(old), null);
            return true;
        } else {
            return false;
        }
    }
}
