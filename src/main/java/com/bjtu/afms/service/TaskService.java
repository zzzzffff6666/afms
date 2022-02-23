package com.bjtu.afms.service;

import com.bjtu.afms.mapper.TaskMapper;
import com.bjtu.afms.model.Task;
import com.bjtu.afms.model.TaskExample;
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

    public List<Task> selectTaskList(Task task, String orderByClause) {
        TaskExample example = new TaskExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        TaskExample.Criteria criteria = example.createCriteria();
        if (task.getType() != null) {
            criteria.andTypeEqualTo(task.getType());
        }
        if (StringUtils.isNotBlank(task.getName())) {
            criteria.andNameLike("%" + task.getName() + "%");
        }
        if (task.getLevel() != null) {
            criteria.andLevelEqualTo(task.getLevel());
        }
        return taskMapper.selectByExample(example);
    }
}
