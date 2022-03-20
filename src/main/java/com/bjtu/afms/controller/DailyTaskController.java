package com.bjtu.afms.controller;

import com.bjtu.afms.biz.DailyTaskBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.DailyTask;
import com.bjtu.afms.service.DailyTaskService;
import com.bjtu.afms.web.param.BatchInsertDailyTaskParam;
import com.bjtu.afms.web.param.query.DailyTaskQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class DailyTaskController {

    @Resource
    private DailyTaskService dailyTaskService;

    @Resource
    private DailyTaskBiz dailyTaskBiz;

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.DAILY_TASK)
    @GetMapping("/dailyTask/info/{dailyTaskId}")
    public Result getDailyTaskInfo(@PathVariable("dailyTaskId") int id) {
        DailyTask dailyTask = dailyTaskService.selectDailyTask(id);
        if (dailyTask != null) {
            return Result.ok(dailyTask);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/my/dailyTask/list", "/my/dailyTask/list/{page}"})
    public Result getMyDailyTaskList(DailyTaskQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        param.setUserId(LoginContext.getUserId());
        return Result.ok(dailyTaskBiz.getDailyTaskList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.DAILY_TASK)
    @GetMapping({"/dailyTask/all", "/dailyTask/all/{page}"})
    public Result getAllDailyTask(DailyTaskQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(dailyTaskBiz.getDailyTaskList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.DAILY_TASK)
    @GetMapping({"/dailyTask/list", "/dailyTask/list/{page}"})
    public Result getDailyTaskList(DailyTaskQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(dailyTaskBiz.getDailyTaskList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL})
    @PostMapping("/dailyTask/insert")
    public Result addDailyTask(@RequestBody DailyTask dailyTask) {
        if (dailyTaskBiz.insertDailyTask(dailyTask)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL})
    @PostMapping("/dailyTask/batchInsert")
    public Result batchAddDailyTask(@RequestBody BatchInsertDailyTaskParam param) {
        dailyTaskBiz.batchInsertDailyTask(param);
        return Result.ok();
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.DAILY_TASK)
    @PostMapping("/dailyTask/status/modify")
    public Result modifyDailyTaskStatus(@RequestParam("id") int id, @RequestParam("status") int satus) {
        if (dailyTaskBiz.modifyDailyTaskStatus(id, satus)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.DAILY_TASK)
    @PostMapping("/dailyTask/user/modify")
    public Result modifyDailyTaskUser(@RequestParam("id") int id, @RequestParam("userId") int userId) {
        if (dailyTaskBiz.modifyDailyTaskUser(id, userId)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.DAILY_TASK)
    @PostMapping("/dailyTask/delete/{dailyTaskId}")
    public Result deleteDailyTask(@PathVariable("dailyTaskId") int id) {
        if (dailyTaskBiz.deleteDailyTask(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
