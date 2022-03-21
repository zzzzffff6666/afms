package com.bjtu.afms.controller;

import com.bjtu.afms.biz.JobBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.web.param.AssignJobParam;
import com.bjtu.afms.web.param.query.JobQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class JobController {

    @Resource
    private JobBiz jobBiz;

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.JOB)
    @GetMapping("/job/detail/{jobId}")
    public Result getJobDetail(@PathVariable("jobId") int id) {
        return Result.ok(jobBiz.getJobDetail(id));
    }

    @GetMapping({"/my/job/list", "/my/job/list/{page}"})
    public Result getMyJobList(JobQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        param.setUserId(LoginContext.getUserId());
        return Result.ok(jobBiz.getJobList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL})
    @GetMapping({"/job/all", "/job/all/{page}"})
    public Result getAllJob(JobQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(jobBiz.getJobList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL})
    @GetMapping({"/job/list", "/job/list/{page}"})
    public Result getJobList(JobQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(jobBiz.getJobList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, relate = true)
    @PostMapping("/job/insert")
    public Result addJob(@RequestBody AssignJobParam param) {
        jobBiz.insertJob(param);
        return Result.ok();
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.JOB)
    @PostMapping("/job/status/modify")
    public Result modifyJobStatus(@RequestParam("id") int id, @RequestParam("status") int status) {
        if (jobBiz.modifyJobStatus(id, status)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.JOB)
    @PostMapping("/job/user/modify")
    public Result modifyJobUser(@RequestBody AssignJobParam param) {
        jobBiz.modifyJobUser(param);
        return Result.ok();
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.JOB)
    @PostMapping("/job/deadline/modify")
    public Result modifyJobDeadline(@RequestBody AssignJobParam param) {
        if (jobBiz.modifyJobDeadline(param)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL})
    @PostMapping("/job/delete/{jobId}")
    public Result deleteJob(@PathVariable("jobId") int id) {
        if (jobBiz.deleteJob(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, relate = true)
    @PostMapping("/job/task/delete")
    public Result deleteJobOfTask(@RequestBody AssignJobParam param) {
        jobBiz.deleteJobOfTask(param);
        return Result.ok();
    }
}
