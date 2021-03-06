package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.DailyTaskBiz;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.mapper.DailyTaskMapper;
import com.bjtu.afms.model.DailyTask;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.service.DailyTaskService;
import com.bjtu.afms.service.UserService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.BatchInsertDailyTaskParam;
import com.bjtu.afms.web.param.SetTaskParam;
import com.bjtu.afms.web.param.query.DailyTaskQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DailyTaskBizImpl implements DailyTaskBiz {

    @Resource
    private DailyTaskService dailyTaskService;

    @Resource
    private UserService userService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    private LogBiz logBiz;


    @Override
    public Page<DailyTask> getDailyTaskList(DailyTaskQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<DailyTask> pageInfo = new PageInfo<>(dailyTaskService.selectDailyTaskList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional
    public boolean insertDailyTask(DailyTask dailyTask) {
        dailyTask.setModTime(null);
        dailyTask.setModUser(null);
        dailyTask.setStartAct(null);
        dailyTask.setEndAct(null);
        dailyTask.setStatus(TaskStatus.CREATED.getId());
        if (dailyTaskService.insertDailyTask(dailyTask) == 1) {
            permissionBiz.initResourceOwner(DataType.DAILY_TASK.getId(), dailyTask.getId(), LoginContext.getUserId());
            permissionBiz.initResourceOwner(DataType.DAILY_TASK.getId(), dailyTask.getId(), dailyTask.getUserId());
            logBiz.saveLog(DataType.DAILY_TASK, dailyTask.getId(), OperationType.INSERT_DAILY_TASK,
                    null, JSON.toJSONString(dailyTask));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void batchInsertDailyTask(BatchInsertDailyTaskParam param) {
        int userId = param.getUserId() == null ? LoginContext.getUserId() : param.getUserId();
        List<DailyTask> dailyTaskList = new ArrayList<>();
        for (SetTaskParam setParam : param.getTaskList()) {
            DailyTask dailyTask = new DailyTask();
            dailyTask.setUserId(setParam.getUserId() == null ? userId : setParam.getUserId());
            dailyTask.setTaskId(setParam.getTaskId());
            dailyTask.setStartPre(setParam.getStartPre());
            dailyTask.setEndPre(setParam.getEndPre());
            dailyTask.setStatus(TaskStatus.CREATED.getId());
            dailyTaskList.add(dailyTask);
        }
        batchInsertDailyTask(dailyTaskList);
    }

    @Override
    @Transactional
    public void batchInsertDailyTask(List<DailyTask> dailyTaskList) {
        List<Permission> permissionList = new ArrayList<>();
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        DailyTaskMapper dailyTaskMapper = sqlSession.getMapper(DailyTaskMapper.class);
        dailyTaskList.forEach(dailyTaskMapper::insertSelective);
        sqlSession.commit();
        sqlSession.clearCache();
        sqlSession.close();
        logBiz.saveLog(DataType.DAILY_TASK, LoginContext.getUserId(), OperationType.BATCH_INSERT_DAILY_TASK,
                null, JSON.toJSONString(dailyTaskList));

        dailyTaskList.forEach(dailyTask -> {
            Permission permission1 = new Permission();
            permission1.setUserId(LoginContext.getUserId());
            permission1.setAuth(AuthType.OWNER.getId());
            permission1.setType(DataType.DAILY_TASK.getId());
            permission1.setRelateId(dailyTask.getId());
            permissionList.add(permission1);
            Permission permission2 = new Permission();
            permission2.setUserId(dailyTask.getUserId());
            permission2.setAuth(AuthType.OWNER.getId());
            permission2.setType(DataType.DAILY_TASK.getId());
            permission2.setRelateId(dailyTask.getId());
            permissionList.add(permission2);
        });
        permissionBiz.batchInsertPermission(permissionList);
    }

    @Override
    @Transactional
    public boolean modifyDailyTaskStatus(int id, int status) {
        DailyTask dailyTask = dailyTaskService.selectDailyTask(id);
        Assert.notNull(dailyTask, APIError.NOT_FOUND);
        Assert.isTrue(TaskStatus.changeCheck(dailyTask.getStatus(), status), APIError.TASK_STATUS_CHANGE_ERROR);

        DailyTask record = new DailyTask();
        record.setId(id);
        if (TaskStatus.isFinish(status)) {
            Date now = new Date();
            record.setEndAct(now);
            record.setStatus(TaskStatus.dateCompare(dailyTask.getEndPre(), now).getId());
        } else if (TaskStatus.isStart(status)) {
            record.setStartAct(new Date());
            record.setStatus(status);
        }
        if (dailyTaskService.updateDailyTask(record) == 1) {
            logBiz.saveLog(DataType.DAILY_TASK, id, OperationType.UPDATE_DAILY_TASK_STATUS,
                    JSON.toJSONString(dailyTask), JSON.toJSONString(record));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyDailyTaskUser(int id, int userId) {
        Assert.isTrue(userService.existId(userId), APIError.USER_NOT_EXIST);
        DailyTask dailyTask = dailyTaskService.selectDailyTask(id);
        Assert.notNull(dailyTask, APIError.NOT_FOUND);
        DailyTask record = new DailyTask();
        record.setId(id);
        record.setUserId(userId);
        if (dailyTaskService.updateDailyTask(dailyTask) == 1) {
            permissionBiz.initResourceOwner(DataType.DAILY_TASK.getId(), id, userId);
            permissionBiz.deleteResourceOwner(DataType.DAILY_TASK.getId(), id, dailyTask.getUserId());
            logBiz.saveLog(DataType.DAILY_TASK, id, OperationType.UPDATE_DAILY_TASK_USER,
                    JSON.toJSONString(dailyTask), JSON.toJSONString(record));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteDailyTask(int dailyTaskId) {
        DailyTask dailyTask = dailyTaskService.selectDailyTask(dailyTaskId);
        Assert.notNull(dailyTask, APIError.NOT_FOUND);
        if (dailyTaskService.deleteDailyTask(dailyTaskId) == 1) {
            permissionBiz.deleteResource(DataType.DAILY_TASK.getId(), dailyTaskId);
            logBiz.saveLog(DataType.DAILY_TASK, dailyTaskId, OperationType.DELETE_DAILY_TASK,
                    JSON.toJSONString(dailyTask), null);
            return true;
        } else {
            return false;
        }
    }
}
