package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.WorkplaceBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Workplace;
import com.bjtu.afms.service.WorkplaceService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.WorkplaceQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class WorkplaceBizImpl implements WorkplaceBiz {

    @Resource
    private WorkplaceService workplaceService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private LogBiz logBiz;

    @Override
    public Page<Workplace> getWorkplaceList(WorkplaceQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Workplace> pageInfo = new PageInfo<>(workplaceService.selectWorkplaceList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional
    public boolean insertWorkplace(Workplace workplace) {
        workplace.setModTime(null);
        workplace.setModUser(null);
        workplace.setPoolNum(0);
        if (workplaceService.insertWorkplace(workplace)) {
            permissionBiz.initResourceOwner(DataType.WORKPLACE.getId(), workplace.getId(), LoginContext.getUserId());
            logBiz.saveLog(DataType.WORKPLACE, workplace.getId(), OperationType.INSERT_WORKPLACE,
                    null, JSON.toJSONString(workplace));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyWorkplaceInfo(Workplace workplace) {
        Workplace old = workplaceService.selectWorkplace(workplace.getId());
        Assert.notNull(old, APIError.NOT_FOUND);
        workplace.setAddTime(null);
        workplace.setAddUser(null);
        if (workplaceService.updateWorkplace(workplace)) {
            logBiz.saveLog(DataType.WORKPLACE, workplace.getId(), OperationType.UPDATE_WORKPLACE_INFO,
                    JSON.toJSONString(old), JSON.toJSONString(workplace));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void modifyWorkplacePoolNum(int id, int addition) {
        Workplace old = workplaceService.selectWorkplace(id);
        Assert.notNull(old, APIError.NOT_FOUND);
        Workplace workplace = new Workplace();
        workplace.setId(id);
        workplace.setPoolNum(old.getPoolNum() + addition);
        workplaceService.updateWorkplace(workplace);
    }

    @Override
    @Transactional
    public boolean deleteWorkplace(int workplaceId) {
        Workplace old = workplaceService.selectWorkplace(workplaceId);
        Assert.notNull(old, APIError.NOT_FOUND);
        if (workplaceService.deleteWorkplace(workplaceId)) {
            permissionBiz.deleteResource(DataType.WORKPLACE.getId(), workplaceId);
            logBiz.saveLog(DataType.WORKPLACE, workplaceId, OperationType.DELETE_WORKPLACE,
                    JSON.toJSONString(old), null);
            return true;
        } else {
            return false;
        }
    }
}
