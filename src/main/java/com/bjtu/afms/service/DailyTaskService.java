package com.bjtu.afms.service;

import com.bjtu.afms.mapper.DailyTaskMapper;
import com.bjtu.afms.model.DailyTask;
import com.bjtu.afms.model.DailyTaskExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public List<DailyTask> selectDailyTaskList(DailyTask dailyTask, String orderByClause) {
        return selectDailyTaskList(dailyTask, null, orderByClause);
    }

    public List<DailyTask> selectDailyTaskList(DailyTask dailyTask, Map<String, Date> timeParam, String orderByClause) {
        DailyTaskExample example = new DailyTaskExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        DailyTaskExample.Criteria criteria = example.createCriteria();
        if (dailyTask.getUserId() != null) {
            criteria.andUserIdEqualTo(dailyTask.getUserId());
        }
        if (dailyTask.getTaskId() != null) {
            criteria.andTaskIdEqualTo(dailyTask.getTaskId());
        }
        if (dailyTask.getStatus() != null) {
            criteria.andStatusEqualTo(dailyTask.getStatus());
        }
        if (timeParam != null) {
            Date start = timeParam.get("start");
            Date end = timeParam.get("end");
            if (end == null) {
                end = new Date();
            }
            criteria.andEndActBetween(start, end);
        }
        return dailyTaskMapper.selectByExample(example);
    }
}
