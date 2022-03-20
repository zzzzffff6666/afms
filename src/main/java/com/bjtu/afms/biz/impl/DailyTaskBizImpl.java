package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.DailyTaskBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.mapper.DailyTaskMapper;
import com.bjtu.afms.mapper.PermissionMapper;
import com.bjtu.afms.model.DailyTask;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.service.DailyTaskService;
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
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;


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
            return true;
        } else {
            return false;
        }
    }

    @Override
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
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        DailyTaskMapper dailyTaskMapper = sqlSession.getMapper(DailyTaskMapper.class);
        dailyTaskList.forEach(dailyTaskMapper::insertSelective);
        sqlSession.commit();
        sqlSession.clearCache();
        PermissionMapper permissionMapper = sqlSession.getMapper(PermissionMapper.class);
        dailyTaskList.forEach(dailyTask -> {
            Permission permission = new Permission();
            permission.setUserId(LoginContext.getUserId());
            permission.setAuth(AuthType.OWNER.getId());
            permission.setType(DataType.DAILY_TASK.getId());
            permission.setRelateId(dailyTask.getId());
            permissionMapper.insertSelective(permission);
            permission.setId(null);
            permission.setUserId(dailyTask.getUserId());
            permissionMapper.insertSelective(permission);
        });
        sqlSession.commit();
        sqlSession.clearCache();
    }

    @Override
    public boolean modifyDailyTaskStatus(int id, int status) {
        DailyTask dailyTask = dailyTaskService.selectDailyTask(id);
        if (dailyTask == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        if (TaskStatus.changeCheck(dailyTask.getStatus(), status)) {
            DailyTask record = new DailyTask();
            record.setId(id);
            record.setStatus(status);
            if (TaskStatus.isFinish(status)) {
                record.setEndAct(new Date());
            } else if (TaskStatus.isStart(status)) {
                record.setStartAct(new Date());
            }
            return dailyTaskService.updateDailyTask(record) == 1;
        } else {
            throw new BizException(APIError.TASK_STATUS_CHANGE_ERROR);
        }
    }

    @Override
    @Transactional
    public boolean modifyDailyTaskUser(int id, int userId) {
        DailyTask dailyTask = dailyTaskService.selectDailyTask(id);
        if (dailyTask == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        DailyTask record = new DailyTask();
        record.setId(id);
        record.setUserId(userId);
        if (dailyTaskService.updateDailyTask(dailyTask) == 1) {
            permissionBiz.initResourceOwner(DataType.DAILY_TASK.getId(), id, userId);
            permissionBiz.deleteResourceOwner(DataType.DAILY_TASK.getId(), id, dailyTask.getUserId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteDailyTask(int dailyTaskId) {
        permissionBiz.deleteResource(DataType.DAILY_TASK.getId(), dailyTaskId);
        return dailyTaskService.deleteDailyTask(dailyTaskId) == 1;
    }
}
