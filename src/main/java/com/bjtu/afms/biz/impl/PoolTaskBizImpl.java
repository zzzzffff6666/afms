package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.PoolTaskBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.mapper.PermissionMapper;
import com.bjtu.afms.mapper.PoolTaskMapper;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.model.PoolCycle;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.service.PoolCycleService;
import com.bjtu.afms.service.PoolTaskService;
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
    private PoolCycleService poolCycleService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

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
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void batchInsertPoolTask(BatchInsertPoolTaskParam param) {
        PoolCycle poolCycle = poolCycleService.selectPoolCycle(param.getPoolCycleId());
        if (poolCycle == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
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
        PermissionMapper permissionMapper = sqlSession.getMapper(PermissionMapper.class);
        poolTaskList.forEach(poolTask -> {
            Permission permission = new Permission();
            permission.setUserId(LoginContext.getUserId());
            permission.setAuth(AuthType.OWNER.getId());
            permission.setType(DataType.POOL_TASK.getId());
            permission.setRelateId(poolTask.getId());
            permissionMapper.insertSelective(permission);
            permission.setId(null);
            permission.setUserId(poolTask.getUserId());
            permissionMapper.insertSelective(permission);
        });
        sqlSession.commit();
        sqlSession.clearCache();
    }

    @Override
    public boolean modifyPoolTaskStatus(int id, int status) {
        PoolTask poolTask = poolTaskService.selectPoolTask(id);
        if (poolTask == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        if (TaskStatus.changeCheck(poolTask.getStatus(), status)) {
            PoolTask record = new PoolTask();
            record.setId(id);
            record.setStatus(status);
            if (TaskStatus.isFinish(status)) {
                record.setEndAct(new Date());
            } else if (TaskStatus.isStart(status)) {
                record.setStartAct(new Date());
            }
            return poolTaskService.updatePoolTask(record) == 1;
        } else {
            throw new BizException(APIError.TASK_STATUS_CHANGE_ERROR);
        }
    }

    @Override
    @Transactional
    public boolean modifyPoolTaskUser(int id, int userId) {
        PoolTask poolTask = poolTaskService.selectPoolTask(id);
        if (poolTask == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        PoolTask record = new PoolTask();
        record.setId(id);
        record.setUserId(userId);
        if (poolTaskService.updatePoolTask(poolTask) == 1) {
            permissionBiz.initResourceOwner(DataType.POOL_TASK.getId(), id, userId);
            permissionBiz.deleteResourceOwner(DataType.POOL_TASK.getId(), id, poolTask.getUserId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deletePoolTask(int poolTaskId) {
        permissionBiz.deleteResource(DataType.POOL_TASK.getId(), poolTaskId);
        return poolTaskService.deletePoolTask(poolTaskId) == 1;
    }
}
