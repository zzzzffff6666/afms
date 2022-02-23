package com.bjtu.afms.service;

import com.bjtu.afms.mapper.StoreMapper;
import com.bjtu.afms.model.Store;
import com.bjtu.afms.model.StoreExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StoreService {

    @Resource
    private StoreMapper storeMapper;

    public int insertStore(Store store) {
        return storeMapper.insertSelective(store);
    }

    public int deleteStore(int storeId) {
        return storeMapper.deleteByPrimaryKey(storeId);
    }

    public int updateStore(Store store) {
        return storeMapper.updateByPrimaryKeySelective(store);
    }

    public Store selectStore(int storeId) {
        return storeMapper.selectByPrimaryKey(storeId);
    }

    public List<Store> selectStoreList(Store store, String orderByClause) {
        StoreExample example = new StoreExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        StoreExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(store.getName())) {
            criteria.andNameLike("%" + store.getName() + "%");
        }
        if (store.getManager() != null) {
            criteria.andManagerEqualTo(store.getManager());
        }
        return storeMapper.selectByExample(example);
    }
}
