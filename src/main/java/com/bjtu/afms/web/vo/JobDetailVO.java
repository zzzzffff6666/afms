package com.bjtu.afms.web.vo;

import com.bjtu.afms.model.Job;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.model.Task;
import com.bjtu.afms.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class JobDetailVO {
    private Integer id;
    private Integer type;
    private Integer relateId;
    private Date deadline;
    private Integer status;
    private Integer taskId;
    private String taskName;
    private String taskContent;
    private Integer taskLevel;
    private Integer poolId;
    private Integer cycle;
    private List<User> worker;

    public JobDetailVO(Job job, Task task, List<User> userList) {
        id = job.getId();
        type = job.getType();
        relateId = job.getRelateId();
        deadline = job.getDeadline();
        status = job.getStatus();
        taskId = job.getTaskId();
        taskName = task.getName();
        taskContent = task.getContent();
        taskLevel = task.getLevel();
        worker = userList;
    }

    public JobDetailVO(Job job, Task task, PoolTask poolTask, List<User> userList) {
        id = job.getId();
        type = job.getType();
        relateId = job.getRelateId();
        deadline = job.getDeadline();
        status = job.getStatus();
        taskId = job.getTaskId();
        taskName = task.getName();
        taskContent = task.getContent();
        taskLevel = task.getLevel();
        poolId = poolTask.getPoolId();
        cycle = poolTask.getCycle();
        worker = userList;
    }
}
