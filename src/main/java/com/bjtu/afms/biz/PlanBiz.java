package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Plan;
import com.bjtu.afms.web.param.ImportPlanParam;
import com.bjtu.afms.web.param.query.PlanQueryParam;

public interface PlanBiz {

    Page<Plan> getPlanList(PlanQueryParam param, Integer page);

    boolean insertPlan(Plan plan);

    boolean importPlan(ImportPlanParam param);

    boolean modifyPlanInfo(Plan plan);

    boolean deletePlan(int planId);
}
