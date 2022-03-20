package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.web.param.BatchInsertPoolTaskParam;
import com.bjtu.afms.web.param.query.PoolTaskQueryParam;

import java.util.List;

public interface PoolTaskBiz {

    Page<PoolTask> getPoolTaskList(PoolTaskQueryParam param, Integer page);

    boolean insertPoolTask(PoolTask poolTask);

    void batchInsertPoolTask(BatchInsertPoolTaskParam param);

    void batchInsertPoolTask(List<PoolTask> poolTaskList);

    boolean modifyPoolTaskStatus(int id, int status);

    boolean modifyPoolTaskUser(int id, int userId);

    boolean deletePoolTask(int poolTaskId);
}
