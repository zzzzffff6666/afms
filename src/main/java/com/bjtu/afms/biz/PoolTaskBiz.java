package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.web.param.query.PoolTaskQueryParam;

public interface PoolTaskBiz {

    Page<PoolTask> getPoolTaskList(PoolTaskQueryParam param, Integer page);

    boolean insertPoolTask(PoolTask poolTask);

    boolean deletePoolTask(int poolTaskId);
}
