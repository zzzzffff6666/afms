package com.bjtu.afms.service;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.mapper.*;
import com.bjtu.afms.model.*;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonService {

    @Resource
    private AlertMapper alertMapper;

    @Resource
    private ClientMapper clientMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private DailyTaskMapper dailyTaskMapper;

    @Resource
    private FundMapper fundMapper;

    @Resource
    private ItemMapper itemMapper;

    @Resource
    private JobMapper jobMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private PlanMapper planMapper;

    @Resource
    private PoolCycleMapper poolCycleMapper;

    @Resource
    private PoolMapper poolMapper;

    @Resource
    private PoolTaskMapper poolTaskMapper;

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private WorkplaceMapper workplaceMapper;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public Object getResource(int type, int relateId) {
        DataType dataType = DataType.findDataType(type);
        Assert.notNull(dataType, APIError.UNKNOWN_DATA_TYPE);
        switch (dataType) {
            case USER:
                return userMapper.selectByPrimaryKey(relateId);
            case PERMISSION:
                return permissionMapper.selectByPrimaryKey(relateId);
            case CLIENT:
                return clientMapper.selectByPrimaryKey(relateId);
            case STORE:
                return storeMapper.selectByPrimaryKey(relateId);
            case ITEM:
                return itemMapper.selectByPrimaryKey(relateId);
            case POOL:
                return poolMapper.selectByPrimaryKey(relateId);
            case POOL_CYCLE:
                return poolCycleMapper.selectByPrimaryKey(relateId);
            case TASK:
                return taskMapper.selectByPrimaryKey(relateId);
            case PLAN:
                return planMapper.selectByPrimaryKey(relateId);
            case POOL_TASK:
                return poolTaskMapper.selectByPrimaryKey(relateId);
            case DAILY_TASK:
                return dailyTaskMapper.selectByPrimaryKey(relateId);
            case JOB:
                return jobMapper.selectByPrimaryKey(relateId);
            case ALERT:
                return alertMapper.selectByPrimaryKey(relateId);
            case COMMENT:
                return commentMapper.selectByPrimaryKey(relateId);
            case FUND:
                return fundMapper.selectByPrimaryKey(relateId);
            default:
                return null;
        }
    }

    @Transactional(rollbackFor = SQLException.class)
    public boolean rollbackOperation(Log log) throws BizException {
        OperationType operationType = OperationType.findOperationType(log.getOperationId());
        Assert.notNull(operationType, APIError.UNKNOWN_OPERATION_TYPE);
        switch (operationType) {
            case INSERT_USER:
                userMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_USER:
                User user = JSON.parseObject(log.getOldValue(), User.class);
                return userMapper.insertSelective(user) >= 1;
            case UPDATE_USER_STATUS:
                user = JSON.parseObject(log.getOldValue(), User.class);
                user.setId(log.getRelateId());
                return userMapper.updateByPrimaryKeySelective(user) >= 1;
            case INSERT_PERMISSION:
                permissionMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_PERMISSION:
                Permission permission = JSON.parseObject(log.getOldValue(), Permission.class);
                return permissionMapper.insertSelective(permission) >= 1;
            case BATCH_INSERT_PERMISSION:
                List<Permission> permissionList = JSON.parseArray(log.getNewValue(), Permission.class);
                PermissionExample permissionExample = new PermissionExample();
                permissionExample.createCriteria().andIdIn(permissionList.stream().map(Permission::getId).collect(Collectors.toList()));
                permissionMapper.deleteByExample(permissionExample);
                return true;
            case BATCH_DELETE_PERMISSION:
                SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                PermissionMapper permissionMapper1 = sqlSession.getMapper(PermissionMapper.class);
                permissionList = JSON.parseArray(log.getOldValue(), Permission.class);
                for (Permission record : permissionList) {
                    permissionMapper1.insertSelective(record);
                }
                sqlSession.commit();
                sqlSession.clearCache();
                sqlSession.close();
                return true;
            case INSERT_CLIENT:
                clientMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_CLIENT:
                Client client = JSON.parseObject(log.getOldValue(), Client.class);
                return clientMapper.insertSelective(client) >= 1;
            case UPDATE_CLIENT_INFO:
                client = JSON.parseObject(log.getOldValue(), Client.class);
                client.setId(log.getRelateId());
                return clientMapper.updateByPrimaryKeySelective(client) >= 1;
            case INSERT_STORE:
                storeMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_STORE:
                Store store = JSON.parseObject(log.getOldValue(), Store.class);
                return storeMapper.insertSelective(store) >= 1;
            case UPDATE_STORE_INFO:
            case UPDATE_STORE_MANAGER:
                store = JSON.parseObject(log.getOldValue(), Store.class);
                store.setId(log.getRelateId());
                return storeMapper.updateByPrimaryKeySelective(store) >= 1;
            case INSERT_ITEM:
                itemMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_ITEM:
                Item item = JSON.parseObject(log.getOldValue(), Item.class);
                return itemMapper.insertSelective(item) >= 1;
            case UPDATE_ITEM_INFO:
            case UPDATE_ITEM_STATUS:
                item = JSON.parseObject(log.getOldValue(), Item.class);
                item.setId(log.getRelateId());
                return itemMapper.updateByPrimaryKeySelective(item) >= 1;
            case INSERT_WORKPLACE:
                workplaceMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_WORKPLACE:
                Workplace workplace = JSON.parseObject(log.getOldValue(), Workplace.class);
                return workplaceMapper.insertSelective(workplace) >= 1;
            case UPDATE_WORKPLACE_INFO:
                workplace = JSON.parseObject(log.getOldValue(), Workplace.class);
                workplace.setId(log.getRelateId());
                return workplaceMapper.updateByPrimaryKeySelective(workplace) >= 1;
            case INSERT_POOL:
                poolMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_POOL:
                Pool pool = JSON.parseObject(log.getOldValue(), Pool.class);
                return poolMapper.insertSelective(pool) >= 1;
            case UPDATE_POOL_INFO:
            case UPDATE_POOL_CURRENT_CYCLE:
                pool = JSON.parseObject(log.getOldValue(), Pool.class);
                pool.setId(log.getRelateId());
                return poolMapper.updateByPrimaryKeySelective(pool) >= 1;
            case INSERT_POOL_CYCLE:
                poolCycleMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_POOL_CYCLE:
                PoolCycle poolCycle = JSON.parseObject(log.getOldValue(), PoolCycle.class);
                return poolCycleMapper.insertSelective(poolCycle) >= 1;
            case UPDATE_POOL_CYCLE_STATUS:
            case UPDATE_POOL_CYCLE_USER:
                poolCycle = JSON.parseObject(log.getOldValue(), PoolCycle.class);
                poolCycle.setId(log.getRelateId());
                return poolCycleMapper.updateByPrimaryKeySelective(poolCycle) >= 1;
            case INSERT_TASK:
                taskMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_TASK:
                Task task = JSON.parseObject(log.getOldValue(), Task.class);
                return taskMapper.insertSelective(task) >= 1;
            case UPDATE_TASK_INFO:
                task = JSON.parseObject(log.getOldValue(), Task.class);
                task.setId(log.getRelateId());
                return taskMapper.updateByPrimaryKeySelective(task) >= 1;
            case INSERT_PLAN:
                planMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_PLAN:
                Plan plan = JSON.parseObject(log.getOldValue(), Plan.class);
                return planMapper.insertSelective(plan) >= 1;
            case UPDATE_PLAN_FINISH:
                plan = JSON.parseObject(log.getOldValue(), Plan.class);
                plan.setId(log.getRelateId());
                return planMapper.updateByPrimaryKeySelective(plan) >= 1;
            case INSERT_POOL_TASK:
                poolTaskMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_POOL_TASK:
                PoolTask poolTask = JSON.parseObject(log.getOldValue(), PoolTask.class);
                return poolTaskMapper.insertSelective(poolTask) >= 1;
            case UPDATE_POOL_TASK_STATUS:
            case UPDATE_POOL_TASK_USER:
                poolTask = JSON.parseObject(log.getOldValue(), PoolTask.class);
                poolTask.setId(log.getRelateId());
                return poolTaskMapper.updateByPrimaryKeySelective(poolTask) >= 1;
            case BATCH_INSERT_POOL_TASK:
                List<PoolTask> poolTaskList = JSON.parseArray(log.getNewValue(), PoolTask.class);
                PoolTaskExample poolTaskExample = new PoolTaskExample();
                poolTaskExample.createCriteria().andIdIn(poolTaskList.stream().map(PoolTask::getId).collect(Collectors.toList()));
                poolTaskMapper.deleteByExample(poolTaskExample);
                return true;
            case BATCH_DELETE_POOL_TASK:
                sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                PoolTaskMapper poolTaskMapper1 = sqlSession.getMapper(PoolTaskMapper.class);
                poolTaskList = JSON.parseArray(log.getOldValue(), PoolTask.class);
                for (PoolTask pt : poolTaskList) {
                    poolTaskMapper1.insertSelective(pt);
                }
                sqlSession.commit();
                sqlSession.clearCache();
                sqlSession.close();
                return true;
            case INSERT_DAILY_TASK:
                dailyTaskMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_DAILY_TASK:
                DailyTask dailyTask = JSON.parseObject(log.getOldValue(), DailyTask.class);
                return dailyTaskMapper.insertSelective(dailyTask) >= 1;
            case UPDATE_DAILY_TASK_STATUS:
            case UPDATE_DAILY_TASK_USER:
                dailyTask = JSON.parseObject(log.getOldValue(), DailyTask.class);
                dailyTask.setId(log.getRelateId());
                return dailyTaskMapper.updateByPrimaryKeySelective(dailyTask) >= 1;
            case BATCH_INSERT_DAILY_TASK:
                List<DailyTask> dailyTaskList = JSON.parseArray(log.getNewValue(), DailyTask.class);
                DailyTaskExample dailyTaskExample = new DailyTaskExample();
                dailyTaskExample.createCriteria().andIdIn(dailyTaskList.stream().map(DailyTask::getId).collect(Collectors.toList()));
                dailyTaskMapper.deleteByExample(dailyTaskExample);
                return true;
            case BATCH_DELETE_DAILY_TASK:
                sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                DailyTaskMapper dailyTaskMapper1 = sqlSession.getMapper(DailyTaskMapper.class);
                dailyTaskList = JSON.parseArray(log.getOldValue(), DailyTask.class);
                for (DailyTask dt : dailyTaskList) {
                    dailyTaskMapper1.insertSelective(dt);
                }
                sqlSession.commit();
                sqlSession.clearCache();
                sqlSession.close();
                return true;
            case INSERT_JOB:
                jobMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_JOB:
                Job job = JSON.parseObject(log.getOldValue(), Job.class);
                return jobMapper.insertSelective(job) >= 1;
            case UPDATE_JOB_STATUS:
            case BATCH_INSERT_JOB:
                List<Job> jobList = JSON.parseArray(log.getNewValue(), Job.class);
                JobExample jobExample = new JobExample();
                jobExample.createCriteria().andIdIn(jobList.stream().map(Job::getId).collect(Collectors.toList()));
                jobMapper.deleteByExample(jobExample);
                return true;
            case BATCH_DELETE_JOB:
                sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                JobMapper jobMapper1 = sqlSession.getMapper(JobMapper.class);
                jobList = JSON.parseArray(log.getOldValue(), Job.class);
                for (Job j : jobList) {
                    jobMapper1.insertSelective(j);
                }
                sqlSession.commit();
                sqlSession.clearCache();
                sqlSession.close();
                return true;
            case INSERT_ALERT:
                alertMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_ALERT:
                Alert alert = JSON.parseObject(log.getOldValue(), Alert.class);
                return alertMapper.insertSelective(alert) >= 1;
            case UPDATE_ALERT_INFO:
            case UPDATE_ALERT_STATUS:
            case UPDATE_ALERT_USER:
                alert = JSON.parseObject(log.getOldValue(), Alert.class);
                alert.setId(log.getRelateId());
                return alertMapper.updateByPrimaryKeySelective(alert) >= 1;
            case INSERT_COMMENT:
                commentMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_COMMENT:
                Comment comment = JSON.parseObject(log.getOldValue(), Comment.class);
                return commentMapper.insertSelective(comment) >= 1;
            case UPDATE_COMMENT:
                comment = JSON.parseObject(log.getOldValue(), Comment.class);
                comment.setId(log.getRelateId());
                return commentMapper.updateByPrimaryKeySelective(comment) >= 1;
            case INSERT_FUND:
                fundMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_FUND:
                Fund fund = JSON.parseObject(log.getOldValue(), Fund.class);
                return fundMapper.insertSelective(fund) >= 1;
            case UPDATE_FUND_INFO:
                fund = JSON.parseObject(log.getOldValue(), Fund.class);
                fund.setId(log.getRelateId());
                return fundMapper.updateByPrimaryKeySelective(fund) >= 1;
            default:
                throw new BizException(APIError.OPERATION_CANNOT_ROLLBACK);
        }
    }
}
