package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.StoreBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Store;
import com.bjtu.afms.service.StoreService;
import com.bjtu.afms.service.UserService;
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
    private UserService userService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private LogBiz logBiz;

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
        store.setModTime(null);
        store.setModUser(null);
        if (storeService.insertStore(store) == 1) {
            permissionBiz.initResourceOwner(DataType.CLIENT.getId(), store.getId(), LoginContext.getUserId());
            permissionBiz.initResourceOwner(DataType.CLIENT.getId(), store.getId(), store.getManager());
            logBiz.saveLog(DataType.STORE, store.getId(), OperationType.INSERT_STORE,
                    null, JSON.toJSONString(store));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyStoreManager(int id, int manager) {
        Assert.isTrue(userService.exist(manager), APIError.USER_NOT_EXIST);
        Store store = storeService.selectStore(id);
        Assert.notNull(store, APIError.NOT_FOUND);
        Store record = new Store();
        record.setId(id);
        record.setManager(manager);
        if (storeService.updateStore(record) == 1) {
            permissionBiz.initResourceOwner(DataType.STORE.getId(), id, manager);
            permissionBiz.deleteResourceOwner(DataType.STORE.getId(), id, store.getManager());
            logBiz.saveLog(DataType.STORE, id, OperationType.UPDATE_STORE_MANAGER,
                    JSON.toJSONString(store), JSON.toJSONString(record));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyStoreInfo(Store store) {
        Store old = storeService.selectStore(store.getId());
        Assert.notNull(old, APIError.NOT_FOUND);
        store.setManager(null);
        store.setAddTime(null);
        store.setAddUser(null);
        if (storeService.updateStore(store) == 1) {
            logBiz.saveLog(DataType.STORE, store.getId(), OperationType.UPDATE_STORE_INFO,
                    JSON.toJSONString(old), JSON.toJSONString(store));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteStore(int storeId) {
        Store old = storeService.selectStore(storeId);
        Assert.notNull(old, APIError.NOT_FOUND);
        permissionBiz.deleteResource(DataType.STORE.getId(), storeId);
        if (storeService.deleteStore(storeId) == 1) {
            logBiz.saveLog(DataType.STORE, storeId, OperationType.DELETE_STORE,
                    JSON.toJSONString(old), null);
            return true;
        } else {
            return false;
        }
    }
}
