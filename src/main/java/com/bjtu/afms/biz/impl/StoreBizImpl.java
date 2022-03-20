package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.StoreBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Store;
import com.bjtu.afms.service.StoreService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.utils.SetUtil;
import com.bjtu.afms.web.param.query.StoreQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

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
        store.setModTime(null);
        store.setModUser(null);
        if (storeService.insertStore(store) == 1) {
            Set<Integer> userIdSet = SetUtil.newHashSet(LoginContext.getUserId(), store.getManager());
            permissionBiz.initResourceOwner(DataType.CLIENT.getId(), store.getId(), userIdSet);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyStoreManager(int id, int manager) {
        Store store = storeService.selectStore(id);
        if (store == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        Store record = new Store();
        record.setId(id);
        record.setManager(manager);
        if (storeService.updateStore(record) == 1) {
            permissionBiz.initResourceOwner(DataType.STORE.getId(), id, manager);
            permissionBiz.deleteResourceOwner(DataType.STORE.getId(), id, store.getManager());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyStoreInfo(Store store) {
        store.setManager(null);
        store.setAddTime(null);
        store.setAddUser(null);
        return storeService.updateStore(store) == 1;
    }

    @Override
    @Transactional
    public boolean deleteStore(int storeId) {
        permissionBiz.deleteResource(DataType.STORE.getId(), storeId);
        return storeService.deleteStore(storeId) == 1;
    }
}
