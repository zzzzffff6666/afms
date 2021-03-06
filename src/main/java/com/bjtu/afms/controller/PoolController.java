package com.bjtu.afms.controller;

import com.bjtu.afms.biz.PoolBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Pool;
import com.bjtu.afms.service.PoolService;
import com.bjtu.afms.web.param.query.PoolQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PoolController {

    @Resource
    private PoolService poolService;

    @Resource
    private PoolBiz poolBiz;

    @GetMapping("/pool/info/{poolId}")
    public Result getPoolInfo(@PathVariable("poolId") int id) {
        Pool pool = poolService.selectPool(id);
        if (pool != null) {
            return Result.ok(pool);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/pool/all", "/pool/all/{page}"})
    public Result getAllPool(PoolQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(poolBiz.getPoolList(param, page));
    }

    @GetMapping({"/pool/list", "/pool/list/{page}"})
    public Result getPoolList(PoolQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(poolBiz.getPoolList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER})
    @PostMapping("/pool/insert")
    public Result addPool(@RequestBody Pool pool) {
        if (poolBiz.insertPool(pool)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.POOL)
    @PostMapping("/pool/info/modify")
    public Result modifyPoolInfo(@RequestBody Pool pool) {
        if (poolBiz.modifyPoolInfo(pool)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.POOL)
    @PostMapping("/pool/newCycle/start")
    public Result startPoolNewCycle(@RequestParam("id") int id, @RequestParam("userId") int userId) {
        if (poolBiz.startPoolNewCycle(id, userId)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.POOL)
    @PostMapping("/pool/delete/{poolId}")
    public Result deletePool(@PathVariable("poolId") int id) {
        if (poolBiz.deletePool(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
