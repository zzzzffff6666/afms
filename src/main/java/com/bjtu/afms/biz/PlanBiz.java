package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Plan;
import com.bjtu.afms.web.param.PlanImportParam;
import com.bjtu.afms.web.param.query.PlanQueryParam;

public interface PlanBiz {

    Page<Plan> getPlanList(PlanQueryParam param, Integer page);

    boolean insertPlan(Plan plan);

    boolean importPlan(PlanImportParam param);

    boolean deletePlan(int planId);
}
