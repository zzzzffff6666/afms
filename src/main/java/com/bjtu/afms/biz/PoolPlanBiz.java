package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.PoolPlan;
import com.bjtu.afms.web.param.query.PoolPlanQueryParam;

public interface PoolPlanBiz {

    Page<PoolPlan> getPoolPlanList(PoolPlanQueryParam param, Integer page);

    boolean insertPoolPlan(PoolPlan poolPlan);

    boolean modifyPoolPlanTime(int id, int finish);

    boolean deletePoolPlan(int poolPlanId);
}
