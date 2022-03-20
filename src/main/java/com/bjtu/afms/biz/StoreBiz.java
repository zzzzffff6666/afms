package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Store;
import com.bjtu.afms.web.param.query.StoreQueryParam;

public interface StoreBiz {

    Page<Store> getStoreList(StoreQueryParam param, Integer page);

    boolean insertStore(Store store);

    boolean modifyStoreManager(int id, int manager);

    boolean modifyStoreInfo(Store store);

    boolean deleteStore(int storeId);
}
