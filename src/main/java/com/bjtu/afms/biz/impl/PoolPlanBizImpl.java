package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.PoolPlanBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.PlanFinish;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Plan;
import com.bjtu.afms.model.PoolPlan;
import com.bjtu.afms.service.PlanService;
import com.bjtu.afms.service.PoolPlanService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.PoolPlanQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class PoolPlanBizImpl implements PoolPlanBiz {

    @Resource
    private PoolPlanService poolPlanService;

    @Resource
    private PlanService planService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Override
    public Page<PoolPlan> getPoolPlanList(PoolPlanQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<PoolPlan> pageInfo = new PageInfo<>(poolPlanService.selectPoolPlanList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertPoolPlan(PoolPlan poolPlan) {
        Plan plan = planService.selectPlan(poolPlan.getPlanId());
        if (plan == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        poolPlan.setStartAct(null);
        poolPlan.setEndAct(null);
        poolPlan.setFinish(PlanFinish.CREATED.getId());
        poolPlan.setModTime(null);
        poolPlan.setModUser(null);
        if (poolPlanService.insertPoolPlan(poolPlan) == 1) {
            Plan record = new Plan();
            record.setId(poolPlan.getPlanId());
            record.setUseNum(plan.getUseNum() + 1);
            planService.updatePlan(record);
            return permissionBiz.initResourceOwner(DataType.POOL_PLAN.getId(), poolPlan.getId(), LoginContext.getUserId());
        } else {
            return false;
        }
    }

    @Override
    public boolean modifyPoolPlanTime(int id, int finish) {
        PoolPlan poolPlan = poolPlanService.selectPoolPlan(id);
        if (poolPlan == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        if (PlanFinish.changeCheck(poolPlan.getFinish(), finish)) {
            PoolPlan record = new PoolPlan();
            record.setId(id);
            if (PlanFinish.isFinish(finish)) {
                record.setEndAct(new Date());
            } else if (PlanFinish.isStart(finish)) {
                record.setStartAct(new Date());
            }
            record.setFinish(finish);
            return poolPlanService.updatePoolPlan(record) == 1;
        } else {
            throw new BizException(APIError.PLAN_FINISH_CHANGE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePoolPlan(int poolPlanId) {
        permissionBiz.deleteResourceOwner(DataType.POOL_PLAN.getId(), poolPlanId);
        return poolPlanService.deletePoolPlan(poolPlanId) == 1;
    }
}
