package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.PoolTaskBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.mapper.PoolTaskMapper;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.model.PoolCycle;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.service.PoolCycleService;
import com.bjtu.afms.service.PoolTaskService;
import com.bjtu.afms.service.UserService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.BatchInsertPoolTaskParam;
import com.bjtu.afms.web.param.SetTaskParam;
import com.bjtu.afms.web.param.query.PoolTaskQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class PoolTaskBizImpl implements PoolTaskBiz {

    @Resource
    private PoolTaskService poolTaskService;

    @Resource
    private UserService userService;

    @Resource
    private PoolCycleService poolCycleService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    private LogBiz logBiz;

    @Override
    public Page<PoolTask> getPoolTaskList(PoolTaskQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<PoolTask> pageInfo = new PageInfo<>(poolTaskService.selectPoolTaskList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional
    public boolean insertPoolTask(PoolTask poolTask) {
        poolTask.setStatus(TaskStatus.CREATED.getId());
        poolTask.setModTime(null);
        poolTask.setModUser(null);
        poolTask.setStartAct(null);
        poolTask.setEndAct(null);
        if (poolTaskService.insertPoolTask(poolTask) == 1) {
            permissionBiz.initResourceOwner(DataType.POOL_TASK.getId(), poolTask.getId(), LoginContext.getUserId());
            permissionBiz.initResourceOwner(DataType.POOL_TASK.getId(), poolTask.getId(), poolTask.getUserId());
            logBiz.saveLog(DataType.POOL_TASK, poolTask.getId(), OperationType.INSERT_POOL_TASK,
                    null, JSON.toJSONString(poolTask));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void batchInsertPoolTask(BatchInsertPoolTaskParam param) {
        PoolCycle poolCycle = poolCycleService.selectPoolCycle(param.getPoolCycleId());
        Assert.notNull(poolCycle, APIError.NOT_FOUND);
        int userId = param.getUserId() == null ? poolCycle.getUserId() : param.getUserId();
        List<PoolTask> poolTaskList = new ArrayList<>();
        for (SetTaskParam setParam : param.getTaskList()) {
            PoolTask poolTask = new PoolTask();
            poolTask.setPoolId(poolCycle.getPoolId());
            poolTask.setCycle(poolCycle.getCycle());
            poolTask.setTaskId(setParam.getTaskId());
            poolTask.setUserId(setParam.getUserId() == null ? userId : setParam.getUserId());
            poolTask.setStatus(TaskStatus.CREATED.getId());
            poolTask.setStartPre(setParam.getStartPre());
            poolTask.setEndPre(setParam.getEndPre());
            poolTaskList.add(poolTask);
        }
        batchInsertPoolTask(poolTaskList);
    }

    @Override
    @Transactional
    public void batchInsertPoolTask(List<PoolTask> poolTaskList) {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        PoolTaskMapper poolTaskMapper = sqlSession.getMapper(PoolTaskMapper.class);
        poolTaskList.forEach(poolTaskMapper::insertSelective);
        sqlSession.commit();
        sqlSession.clearCache();
        sqlSession.close();
        logBiz.saveLog(DataType.POOL_TASK, LoginContext.getUserId(), OperationType.BATCH_INSERT_POOL_TASK,
                null, JSON.toJSONString(poolTaskList));

        List<Permission> permissionList = new ArrayList<>();
        poolTaskList.forEach(poolTask -> {
            Permission permission1 = new Permission();
            permission1.setUserId(LoginContext.getUserId());
            permission1.setAuth(AuthType.OWNER.getId());
            permission1.setType(DataType.POOL_TASK.getId());
            permission1.setRelateId(poolTask.getId());
            permissionList.add(permission1);
            Permission permission2 = new Permission();
            permission2.setUserId(poolTask.getUserId());
            permission2.setAuth(AuthType.OWNER.getId());
            permission2.setType(DataType.POOL_TASK.getId());
            permission2.setRelateId(poolTask.getId());
            permissionList.add(permission2);
        });
        permissionBiz.batchInsertPermission(permissionList);
    }

    @Override
    @Transactional
    public boolean modifyPoolTaskStatus(int id, int status) {
        PoolTask poolTask = poolTaskService.selectPoolTask(id);
        Assert.notNull(poolTask, APIError.NOT_FOUND);
        Assert.isTrue(TaskStatus.changeCheck(poolTask.getStatus(), status), APIError.TASK_STATUS_CHANGE_ERROR);
        PoolTask record = new PoolTask();
        record.setId(id);
        if (TaskStatus.isFinish(status)) {
            Date now = new Date();
            record.setEndAct(now);
            record.setStatus(TaskStatus.dateCompare(poolTask.getEndPre(), now).getId());
        } else if (TaskStatus.isStart(status)) {
            record.setStartAct(new Date());
            record.setStatus(status);
        }
        if (poolTaskService.updatePoolTask(record) == 1) {
            logBiz.saveLog(DataType.POOL_TASK, id, OperationType.UPDATE_POOL_TASK_STATUS,
                    JSON.toJSONString(poolTask), JSON.toJSONString(record));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyPoolTaskUser(int id, int userId) {
        Assert.isTrue(userService.existId(userId), APIError.USER_NOT_EXIST);
        PoolTask poolTask = poolTaskService.selectPoolTask(id);
        Assert.notNull(poolTask, APIError.NOT_FOUND);
        PoolTask record = new PoolTask();
        record.setId(id);
        record.setUserId(userId);
        if (poolTaskService.updatePoolTask(record) == 1) {
            permissionBiz.initResourceOwner(DataType.POOL_TASK.getId(), id, userId);
            permissionBiz.deleteResourceOwner(DataType.POOL_TASK.getId(), id, poolTask.getUserId());
            logBiz.saveLog(DataType.POOL_TASK, id, OperationType.UPDATE_POOL_TASK_USER,
                    JSON.toJSONString(poolTask), JSON.toJSONString(record));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deletePoolTask(int poolTaskId) {
        PoolTask poolTask = poolTaskService.selectPoolTask(poolTaskId);
        Assert.notNull(poolTask, APIError.NOT_FOUND);
        if (poolTaskService.deletePoolTask(poolTaskId) == 1) {
            permissionBiz.deleteResource(DataType.POOL_TASK.getId(), poolTaskId);
            logBiz.saveLog(DataType.POOL_TASK, poolTaskId, OperationType.DELETE_POOL_TASK,
                    JSON.toJSONString(poolTask), null);
            return true;
        } else {
            return false;
        }
    }
}
