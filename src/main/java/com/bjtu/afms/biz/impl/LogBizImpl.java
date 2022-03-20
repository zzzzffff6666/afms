package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Log;
import com.bjtu.afms.service.LogService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.LogQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LogBizImpl implements LogBiz {

    @Resource
    private LogService logService;

    @Resource
    private ConfigUtil configUtil;

    @Override
    public Page<Log> getLogList(LogQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Log> pageInfo = new PageInfo<>(logService.selectLogList(param));
        return new Page<>(pageInfo);
    }

    @Override
    public void saveLog(DataType dataType, int relateId, OperationType operationType, String oldValue, String newValue) {
        saveLog(dataType, relateId, LoginContext.getUserId(), operationType, oldValue, newValue);
    }

    @Override
    public void saveLog(DataType dataType, int relateId, int userId, OperationType operationType, String oldValue, String newValue) {
        Log operationLog = new Log();
        operationLog.setType(dataType.getId());
        operationLog.setRelateId(relateId);
        operationLog.setUserId(userId);
        operationLog.setOperationId(operationType.getId());
        operationLog.setOperation(operationType.getOperation());
        operationLog.setOldValue(oldValue);
        operationLog.setNewValue(newValue);
        logService.insertLog(operationLog);
    }
}
