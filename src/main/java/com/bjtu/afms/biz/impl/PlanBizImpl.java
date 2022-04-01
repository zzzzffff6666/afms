package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.PlanBiz;
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
import com.bjtu.afms.model.Pool;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.service.PlanService;
import com.bjtu.afms.service.PoolService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.utils.DateUtil;
import com.bjtu.afms.web.param.query.PlanQueryParam;
import com.bjtu.afms.web.pojo.ErrorInfo;
import com.bjtu.afms.web.pojo.PlanTask;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PlanBizImpl implements PlanBiz {

    @Resource
    private PlanService planService;

    @Resource
    private PoolTaskBiz poolTaskBiz;

    @Resource
    private PoolService poolService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private LogBiz logBiz;

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
    @Transactional
    public boolean insertPlan(Plan plan) {
        plan.setFinish(PlanFinish.CREATED.getId());
        plan.setModTime(null);
        plan.setModUser(null);
        if (planService.insertPlan(plan) == 1) {
            permissionBiz.initResourceOwner(DataType.PLAN.getId(), plan.getId(), LoginContext.getUserId());
            logBiz.saveLog(DataType.PLAN, plan.getId(), OperationType.INSERT_PLAN,
                    null, JSON.toJSONString(plan));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public ErrorInfo applyPlan(int planId) {
        Plan plan = planService.selectPlan(planId);
        Assert.notNull(plan, APIError.NOT_FOUND);

        Plan record = new Plan();
        record.setId(planId);
        record.setApplyTime(new Date());
        record.setFinish(PlanFinish.APPLIED.getId());
        Assert.isTrue(planService.updatePlan(record) == 1, APIError.PLAN_APPLY_FAILED);
        logBiz.saveLog(DataType.PLAN, planId, OperationType.APPLY_PLAN,
                JSON.toJSONString(plan), JSON.toJSONString(record));

        List<PlanTask> taskList = JSON.parseArray(plan.getTaskList(), PlanTask.class);
        Set<Integer> poolIdSet = taskList.stream()
                .flatMap(task -> task.getPoolIdList().stream())
                .collect(Collectors.toSet());
        List<Pool> poolList = poolService.selectPoolByIdList(new ArrayList<>(poolIdSet));
        Map<Integer, Integer> poolCycleMap = poolList.stream()
                .collect(Collectors.toMap(Pool::getId, Pool::getCurrentCycle));

        ErrorInfo errorInfo = new ErrorInfo();
        List<PoolTask> poolTaskList = new ArrayList<>();
        for (PlanTask planTask : taskList) {
            for (int poolId : planTask.getPoolIdList()) {
                if (!poolCycleMap.containsKey(poolId)) {
                    errorInfo.failedAdd(String.format("任务创建失败：taskId=%d，poolId=%d", planTask.getTaskId(), poolId));
                    continue;
                }
                PoolTask poolTask = new PoolTask();
                poolTask.setPoolId(poolId);
                poolTask.setCycle(poolCycleMap.get(poolId) + 1);
                poolTask.setPlanId(planId);
                poolTask.setUserId(planTask.getUserId());
                poolTask.setTaskId(planTask.getTaskId());
                poolTask.setStatus(TaskStatus.CREATED.getId());
                poolTask.setStartPre(planTask.getStartTime());
                poolTask.setEndPre(DateUtil.plusDays(planTask.getTaskDuration(), planTask.getStartTime()));
                poolTaskList.add(poolTask);
                errorInfo.successAdd();
            }
        }
        poolTaskBiz.batchInsertPoolTask(poolTaskList);
        return errorInfo;
    }

    @Override
    @Transactional
    public boolean modifyPlanFinish(int id, int finish) {
        Plan old = planService.selectPlan(id);
        Assert.notNull(old, APIError.NOT_FOUND);
        Assert.isTrue(PlanFinish.changeCheck(old.getFinish(), finish), APIError.PLAN_FINISH_CHANGE_ERROR);

        Plan record = new Plan();
        record.setId(id);
        record.setFinish(finish);
        if (PlanFinish.isStart(finish)) {
            record.setApplyTime(new Date());
        }
        if (PlanFinish.isFinish(finish)) {
            record.setFinishTime(new Date());
        }
        if (planService.updatePlan(record) == 1) {
            logBiz.saveLog(DataType.PLAN, id, OperationType.UPDATE_PLAN_FINISH,
                    JSON.toJSONString(old), JSON.toJSONString(record));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deletePlan(int planId) {
        Plan old = planService.selectPlan(planId);
        Assert.notNull(old, APIError.NOT_FOUND);
        if (planService.deletePlan(planId) == 1) {
            permissionBiz.deleteResource(DataType.PLAN.getId(), planId);
            logBiz.saveLog(DataType.PLAN, planId, OperationType.DELETE_PLAN,
                    JSON.toJSONString(old), null);
            return true;
        } else {
            return false;
        }
    }
}
