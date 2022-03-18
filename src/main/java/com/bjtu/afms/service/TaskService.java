package com.bjtu.afms.service;

import com.bjtu.afms.mapper.TaskMapper;
import com.bjtu.afms.model.Task;
import com.bjtu.afms.model.TaskExample;
import com.bjtu.afms.web.param.query.TaskQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskService {

    @Resource
    private TaskMapper taskMapper;

    public int insertTask(Task task) {
        return taskMapper.insertSelective(task);
    }

    public int deleteTask(int taskId) {
        return taskMapper.deleteByPrimaryKey(taskId);
    }

    public int updateTask(Task task) {
        return taskMapper.updateByPrimaryKeySelective(task);
    }

    public Task selectTask(int taskId) {
        return taskMapper.selectByPrimaryKey(taskId);
    }

    public List<Task> selectTaskList(TaskQueryParam param) {
        TaskExample example = new TaskExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        TaskExample.Criteria criteria = example.createCriteria();
        if (param.getType() != null) {
            criteria.andTypeEqualTo(param.getType());
        }
        if (StringUtils.isNotBlank(param.getName())) {
            criteria.andNameLike("%" + param.getName() + "%");
        }
        if (param.getLevel() != null) {
            criteria.andLevelEqualTo(param.getLevel());
        }
        return taskMapper.selectByExample(example);
    }
}
