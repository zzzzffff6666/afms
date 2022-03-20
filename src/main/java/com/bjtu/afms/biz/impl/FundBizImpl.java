package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.FundBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Fund;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.service.FundService;
import com.bjtu.afms.service.PermissionService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.FundQueryParam;
import com.bjtu.afms.web.param.query.PermissionQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FundBizImpl implements FundBiz {

    @Resource
    private FundService fundService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private PermissionService permissionService;

    @Override
    public Page<Fund> getFundList(FundQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Fund> pageInfo = new PageInfo<>(fundService.selectFundList(param));
        return new Page<>(pageInfo);
    }

    @Override
    public Page<Fund> getMyFundList(Integer page) {
        if (page == null) {
            page = 0;
        }
        PermissionQueryParam param = new PermissionQueryParam();
        param.setType(DataType.FUND.getId());
        param.setAuth(AuthType.OWNER.getId());
        param.setUserId(LoginContext.getUserId());
        param.setOrderBy("add_time desc");
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Permission> pageInfo = new PageInfo<>(permissionService.selectPermissionList(param));
        List<Integer> idList = pageInfo.getList().stream().map(Permission::getRelateId).collect(Collectors.toList());
        List<Fund> fundList = fundService.selectFundByIdList(idList);
        return new Page<>(pageInfo, fundList);
    }

    @Override
    @Transactional
    public boolean insertFund(Fund fund) {
        fund.setModTime(null);
        fund.setModUser(null);
        if (fundService.insertFund(fund) == 1) {
            permissionBiz.initResourceOwner(DataType.FUND.getId(), fund.getId(), LoginContext.getUserId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyFundInfo(Fund fund) {
        fund.setAddTime(null);
        fund.setAddUser(null);
        return fundService.updateFund(fund) == 1;
    }

    @Override
    @Transactional
    public boolean deleteFund(int fundId) {
        permissionBiz.deleteResource(DataType.FUND.getId(), fundId);
        return fundService.deleteFund(fundId) == 1;
    }
}
