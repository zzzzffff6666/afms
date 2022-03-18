package com.bjtu.afms.service;

import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.mapper.DailyTaskMapper;
import com.bjtu.afms.model.DailyTask;
import com.bjtu.afms.model.DailyTaskExample;
import com.bjtu.afms.web.param.query.DailyTaskQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DailyTaskService {

    @Resource
    private DailyTaskMapper dailyTaskMapper;

    public int insertDailyTask(DailyTask dailyTask) {
        return dailyTaskMapper.insertSelective(dailyTask);
    }

    public int deleteDailyTask(int dailyTaskId) {
        return dailyTaskMapper.deleteByPrimaryKey(dailyTaskId);
    }

    public int updateDailyTask(DailyTask dailyTask) {
        return dailyTaskMapper.updateByPrimaryKeySelective(dailyTask);
    }

    public DailyTask selectDailyTask(int dailyTaskId) {
        return dailyTaskMapper.selectByPrimaryKey(dailyTaskId);
    }

    public List<DailyTask> selectDailyTaskList(DailyTaskQueryParam param) {
        DailyTaskExample example = new DailyTaskExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        DailyTaskExample.Criteria criteria = example.createCriteria();
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
        return dailyTaskMapper.selectByExample(example);
    }

    public List<DailyTask> selectUnfinishedTaskList(int taskId) {
        DailyTaskExample example = new DailyTaskExample();
        example.createCriteria().andTaskIdEqualTo(taskId).andStatusIn(
                TaskStatus.getUnfinished().stream().map(TaskStatus::getId).collect(Collectors.toList()));
        return dailyTaskMapper.selectByExample(example);
    }

    public List<DailyTask> selectUnfinishedTaskList() {
        DailyTaskExample example = new DailyTaskExample();
        example.createCriteria().andStatusIn(
                TaskStatus.getUnfinished().stream().map(TaskStatus::getId).collect(Collectors.toList()));
        return dailyTaskMapper.selectByExample(example);
    }
}
