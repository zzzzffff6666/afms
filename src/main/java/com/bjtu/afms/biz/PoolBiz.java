package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Pool;
import com.bjtu.afms.web.param.query.PoolQueryParam;

public interface PoolBiz {

    Page<Pool> getPoolList(PoolQueryParam param, Integer page);

    boolean insertPool(Pool pool);

    boolean modifyPoolInfo(Pool pool);

    boolean modifyPoolDetail(int id, String detail);

    boolean deletePool(int poolId);
}
