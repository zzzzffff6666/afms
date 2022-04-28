package com.bjtu.afms.controller;

import com.bjtu.afms.biz.AlertBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Alert;
import com.bjtu.afms.service.AlertService;
import com.bjtu.afms.web.param.query.AlertQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class AlertController {

    @Resource
    private AlertService alertService;

    @Resource
    private AlertBiz alertBiz;

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.ALERT)
    @GetMapping("/alert/info/{alertId}")
    public Result getAlertInfo(@PathVariable("alertId") int id) {
        Alert alert = alertService.selectAlert(id);
        if (alert != null) {
            return Result.ok(alert);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/my/alert/list", "/my/alert/list/{page}"})
    public Result getMyAlertList(@PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(alertBiz.getMyAlertList(page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL})
    @GetMapping({"/alert/all", "/alert/all/{page}"})
    public Result getAllAlert(AlertQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(alertBiz.getAlertList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL})
    @GetMapping({"/alert/list", "/alert/list/{page}"})
    public Result getAlertList(AlertQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(alertBiz.getAlertList(param, page));
    }

    @PostMapping("/alert/insert")
    public Result addAlert(@RequestBody Alert alert) {
        if (alertBiz.insertAlert(alert)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.ALERT)
    @PostMapping("/alert/info/modify")
    public Result modifyAlertInfo(@RequestBody Alert alert) {
        if (alertBiz.modifyAlertInfo(alert)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.ALERT)
    @PostMapping("/alert/user/modify")
    public Result modifyAlertUser(@RequestParam("id") int id, @RequestParam("userId") int userId) {
        if (alertBiz.modifyAlertUser(id, userId)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.ALERT)
    @PostMapping("/alert/status/modify")
    public Result modifyAlertStatus(@RequestParam("id") int id, @RequestParam("status") int status) {
        if (alertBiz.modifyAlertStatus(id, status)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.TASK_PRINCIPAL}, owner = true, data = DataType.ALERT)
    @PostMapping("/alert/delete/{alertId}")
    public Result deleteAlert(@PathVariable("alertId") int id) {
        if (alertBiz.deleteAlert(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
