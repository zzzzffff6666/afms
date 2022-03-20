package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Task;
import com.bjtu.afms.web.param.query.TaskQueryParam;

public interface TaskBiz {

    Page<Task> getTaskList(TaskQueryParam param, Integer page);

    boolean insertTask(Task task);

    boolean modifyTaskInfo(Task task);

    boolean deleteTask(int taskId);
}
