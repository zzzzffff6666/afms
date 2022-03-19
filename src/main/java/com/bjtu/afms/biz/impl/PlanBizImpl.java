package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.PlanBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Plan;
import com.bjtu.afms.model.PoolCycle;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.service.PlanService;
import com.bjtu.afms.service.PoolCycleService;
import com.bjtu.afms.service.PoolTaskService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.ImportPlanParam;
import com.bjtu.afms.web.param.query.PlanQueryParam;
import com.bjtu.afms.web.param.query.PoolTaskQueryParam;
import com.bjtu.afms.web.pojo.PlanTask;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlanBizImpl implements PlanBiz {

    @Resource
    private PlanService planService;

    @Resource
    private PoolCycleService poolCycleService;

    @Resource
    private PoolTaskService poolTaskService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Override
    public Page<Plan> getPlanList(PlanQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Plan> pageInfo = new PageInfo<>(planService.selectPlanList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertPlan(Plan plan) {
        plan.setUseNum(0);
        plan.setModTime(null);
        plan.setModUser(null);
        if (planService.insertPlan(plan) == 1) {
            return permissionBiz.initResourceOwner(DataType.PLAN.getId(), plan.getId(), LoginContext.getUserId());
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean importPlan(ImportPlanParam param) {
        PoolCycle poolCycle;
        if (param.getPoolCycleId() != null) {
            poolCycle = poolCycleService.selectPoolCycle(param.getPoolCycleId());
        } else {
            throw new BizException(APIError.PARAMETER_ERROR);
        }
        if (poolCycle == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        PoolTaskQueryParam param1 = new PoolTaskQueryParam();
        param1.setPoolId(param.getPoolId());
        param1.setCycle(param.getCycle());
        List<PoolTask> poolTaskList = poolTaskService.selectPoolTaskList(param1);
        List<PlanTask> taskList = new ArrayList<>();
        long start = poolCycle.getStartTime().getTime();
        for (PoolTask poolTask : poolTaskList) {
            PlanTask planTask = new PlanTask();
            planTask.setTaskId(poolTask.getTaskId());
            planTask.setStartOffset(poolTask.getStartAct().getTime() - start);
            planTask.setEndOffset(poolTask.getEndAct().getTime() - start);
            taskList.add(planTask);
        }
        Plan plan = new Plan();
        plan.setName(param.getName());
        plan.setUseNum(0);
        plan.setTaskList(JSON.toJSONString(taskList));
        if (planService.insertPlan(plan) == 1) {
            return permissionBiz.initResourceOwner(DataType.PLAN.getId(), plan.getId(), LoginContext.getUserId());
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePlan(int planId) {
        permissionBiz.deleteResourceOwner(DataType.PLAN.getId(), planId);
        return planService.deletePlan(planId) == 1;
    }
}
