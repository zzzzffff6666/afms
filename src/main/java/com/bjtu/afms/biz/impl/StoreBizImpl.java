package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.StoreBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Store;
import com.bjtu.afms.service.StoreService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.StoreQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class StoreBizImpl implements StoreBiz {

    @Resource
    private StoreService storeService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Override
    public Page<Store> getStoreList(StoreQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Store> pageInfo = new PageInfo<>(storeService.selectStoreList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional
    public boolean insertStore(Store store) {
        if (storeService.insertStore(store) == 1) {
            permissionBiz.initResourceOwner(DataType.CLIENT.getId(), store.getId(), LoginContext.getUserId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteStore(int storeId) {
        permissionBiz.deleteResource(DataType.STORE.getId(), storeId);
        return storeService.deleteStore(storeId) == 1;
    }
}
