package com.bjtu.afms.controller;

import com.bjtu.afms.biz.PoolCycleBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.PoolCycle;
import com.bjtu.afms.service.PoolCycleService;
import com.bjtu.afms.web.param.query.PoolCycleQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PoolCycleController {

    @Resource
    private PoolCycleService poolCycleService;

    @Resource
    private PoolCycleBiz poolCycleBiz;

    @GetMapping("/poolCycle/info/{poolCycleId}")
    public Result getPoolCycleInfo(@PathVariable("poolCycleId") int id) {
        PoolCycle poolCycle = poolCycleService.selectPoolCycle(id);
        if (poolCycle != null) {
            return Result.ok(poolCycle);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping("/poolCycle/info/current/{poolId}")
    public Result getPoolCurrentCycle(@PathVariable("poolId") int id) {
        return Result.ok(poolCycleBiz.getPoolCurrentCycle(id));
    }

    @GetMapping({"/poolCycle/all", "/poolCycle/all/{page}"})
    public Result getAllPoolCycle(PoolCycleQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(poolCycleBiz.getPoolCycleList(param, page));
    }

    @GetMapping({"/poolCycle/list", "/poolCycle/list/{page}"})
    public Result getPoolCycleList(PoolCycleQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(poolCycleBiz.getPoolCycleList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.POOL_CYCLE)
    @PostMapping("/poolCycle/userId/modify")
    public Result modifyPoolCycleUser(@RequestParam("id") int id, @RequestParam("userId") int userId) {
        if (poolCycleBiz.modifyPoolCycleUser(id, userId)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.POOL_CYCLE)
    @PostMapping("/poolCycle/status/modify")
    public Result modifyPoolCycleStatus(@RequestParam("id") int id, @RequestParam("status") int status) {
        if (poolCycleBiz.modifyPoolCycleStatus(id, status)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.POOL_CYCLE)
    @PostMapping("/poolCycle/delete/{poolCycleId}")
    public Result deletePoolCycle(@PathVariable("poolCycleId") int id) {
        if (poolCycleBiz.deletePoolCycle(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
