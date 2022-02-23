package com.bjtu.afms.service;

import com.bjtu.afms.mapper.PoolTaskMapper;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.model.PoolTaskExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PoolTaskService {

    @Resource
    private PoolTaskMapper poolTaskMapper;

    public int insertPoolTask(PoolTask poolTask) {
        return poolTaskMapper.insertSelective(poolTask);
    }

    public int deletePoolTask(int poolTaskId) {
        return poolTaskMapper.deleteByPrimaryKey(poolTaskId);
    }

    public int updatePoolTask(PoolTask poolTask) {
        return poolTaskMapper.updateByPrimaryKeySelective(poolTask);
    }

    public PoolTask selectPoolTask(int poolTaskId) {
        return poolTaskMapper.selectByPrimaryKey(poolTaskId);
    }

    public List<PoolTask> selectPoolTaskList(PoolTask poolTask, String orderByClause) {
        return selectPoolTaskList(poolTask, null, orderByClause);
    }

    public List<PoolTask> selectPoolTaskList(PoolTask poolTask, Map<String, Date> timeParam, String orderByClause) {
        PoolTaskExample example = new PoolTaskExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        PoolTaskExample.Criteria criteria = example.createCriteria();
        if (poolTask.getPoolId() != null) {
            criteria.andPoolIdEqualTo(poolTask.getPoolId());
        }
        if (poolTask.getCycle() != null) {
            criteria.andCycleEqualTo(poolTask.getCycle());
        }
        if (poolTask.getUserId() != null) {
            criteria.andUserIdEqualTo(poolTask.getUserId());
        }
        if (poolTask.getTaskId() != null) {
            criteria.andTaskIdEqualTo(poolTask.getTaskId());
        }
        if (poolTask.getStatus() != null) {
            criteria.andStatusEqualTo(poolTask.getStatus());
        }
        if (timeParam != null) {
            Date start = timeParam.get("start");
            Date end = timeParam.get("end");
            if (end == null) {
                end = new Date();
            }
            criteria.andEndActBetween(start, end);
        }
        return poolTaskMapper.selectByExample(example);
    }
}
