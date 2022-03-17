package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.PoolCycleBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.PoolCycle;
import com.bjtu.afms.service.PoolCycleService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.ModifyCycleFundParam;
import com.bjtu.afms.web.param.query.PoolCycleQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class PoolCycleBizImpl implements PoolCycleBiz {

    @Resource
    private PoolCycleService poolCycleService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Override
    public PoolCycle getPoolCurrentCycle(int poolId) {
        PoolCycleQueryParam param = new PoolCycleQueryParam();
        param.setOrderBy("cycle desc");
        param.setPoolId(poolId);
        PageHelper.startPage(0, 1);
        List<PoolCycle> poolCycleList = poolCycleService.selectPoolCycleList(param);
        if (CollectionUtils.isEmpty(poolCycleList)) {
            throw new BizException(APIError.NOT_FOUND);
        } else {
            return poolCycleList.get(0);
        }
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
    @Transactional(rollbackFor = Exception.class)
    public boolean insertPoolCycle(PoolCycle poolCycle) {
        PoolCycle record = new PoolCycle();
        record.setPoolId(poolCycle.getPoolId());
        record.setCycle(poolCycle.getCycle());
        record.setUserId(poolCycle.getUserId());
        record.setStartTime(new Date());
        record.setStatus(TaskStatus.CREATED.getId());
        if (poolCycleService.insertPoolCycle(record) == 1) {
            return permissionBiz.initResourceOwner(DataType.POOL_CYCLE.getId(), poolCycle.getId(), LoginContext.getUserId());
        } else {
            return false;
        }
    }

    @Override
    public boolean modifyPoolCycleFund(ModifyCycleFundParam param) {
        PoolCycle poolCycle = poolCycleService.selectPoolCycle(param.getId());
        if (poolCycle == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        PoolCycle record = new PoolCycle();
        record.setId(param.getId());
        if (param.getCost() != null) {
            BigDecimal sum = poolCycle.getCost() == null ? new BigDecimal(0)  : poolCycle.getCost();
            record.setCost(param.getCost().add(sum));
        }
        if (param.getIncome() != null) {
            BigDecimal sum = poolCycle.getIncome() == null ? new BigDecimal(0) : poolCycle.getIncome();
            record.setIncome(param.getIncome().add(sum));
        }
        return poolCycleService.updatePoolCycle(record) == 1;
    }
}
