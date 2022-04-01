package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.PoolCycle;
import com.bjtu.afms.web.param.query.PoolCycleQueryParam;

public interface PoolCycleBiz {

    PoolCycle getPoolCurrentCycle(int poolId);

    Page<PoolCycle> getPoolCycleList(PoolCycleQueryParam param, Integer page);

    boolean insertPoolCycle(PoolCycle poolCycle);

    boolean modifyPoolCycleUser(int id, int userId);

    boolean modifyPoolCycleStatus(int id, int status);

    boolean deletePoolCycle(int poolCycleId);
}
