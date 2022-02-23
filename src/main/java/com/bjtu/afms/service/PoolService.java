package com.bjtu.afms.service;

import com.bjtu.afms.mapper.PoolMapper;
import com.bjtu.afms.model.Pool;
import com.bjtu.afms.model.PoolExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PoolService {

    @Resource
    private PoolMapper poolMapper;

    public int insertPool(Pool pool) {
        return poolMapper.insertSelective(pool);
    }

    public int deletePool(int poolId) {
        return poolMapper.deleteByPrimaryKey(poolId);
    }

    public int updatePool(Pool pool) {
        return poolMapper.updateByPrimaryKeySelective(pool);
    }

    public Pool selectPool(int poolId) {
        return poolMapper.selectByPrimaryKey(poolId);
    }

    public List<Pool> selectPoolList(Pool pool, String orderByClause) {
        PoolExample example = new PoolExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        PoolExample.Criteria criteria = example.createCriteria();
        if (pool.getPlace() != null) {
            criteria.andPlaceEqualTo(pool.getPlace());
        }
        if (pool.getType() != null) {
            criteria.andTypeEqualTo(pool.getType());
        }
        if (pool.getStatus() != null) {
            criteria.andStatusEqualTo(pool.getStatus());
        }
        return poolMapper.selectByExample(example);
    }
}
