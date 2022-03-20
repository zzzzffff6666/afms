package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.DailyTask;
import com.bjtu.afms.web.param.BatchInsertDailyTaskParam;
import com.bjtu.afms.web.param.query.DailyTaskQueryParam;

import java.util.List;

public interface DailyTaskBiz {

    Page<DailyTask> getDailyTaskList(DailyTaskQueryParam param, Integer page);

    boolean insertDailyTask(DailyTask dailyTask);

    void batchInsertDailyTask(BatchInsertDailyTaskParam param);

    void batchInsertDailyTask(List<DailyTask> dailyTaskList);

    boolean modifyDailyTaskStatus(int id, int status);

    boolean modifyDailyTaskUser(int id, int userId);

    boolean deleteDailyTask(int dailyTaskId);
}
