package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.PoolBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.enums.PoolStatus;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Pool;
import com.bjtu.afms.service.PoolService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.PoolQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class PoolBizImpl implements PoolBiz {

    @Resource
    private PoolService poolService;

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
        pool.setDetail(null);
        pool.setAddTime(null);
        pool.setAddUser(null);
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
    public boolean modifyPoolDetail(int id, String detail) {
        Pool pool = new Pool();
        pool.setId(id);
        pool.setDetail(detail);
        return poolService.updatePool(pool) == 1;
    }

    @Override
    @Transactional
    public boolean deletePool(int poolId) {
        Pool old = poolService.selectPool(poolId);
        Assert.notNull(old, APIError.NOT_FOUND);
        if (poolService.deletePool(poolId) == 1) {
            permissionBiz.deleteResource(DataType.POOL.getId(), poolId);
            logBiz.saveLog(DataType.POOL, poolId, OperationType.DELETE_POOL,
                    JSON.toJSONString(old), null);
            return true;
        } else {
            return false;
        }
    }
}
