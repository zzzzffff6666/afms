package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.PoolPlanBiz;
import com.bjtu.afms.biz.PoolTaskBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.enums.PlanFinish;
import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Plan;
import com.bjtu.afms.model.PoolCycle;
import com.bjtu.afms.model.PoolPlan;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.service.PlanService;
import com.bjtu.afms.service.PoolCycleService;
import com.bjtu.afms.service.PoolPlanService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.utils.DateUtil;
import com.bjtu.afms.web.param.query.PoolCycleQueryParam;
import com.bjtu.afms.web.param.query.PoolPlanQueryParam;
import com.bjtu.afms.web.pojo.PlanTask;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class PoolPlanBizImpl implements PoolPlanBiz {

    @Resource
    private PoolPlanService poolPlanService;

    @Resource
    private PlanService planService;

    @Resource
    private PoolCycleService poolCycleService;

    @Resource
    private PoolTaskBiz poolTaskBiz;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private LogBiz logBiz;

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
    @Transactional
    public boolean insertPoolPlan(PoolPlan poolPlan) {
        PoolCycleQueryParam param = new PoolCycleQueryParam();
        param.setPoolId(poolPlan.getPoolId());
        param.setCycle(poolPlan.getCycle());
        List<PoolCycle> poolCycleList = poolCycleService.selectPoolCycleList(param);
        Assert.notEmpty(poolCycleList, APIError.NOT_FOUND);
        PoolCycle poolCycle = poolCycleList.get(0);
        Plan plan = planService.selectPlan(poolPlan.getPlanId());
        Assert.notNull(plan, APIError.NOT_FOUND);
        poolPlan.setStartAct(null);
        poolPlan.setEndAct(null);
        poolPlan.setFinish(PlanFinish.CREATED.getId());
        poolPlan.setModTime(null);
        poolPlan.setModUser(null);
        long endPre = 0L;
        List<PoolTask> poolTaskList = new ArrayList<>();
        List<PlanTask> planTaskList = JSON.parseArray(plan.getTaskList(), PlanTask.class);
        for (PlanTask planTask : planTaskList) {
            PoolTask poolTask = new PoolTask();
            poolTask.setPoolId(poolPlan.getPoolId());
            poolTask.setCycle(poolPlan.getCycle());
            poolTask.setTaskId(planTask.getTaskId());
            poolTask.setUserId(poolCycle.getUserId());
            poolTask.setStatus(TaskStatus.CREATED.getId());
            poolTask.setStartPre(DateUtil.plusMillis(planTask.getStartOffset(), poolPlan.getStartPre()));
            poolTask.setEndPre(DateUtil.plusMillis(planTask.getEndOffset(), poolPlan.getStartPre()));
            endPre = Math.max(endPre, planTask.getEndOffset());
            poolTaskList.add(poolTask);
        }
        poolPlan.setEndPre(DateUtil.plusMillis(endPre, poolPlan.getStartPre()));
        if (poolPlanService.insertPoolPlan(poolPlan) == 1) {
            Plan record = new Plan();
            record.setId(poolPlan.getPlanId());
            record.setUseNum(plan.getUseNum() + 1);
            planService.updatePlan(record);
            poolTaskBiz.batchInsertPoolTask(poolTaskList);
            permissionBiz.initResourceOwner(DataType.POOL_PLAN.getId(), poolPlan.getId(), LoginContext.getUserId());
            logBiz.saveLog(DataType.POOL_PLAN, poolPlan.getId(), OperationType.INSERT_POOL_PLAN,
                    null, JSON.toJSONString(poolPlan));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyPoolPlanTime(int id, int finish) {
        PoolPlan poolPlan = poolPlanService.selectPoolPlan(id);
        Assert.notNull(poolPlan, APIError.NOT_FOUND);
        Assert.isTrue(PlanFinish.changeCheck(poolPlan.getFinish(), finish), APIError.PLAN_FINISH_CHANGE_ERROR);
        PoolPlan record = new PoolPlan();
        record.setId(id);
        if (PlanFinish.isFinish(finish)) {
            Date now = new Date();
            record.setEndAct(now);
            record.setFinish(PlanFinish.dateCompare(poolPlan.getEndPre(), now).getId());
        } else if (PlanFinish.isStart(finish)) {
            record.setStartAct(new Date());
            record.setFinish(finish);
        }
        if (poolPlanService.updatePoolPlan(record) == 1) {
            logBiz.saveLog(DataType.POOL_PLAN, poolPlan.getId(), OperationType.UPDATE_POOL_PLAN_FINISH,
                    JSON.toJSONString(poolPlan), JSON.toJSONString(record));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deletePoolPlan(int poolPlanId) {
        PoolPlan poolPlan = poolPlanService.selectPoolPlan(poolPlanId);
        Assert.notNull(poolPlan, APIError.NOT_FOUND);
        if (poolPlanService.deletePoolPlan(poolPlanId) == 1) {
            permissionBiz.deleteResource(DataType.POOL_PLAN.getId(), poolPlanId);
            logBiz.saveLog(DataType.POOL_PLAN, poolPlanId, OperationType.DELETE_POOL_PLAN,
                    JSON.toJSONString(poolPlan), null);
            return true;
        } else {
            return false;
        }
    }
}
