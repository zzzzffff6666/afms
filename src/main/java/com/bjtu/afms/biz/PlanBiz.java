package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Plan;
import com.bjtu.afms.web.param.query.PlanQueryParam;
import com.bjtu.afms.web.pojo.ErrorInfo;

public interface PlanBiz {

    Page<Plan> getPlanList(PlanQueryParam param, Integer page);

    boolean insertPlan(Plan plan);

    ErrorInfo applyPlan(int planId);

    boolean modifyPlanFinish(int id, int finish);

    boolean deletePlan(int planId);
}
