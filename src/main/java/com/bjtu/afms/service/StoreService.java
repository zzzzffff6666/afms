package com.bjtu.afms.service;

import com.bjtu.afms.mapper.StoreMapper;
import com.bjtu.afms.model.Store;
import com.bjtu.afms.model.StoreExample;
import com.bjtu.afms.web.param.query.StoreQueryParam;
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

    public List<Store> selectStoreList(StoreQueryParam param) {
        StoreExample example = new StoreExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        StoreExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getName())) {
            criteria.andNameLike("%" + param.getName() + "%");
        }
        if (param.getManager() != null) {
            criteria.andManagerEqualTo(param.getManager());
        }
        return storeMapper.selectByExample(example);
    }
}
