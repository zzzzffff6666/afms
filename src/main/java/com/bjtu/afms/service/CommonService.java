package com.bjtu.afms.service;

import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.mapper.*;
import com.bjtu.afms.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Resource
    private VerifyMapper verifyMapper;

    public Object getResource(DataType dataType, int relateId) {
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
            case POOL_PLAN:
                return poolPlanMapper.selectByPrimaryKey(relateId);
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

    public boolean rollbackOperation(Log log) {
        switch (log.getType()) {
            case 1:
                User user = userMapper.selectByPrimaryKey(log.getRelateId());
            case 2:
                Permission permission = permissionMapper.selectByPrimaryKey(log.getRelateId());
            case 3:
                Client client = clientMapper.selectByPrimaryKey(log.getRelateId());
            case 4:
                Store store = storeMapper.selectByPrimaryKey(log.getRelateId());
            case 5:
                Item item = itemMapper.selectByPrimaryKey(log.getRelateId());
            case 6:
                Pool pool = poolMapper.selectByPrimaryKey(log.getRelateId());
            case 7:
                PoolCycle poolCycle = poolCycleMapper.selectByPrimaryKey(log.getRelateId());
            case 8:
                Task task = taskMapper.selectByPrimaryKey(log.getRelateId());
            case 9:
                Plan plan = planMapper.selectByPrimaryKey(log.getRelateId());
            case 10:
                PoolPlan poolPlan = poolPlanMapper.selectByPrimaryKey(log.getRelateId());
            case 11:
                PoolTask poolTask = poolTaskMapper.selectByPrimaryKey(log.getRelateId());
            case 12:
                DailyTask dailyTask = dailyTaskMapper.selectByPrimaryKey(log.getRelateId());
            case 13:
                Job job = jobMapper.selectByPrimaryKey(log.getRelateId());
            case 14:
                Alert alert = alertMapper.selectByPrimaryKey(log.getRelateId());
            case 15:
                Comment comment = commentMapper.selectByPrimaryKey(log.getRelateId());
            case 16:
                Fund fund = fundMapper.selectByPrimaryKey(log.getRelateId());
            default:
                return false;
        }
    }
}
