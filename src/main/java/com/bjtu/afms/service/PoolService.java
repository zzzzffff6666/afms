package com.bjtu.afms.service;

import com.bjtu.afms.mapper.PoolMapper;
import com.bjtu.afms.model.Pool;
import com.bjtu.afms.model.PoolExample;
import com.bjtu.afms.web.param.query.PoolQueryParam;
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

    public List<Pool> selectPoolList(PoolQueryParam param) {
        PoolExample example = new PoolExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        PoolExample.Criteria criteria = example.createCriteria();
        if (param.getPlace() != null) {
            criteria.andPlaceEqualTo(param.getPlace());
        }
        if (param.getType() != null) {
            criteria.andTypeEqualTo(param.getType());
        }
        if (param.getStatus() != null) {
            criteria.andStatusEqualTo(param.getStatus());
        }
        return poolMapper.selectByExample(example);
    }
}
