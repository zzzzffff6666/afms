package com.bjtu.afms.service;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.mapper.*;
import com.bjtu.afms.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

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
    private LogMapper logMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private PlanMapper planMapper;

    @Resource
    private PoolCycleMapper poolCycleMapper;

    @Resource
    private PoolPlanMapper poolPlanMapper;

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

    public Object getResource(int type, int relateId) {
        switch (type) {
            case 1:
                return userMapper.selectByPrimaryKey(relateId);
            case 2:
                return permissionMapper.selectByPrimaryKey(relateId);
            case 3:
                return clientMapper.selectByPrimaryKey(relateId);
            case 4:
                return storeMapper.selectByPrimaryKey(relateId);
            case 5:
                return itemMapper.selectByPrimaryKey(relateId);
            case 6:
                return poolMapper.selectByPrimaryKey(relateId);
            case 7:
                return poolCycleMapper.selectByPrimaryKey(relateId);
            case 8:
                return taskMapper.selectByPrimaryKey(relateId);
            case 9:
                return planMapper.selectByPrimaryKey(relateId);
            case 10:
                return poolPlanMapper.selectByPrimaryKey(relateId);
            case 11:
                return poolTaskMapper.selectByPrimaryKey(relateId);
            case 12:
                return dailyTaskMapper.selectByPrimaryKey(relateId);
            case 13:
                return jobMapper.selectByPrimaryKey(relateId);
            case 14:
                return alertMapper.selectByPrimaryKey(relateId);
            case 15:
                return commentMapper.selectByPrimaryKey(relateId);
            case 16:
                return fundMapper.selectByPrimaryKey(relateId);
            default:
                return null;
        }
    }

    @Transactional(rollbackFor = SQLException.class)
    public boolean rollbackOperation(Log log) throws BizException {
        switch (OperationType.findOperationType(log.getOperationId())) {
            case INSERT_USER:
                userMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_USER:
                User user = JSON.parseObject(log.getOldValue(), User.class);
                return userMapper.insertSelective(user) >= 1;
            case UPDATE_USER_INFO:
                user = JSON.parseObject(log.getOldValue(), User.class);
                user.setId(log.getRelateId());
                return userMapper.updateByPrimaryKeySelective(user) >= 1;
            case INSERT_PERMISSION:
                permissionMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_PERMISSION:
                Permission permission = JSON.parseObject(log.getOldValue(), Permission.class);
                return permissionMapper.insertSelective(permission) >= 1;
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
            case UPDATE_ITEM_STORE:
                item = JSON.parseObject(log.getOldValue(), Item.class);
                item.setId(log.getRelateId());
                return itemMapper.updateByPrimaryKeySelective(item) >= 1;
            case INSERT_POOL:
                poolMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_POOL:
                Pool pool = JSON.parseObject(log.getOldValue(), Pool.class);
                return poolMapper.insertSelective(pool) >= 1;
            case UPDATE_POOL_INFO:
            case UPDATE_POOL_DETAIL:
                pool = JSON.parseObject(log.getOldValue(), Pool.class);
                pool.setId(log.getRelateId());
                return poolMapper.updateByPrimaryKeySelective(pool) >= 1;
            case INSERT_POOL_CYCLE:
                poolCycleMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_POOL_CYCLE:
                PoolCycle poolCycle = JSON.parseObject(log.getOldValue(), PoolCycle.class);
                return poolCycleMapper.insertSelective(poolCycle) >= 1;
            case UPDATE_POOL_CYCLE_FUND:
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
            case UPDATE_TASK_LEVEL:
                task = JSON.parseObject(log.getOldValue(), Task.class);
                task.setId(log.getRelateId());
                return taskMapper.updateByPrimaryKeySelective(task) >= 1;
            case INSERT_PLAN:
                planMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_PLAN:
                Plan plan = JSON.parseObject(log.getOldValue(), Plan.class);
                return planMapper.insertSelective(plan) >= 1;
            case UPDATE_PLAN_INFO:
                plan = JSON.parseObject(log.getOldValue(), Plan.class);
                plan.setId(log.getRelateId());
                return planMapper.updateByPrimaryKeySelective(plan) >= 1;
            case INSERT_POOL_PLAN:
                poolPlanMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_POOL_PLAN:
                PoolPlan poolPlan = JSON.parseObject(log.getOldValue(), PoolPlan.class);
                return poolPlanMapper.insertSelective(poolPlan) >= 1;
            case UPDATE_POOL_PLAN_ACT_TIME:
            case UPDATE_POOL_PLAN_FINISH:
                poolPlan = JSON.parseObject(log.getOldValue(), PoolPlan.class);
                poolPlan.setId(log.getRelateId());
                return poolPlanMapper.updateByPrimaryKeySelective(poolPlan) >= 1;
            case INSERT_POOL_TASK:
                poolTaskMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_POOL_TASK:
                PoolTask poolTask = JSON.parseObject(log.getOldValue(), PoolTask.class);
                return poolTaskMapper.insertSelective(poolTask) >= 1;
            case UPDATE_POOL_TASK_PRE_TIME:
            case UPDATE_POOL_TASK_STATUS:
            case UPDATE_POOL_TASK_USER:
                poolTask = JSON.parseObject(log.getOldValue(), PoolTask.class);
                poolTask.setId(log.getRelateId());
                return poolTaskMapper.updateByPrimaryKeySelective(poolTask) >= 1;
            case BATCH_INSERT_POOL_TASK:
                List<PoolTask> poolTaskList = JSON.parseArray(log.getNewValue(), PoolTask.class);
                for (PoolTask pt : poolTaskList) {
                    poolTaskMapper.deleteByPrimaryKey(pt.getId());
                }
                return true;
            case BATCH_DELETE_POOL_TASK:
                poolTaskList = JSON.parseArray(log.getOldValue(), PoolTask.class);
                for (PoolTask pt : poolTaskList) {
                    poolTaskMapper.insertSelective(pt);
                }
                return true;
            case INSERT_DAILY_TASK:
                dailyTaskMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_DAILY_TASK:
                DailyTask dailyTask = JSON.parseObject(log.getOldValue(), DailyTask.class);
                return dailyTaskMapper.insertSelective(dailyTask) >= 1;
            case UPDATE_DAILY_TASK_PRE_TIME:
            case UPDATE_DAILY_TASK_STATUS:
            case UPDATE_DAILY_TASK_USER:
                dailyTask = JSON.parseObject(log.getOldValue(), DailyTask.class);
                dailyTask.setId(log.getRelateId());
                return dailyTaskMapper.updateByPrimaryKeySelective(dailyTask) >= 1;
            case BATCH_INSERT_DAILY_TASK:
                List<DailyTask> dailyTaskList = JSON.parseArray(log.getNewValue(), DailyTask.class);
                for (DailyTask dt : dailyTaskList) {
                    dailyTaskMapper.deleteByPrimaryKey(dt.getId());
                }
                return true;
            case BATCH_DELETE_DAILY_TASK:
                dailyTaskList = JSON.parseArray(log.getOldValue(), DailyTask.class);
                for (DailyTask dt : dailyTaskList) {
                    dailyTaskMapper.insertSelective(dt);
                }
                return true;
            case INSERT_JOB:
                jobMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_JOB:
                Job job = JSON.parseObject(log.getOldValue(), Job.class);
                return jobMapper.insertSelective(job) >= 1;
            case UPDATE_JOB_DEADLINE:
            case UPDATE_JOB_STATUS:
            case UPDATE_JOB_USER:
                job = JSON.parseObject(log.getOldValue(), Job.class);
                job.setId(log.getRelateId());
                return jobMapper.updateByPrimaryKeySelective(job) >= 1;
            case BATCH_INSERT_JOB:
                List<Job> jobList = JSON.parseArray(log.getNewValue(), Job.class);
                for (Job j : jobList) {
                    jobMapper.deleteByPrimaryKey(j.getId());
                }
                return true;
            case BATCH_DELETE_JOB:
                jobList = JSON.parseArray(log.getOldValue(), Job.class);
                for (Job j : jobList) {
                    jobMapper.insertSelective(j);
                }
                return true;
            case INSERT_ALERT:
                alertMapper.deleteByPrimaryKey(log.getRelateId());
                return true;
            case DELETE_ALERT:
                Alert alert = JSON.parseObject(log.getOldValue(), Alert.class);
                return alertMapper.insertSelective(alert) >= 1;
            case UPDATE_ALERT_INFO:
            case UPDATE_ALERT_LEVEL:
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
