package com.bjtu.afms.controller;

import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Log;
import com.bjtu.afms.service.LogService;
import com.bjtu.afms.web.param.query.LogQueryParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LogController {

    @Resource
    private LogService logService;

    @Resource
    private LogBiz logBiz;

    @GetMapping("/log/info/{logId}")
    public Result getLogInfo(@PathVariable("logId") int id) {
        Log operationLog = logService.selectLog(id);
        if (operationLog != null) {
            return Result.ok(operationLog);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/my/log/list", "/my/log/list/{page}"})
    public Result getMyLogList(LogQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(logBiz.getLogList(param, page));
    }

    @GetMapping({"/log/list", "/log/list/{page}"})
    public Result getLogList(LogQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(logBiz.getLogList(param, page));
    }
}
