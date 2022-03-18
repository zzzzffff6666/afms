package com.bjtu.afms.service;

import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.mapper.PoolTaskMapper;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.model.PoolTaskExample;
import com.bjtu.afms.web.param.query.PoolTaskQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<PoolTask> selectPoolTaskList(PoolTaskQueryParam param) {
        PoolTaskExample example = new PoolTaskExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        PoolTaskExample.Criteria criteria = example.createCriteria();
        if (param.getPoolId() != null) {
            criteria.andPoolIdEqualTo(param.getPoolId());
        }
        if (param.getCycle() != null) {
            criteria.andCycleEqualTo(param.getCycle());
        }
        if (param.getUserId() != null) {
            criteria.andUserIdEqualTo(param.getUserId());
        }
        if (param.getTaskId() != null) {
            criteria.andTaskIdEqualTo(param.getTaskId());
        }
        if (param.getStatus() != null) {
            criteria.andStatusEqualTo(param.getStatus());
        }
        if (param.getStartActBegin() != null || param.getStartActLast() != null) {
            if (param.getStartActBegin() == null) {
                param.setStartActBegin(new Date(0L));
            }
            if (param.getStartActLast() == null) {
                param.setStartActLast(new Date());
            }
            criteria.andStartActBetween(param.getStartActBegin(), param.getStartActLast());
        }
        if (param.getEndActBegin() != null || param.getEndActLast() != null) {
            if (param.getEndActBegin() == null) {
                param.setEndActBegin(new Date(0L));
            }
            if (param.getEndActLast() == null) {
                param.setEndActLast(new Date());
            }
            criteria.andEndActBetween(param.getEndActBegin(), param.getEndActLast());
        }
        return poolTaskMapper.selectByExample(example);
    }

    public List<PoolTask> selectUnfinishedTaskList(int taskId) {
        PoolTaskExample example = new PoolTaskExample();
        example.createCriteria().andTaskIdEqualTo(taskId).andStatusIn(
                TaskStatus.getUnfinished().stream().map(TaskStatus::getId).collect(Collectors.toList()));
        return poolTaskMapper.selectByExample(example);
    }

    public List<PoolTask> selectUnfinishedTaskList() {
        PoolTaskExample example = new PoolTaskExample();
        example.createCriteria().andStatusIn(
                TaskStatus.getUnfinished().stream().map(TaskStatus::getId).collect(Collectors.toList()));
        return poolTaskMapper.selectByExample(example);
    }
}
