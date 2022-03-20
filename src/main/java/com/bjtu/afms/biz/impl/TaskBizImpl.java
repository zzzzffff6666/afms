package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.TaskBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.exception.BizException;
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
import org.springframework.util.CollectionUtils;

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
        if (taskService.insertTask(task) == 1) {
            permissionBiz.initResourceOwner(DataType.TASK.getId(), task.getId(), LoginContext.getUserId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteTask(int taskId) {
        List<DailyTask> dailyTaskList = dailyTaskService.selectUnfinishedTaskList(taskId);
        if (!CollectionUtils.isEmpty(dailyTaskList)) {
            throw new BizException(APIError.TASK_NOW_USED);
        }
        List<PoolTask> poolTaskList = poolTaskService.selectUnfinishedTaskList(taskId);
        if (!CollectionUtils.isEmpty(poolTaskList)) {
            throw new BizException(APIError.TASK_NOW_USED);
        }
        permissionBiz.deleteResource(DataType.TASK.getId(), taskId);
        return taskService.deleteTask(taskId) == 1;
    }
}
