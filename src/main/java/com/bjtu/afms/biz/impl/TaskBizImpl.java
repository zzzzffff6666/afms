package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.TaskBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.enums.UrgentLevel;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.DailyTask;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.model.Task;
import com.bjtu.afms.service.DailyTaskService;
import com.bjtu.afms.service.PoolTaskService;
import com.bjtu.afms.service.TaskService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.TaskQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TaskBizImpl implements TaskBiz {

    @Resource
    private TaskService taskService;

    @Resource
    private PoolTaskService poolTaskService;

    @Resource
    private DailyTaskService dailyTaskService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private LogBiz logBiz;

    @Override
    public Page<Task> getTaskList(TaskQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Task> pageInfo = new PageInfo<>(taskService.selectTaskList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional
    public boolean insertTask(Task task) {
        task.setModTime(null);
        task.setModUser(null);
        if (task.getLevel() == null) {
            task.setLevel(UrgentLevel.U1.getId());
        }
        if (taskService.insertTask(task) == 1) {
            permissionBiz.initResourceOwner(DataType.TASK.getId(), task.getId(), LoginContext.getUserId());
            logBiz.saveLog(DataType.TASK, task.getId(), OperationType.INSERT_TASK,
                    null, JSON.toJSONString(task));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyTaskInfo(Task task) {
        Task old = taskService.selectTask(task.getId());
        Assert.notNull(old, APIError.NOT_FOUND);
        task.setAddTime(null);
        task.setAddUser(null);
        if (taskService.updateTask(task) == 1) {
            logBiz.saveLog(DataType.TASK, task.getId(), OperationType.UPDATE_TASK_INFO,
                    JSON.toJSONString(old), JSON.toJSONString(task));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteTask(int taskId) {
        List<DailyTask> dailyTaskList = dailyTaskService.selectUnfinishedTaskList(taskId);
        Assert.isEmpty(dailyTaskList, APIError.TASK_NOW_USED);
        List<PoolTask> poolTaskList = poolTaskService.selectUnfinishedTaskList(taskId);
        Assert.isEmpty(poolTaskList, APIError.TASK_NOW_USED);
        Task task = taskService.selectTask(taskId);
        Assert.isNull(task, APIError.NOT_FOUND);
        if (taskService.deleteTask(taskId) == 1) {
            permissionBiz.deleteResource(DataType.TASK.getId(), taskId);
            logBiz.saveLog(DataType.TASK, taskId, OperationType.DELETE_TASK,
                    JSON.toJSONString(task), null);
            return true;
        } else {
            return false;
        }
    }
}
