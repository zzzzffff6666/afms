package com.bjtu.afms.controller;

import com.bjtu.afms.biz.PlanBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Plan;
import com.bjtu.afms.service.PlanService;
import com.bjtu.afms.web.param.PlanImportParam;
import com.bjtu.afms.web.param.query.PlanQueryParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PlanController {

    @Resource
    private PlanService planService;

    @Resource
    private PlanBiz planBiz;

    @GetMapping("/plan/info/{planId}")
    public Result getPlanInfo(@PathVariable("planId") int id) {
        Plan plan = planService.selectPlan(id);
        if (plan != null) {
            return Result.ok(plan);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/plan/all", "/plan/all/{page}"})
    public Result getAllPlan(PlanQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(planBiz.getPlanList(param, page));
    }

    @GetMapping({"/plan/list", "/plan/list/{page}"})
    public Result getPlanList(PlanQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(planBiz.getPlanList(param, page));
    }

    @PostMapping("/plan/insert")
    public Result addPlan(@RequestBody Plan plan) {
        if (planBiz.insertPlan(plan)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL})
    @PostMapping("/admin/plan/import")
    public Result importPlan(@RequestBody @Validated PlanImportParam param) {
        if (planBiz.importPlan(param)) {
            return Result.ok();
        } else {
            return Result.error(APIError.IMPORT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.PLAN)
    @PostMapping("/admin/plan/info/modify")
    public Result modifyPlanInfo(@RequestBody Plan plan) {
        plan.setUseNum(null);
        plan.setAddTime(null);
        plan.setAddUser(null);
        if (planService.updatePlan(plan) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.PLAN)
    @PostMapping("/admin/plan/delete/{planId}")
    public Result deletePlan(@PathVariable("planId") int id) {
        if (planBiz.deletePlan(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
