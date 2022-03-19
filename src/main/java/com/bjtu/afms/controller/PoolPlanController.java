package com.bjtu.afms.controller;

import com.bjtu.afms.biz.PoolPlanBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.PoolPlan;
import com.bjtu.afms.service.PoolPlanService;
import com.bjtu.afms.web.param.query.PoolPlanQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PoolPlanController {

    @Resource
    private PoolPlanService poolPlanService;

    @Resource
    private PoolPlanBiz poolPlanBiz;

    @GetMapping("/poolPlan/info/{poolPlanId}")
    public Result getPoolPlanInfo(@PathVariable("poolPlanId") int id) {
        PoolPlan poolPlan = poolPlanService.selectPoolPlan(id);
        if (poolPlan != null) {
            return Result.ok(poolPlan);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/poolPlan/all", "/poolPlan/all/{page}"})
    public Result getAllPoolPlan(PoolPlanQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(poolPlanBiz.getPoolPlanList(param, page));
    }

    @GetMapping({"/poolPlan/list", "/poolPlan/list/{page}"})
    public Result getPoolPlanList(PoolPlanQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(poolPlanBiz.getPoolPlanList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.POOL_CYCLE)
    @PostMapping("/poolPlan/insert")
    public Result insertPoolPlan(@RequestBody PoolPlan poolPlan) {
        if (poolPlanBiz.insertPoolPlan(poolPlan)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.POOL_PLAN)
    @PostMapping("/poolPlan/time/modify")
    public Result modifyPoolPlanTime(@RequestParam("id") int id, @RequestParam("finish") int finish) {
        if (poolPlanBiz.modifyPoolPlanTime(id, finish)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.POOL_PLAN)
    @PostMapping("/poolPlan/delete/{poolPlanId}")
    public Result deletePoolPlan(@PathVariable("poolPlanId") int id) {
        if (poolPlanBiz.deletePoolPlan(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }
}
