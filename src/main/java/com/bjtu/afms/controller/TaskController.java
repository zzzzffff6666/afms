package com.bjtu.afms.controller;

import com.bjtu.afms.biz.TaskBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Task;
import com.bjtu.afms.service.TaskService;
import com.bjtu.afms.web.param.query.TaskQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class TaskController {

    @Resource
    private TaskService taskService;

    @Resource
    private TaskBiz taskBiz;

    @GetMapping("/task/info/{taskId}")
    public Result getTaskInfo(@PathVariable("taskId") int id) {
        Task task = taskService.selectTask(id);
        if (task != null) {
            return Result.ok(task);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/task/all", "/task/all/{page}"})
    public Result getAllTask(TaskQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(taskBiz.getTaskList(param, page));
    }

    @GetMapping({"/task/list", "/task/list/{page}"})
    public Result getTaskList(TaskQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(taskBiz.getTaskList(param, page));
    }

    @PostMapping("/task/insert")
    public Result addTask(@RequestBody Task task) {
        if (taskBiz.insertTask(task)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.TASK)
    @PostMapping("/task/info/modify")
    public Result modifyTaskInfo(@RequestBody Task task) {
        task.setAddTime(null);
        task.setAddUser(null);
        if (taskService.updateTask(task) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.TASK)
    @PostMapping("/admin/task/delete/{taskId}")
    public Result deleteTask(@PathVariable("taskId") int id) {
        if (taskBiz.deleteTask(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
