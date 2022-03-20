package com.bjtu.afms.controller;

import com.bjtu.afms.biz.PoolTaskBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.service.PoolTaskService;
import com.bjtu.afms.web.param.BatchInsertPoolTaskParam;
import com.bjtu.afms.web.param.query.PoolTaskQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PoolTaskController {

    @Resource
    private PoolTaskService poolTaskService;

    @Resource
    private PoolTaskBiz poolTaskBiz;

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.POOL_TASK)
    @GetMapping("/poolTask/info/{poolTaskId}")
    public Result getPoolTaskInfo(@PathVariable("poolTaskId") int id) {
        PoolTask poolTask = poolTaskService.selectPoolTask(id);
        if (poolTask != null) {
            return Result.ok(poolTask);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/my/poolTask/list", "/my/poolTask/list/{page}"})
    public Result getMyPoolTaskList(PoolTaskQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        param.setUserId(LoginContext.getUserId());
        return Result.ok(poolTaskBiz.getPoolTaskList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER, AuthType.TASK_PRINCIPAL})
    @GetMapping({"/poolTask/all", "/poolTask/all/{page}"})
    public Result getAllPoolTask(PoolTaskQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(poolTaskBiz.getPoolTaskList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER, AuthType.TASK_PRINCIPAL})
    @GetMapping({"/poolTask/list", "/poolTask/list/{page}"})
    public Result getPoolTaskList(PoolTaskQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(poolTaskBiz.getPoolTaskList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.POOL_CYCLE)
    @PostMapping("/poolTask/insert")
    public Result addPoolTask(@RequestBody PoolTask poolTask) {
        if (poolTaskBiz.insertPoolTask(poolTask)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.POOL_CYCLE)
    @PostMapping("/poolTask/batchInsert")
    public Result batchAddPoolTask(@RequestBody BatchInsertPoolTaskParam param) {
        poolTaskBiz.batchInsertPoolTask(param);
        return Result.ok();
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.POOL_TASK)
    @PostMapping("/poolTask/status/modify")
    public Result modifyPoolTaskStatus(@RequestParam("id") int id, @RequestParam("status") int status) {
        if (poolTaskBiz.modifyPoolTaskStatus(id, status)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.POOL_TASK)
    @PostMapping("/poolTask/user/modify")
    public Result modifyPoolTaskUser(@RequestParam("id") int id, @RequestParam("userId") int userId) {
        if (poolTaskBiz.modifyPoolTaskUser(id, userId)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.POOL_TASK)
    @PostMapping("/poolTask/delete/{poolTaskId}")
    public Result deletePoolTask(@PathVariable("poolTaskId") int id) {
        if (poolTaskBiz.deletePoolTask(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }

}
