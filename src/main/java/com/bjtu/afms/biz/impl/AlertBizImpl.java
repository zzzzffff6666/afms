package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.AlertBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Alert;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.service.AlertService;
import com.bjtu.afms.service.PermissionService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.utils.SetUtil;
import com.bjtu.afms.web.param.query.AlertQueryParam;
import com.bjtu.afms.web.param.query.PermissionQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AlertBizImpl implements AlertBiz {

    @Resource
    private AlertService alertService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private PermissionService permissionService;

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
        Alert record = new Alert();
        record.setName(alert.getName());
        record.setType(alert.getType());
        record.setRelateId(alert.getRelateId());
        record.setUserId(alert.getUserId());
        record.setContent(alert.getContent());
        record.setLevel(alert.getLevel());
        record.setStatus(TaskStatus.CREATED.getId());
        record.setStartTime(new Date());
        if (alertService.insertAlert(record) == 1) {
            Set<Integer> userIdSet = SetUtil.newHashSet(LoginContext.getUserId(), record.getUserId());
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
            permissionBiz.initResourceOwner(DataType.ALERT.getId(), record.getId(), userIdSet);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyAlertInfo(Alert alert) {
        alert.setStartTime(null);
        alert.setHandleTime(null);
        alert.setEndTime(null);
        alert.setStatus(null);
        alert.setAddTime(null);
        alert.setAddUser(null);
        return alertService.updateAlert(alert) == 1;
    }

    @Override
    @Transactional
    public boolean modifyAlertUser(int id, int userId) {
        Alert alert = alertService.selectAlert(id);
        if (alert == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        Alert record = new Alert();
        record.setId(id);
        record.setUserId(userId);
        if (alertService.updateAlert(alert) == 1) {
            permissionBiz.initResourceOwner(DataType.ALERT.getId(), record.getId(), userId);
            permissionBiz.deleteResourceOwner(DataType.ALERT.getId(), record.getId(), alert.getUserId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyAlertStatus(int id, int status) {
        Alert alert = alertService.selectAlert(id);
        if (alert == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        if (TaskStatus.changeCheck(alert.getStatus(), status)) {
            Alert record = new Alert();
            record.setId(id);
            record.setStatus(status);
            if (TaskStatus.isFinish(status)) {
                record.setEndTime(new Date());
            } else if (TaskStatus.isStart(status)) {
                record.setHandleTime(new Date());
            }
            return alertService.updateAlert(record) == 1;
        } else {
            throw new BizException(APIError.TASK_STATUS_CHANGE_ERROR);
        }
    }

    @Override
    @Transactional
    public boolean deleteAlert(int alertId) {
        permissionBiz.deleteResource(DataType.ALERT.getId(), alertId);
        return alertService.deleteAlert(alertId) == 1;
    }
}
