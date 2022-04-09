package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.AlertBiz;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.*;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Alert;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.service.AlertService;
import com.bjtu.afms.service.PermissionService;
import com.bjtu.afms.service.ToolService;
import com.bjtu.afms.service.UserService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.utils.SetUtil;
import com.bjtu.afms.web.param.query.AlertQueryParam;
import com.bjtu.afms.web.param.query.PermissionQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AlertBizImpl implements AlertBiz {

    @Resource
    private AlertService alertService;

    @Resource
    private UserService userService;

    @Resource
    private ToolService toolService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private PermissionService permissionService;

    @Resource
    private LogBiz logBiz;

    @Override
    public Page<Alert> getAlertList(AlertQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Alert> pageInfo = new PageInfo<>(alertService.selectAlertList(param));
        return new Page<>(pageInfo);
    }

    @Override
    public Page<Alert> getMyAlertList(Integer page) {
        if (page == null) {
            page = 0;
        }
        PermissionQueryParam param = new PermissionQueryParam();
        param.setType(DataType.ALERT.getId());
        param.setAuth(AuthType.OWNER.getId());
        param.setUserId(LoginContext.getUserId());
        param.setOrderBy("add_time desc");
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Permission> pageInfo = new PageInfo<>(permissionService.selectPermissionList(param));
        List<Integer> idList = pageInfo.getList().stream().map(Permission::getRelateId).collect(Collectors.toList());
        List<Alert> alertList = alertService.selectAlertByIdList(idList);
        return new Page<>(pageInfo, alertList);
    }

    @Override
    @Transactional
    public boolean insertAlert(Alert alert) {
        alert.setStatus(TaskStatus.CREATED.getId());
        alert.setStartTime(new Date());
        alert.setModTime(null);
        alert.setModUser(null);
        if (alertService.insertAlert(alert) == 1) {
            Set<Integer> userIdSet = SetUtil.newHashSet(LoginContext.getUserId(), alert.getUserId());
            if (alert.getType() != null && alert.getRelateId() != null) {
                PermissionQueryParam param = new PermissionQueryParam();
                param.setAuth(AuthType.OWNER.getId());
                param.setType(alert.getType());
                param.setRelateId(alert.getRelateId());
                List<Integer> userIdList = permissionService.selectPermissionList(param).stream()
                        .map(Permission::getUserId)
                        .collect(Collectors.toList());
                userIdSet.addAll(userIdList);
            }
            permissionBiz.initResourceOwner(DataType.ALERT.getId(), alert.getId(), userIdSet);
            logBiz.saveLog(DataType.ALERT, alert.getId(), OperationType.INSERT_ALERT,
                    null, JSON.toJSONString(alert));
            Map<String, String> param = new HashMap<>();
            param.put("alertName", alert.getName());
            param.put("alertLevel", UrgentLevel.getInfo(alert.getLevel()));
            toolService.batchSendAlert(new ArrayList<>(userIdSet), param);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyAlertInfo(Alert alert) {
        Alert old = alertService.selectAlert(alert.getId());
        Assert.notNull(old, APIError.NOT_FOUND);
        alert.setStartTime(null);
        alert.setHandleTime(null);
        alert.setEndTime(null);
        alert.setStatus(null);
        alert.setAddTime(null);
        alert.setAddUser(null);
        if (alertService.updateAlert(alert) == 1) {
            logBiz.saveLog(DataType.ALERT, alert.getId(), OperationType.UPDATE_ALERT_INFO,
                    JSON.toJSONString(old), JSON.toJSONString(alert));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyAlertUser(int id, int userId) {
        Assert.isTrue(userService.exist(userId), APIError.USER_NOT_EXIST);
        Alert alert = alertService.selectAlert(id);
        Assert.notNull(alert, APIError.NOT_FOUND);
        Alert record = new Alert();
        record.setId(id);
        record.setUserId(userId);
        if (alertService.updateAlert(record) == 1) {
            permissionBiz.initResourceOwner(DataType.ALERT.getId(), record.getId(), userId);
            permissionBiz.deleteResourceOwner(DataType.ALERT.getId(), record.getId(), alert.getUserId());
            logBiz.saveLog(DataType.ALERT, alert.getId(), OperationType.UPDATE_ALERT_USER,
                    JSON.toJSONString(alert), JSON.toJSONString(record));
            Map<String, String> param = new HashMap<>();
            param.put("alertName", alert.getName());
            param.put("alertLevel", UrgentLevel.getInfo(alert.getLevel()));
            toolService.sendAlert(userId, param);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyAlertStatus(int id, int status) {
        Alert alert = alertService.selectAlert(id);
        Assert.notNull(alert, APIError.NOT_FOUND);
        Assert.isTrue(status != TaskStatus.OVERDUE.getId() && TaskStatus.changeCheck(alert.getStatus(), status),
                APIError.TASK_STATUS_CHANGE_ERROR);
        Alert record = new Alert();
        record.setId(id);
        record.setStatus(status);
        if (TaskStatus.isFinish(status)) {
            record.setEndTime(new Date());
        } else if (TaskStatus.isStart(status)) {
            record.setHandleTime(new Date());
        }
        if (alertService.updateAlert(record) == 1) {
            logBiz.saveLog(DataType.ALERT, alert.getId(), OperationType.UPDATE_ALERT_STATUS,
                    JSON.toJSONString(alert), JSON.toJSONString(record));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteAlert(int alertId) {
        Alert alert = alertService.selectAlert(alertId);
        Assert.notNull(alert, APIError.NOT_FOUND);
        if (alertService.deleteAlert(alertId) == 1) {
            permissionBiz.deleteResource(DataType.ALERT.getId(), alertId);
            logBiz.saveLog(DataType.ALERT, alertId, OperationType.DELETE_ALERT,
                    JSON.toJSONString(alert), null);
            return true;
        } else {
            return false;
        }
    }
}
