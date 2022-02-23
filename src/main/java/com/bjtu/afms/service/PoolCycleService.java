package com.bjtu.afms.service;

import com.bjtu.afms.mapper.PoolCycleMapper;
import com.bjtu.afms.model.PoolCycle;
import com.bjtu.afms.model.PoolCycleExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PoolCycleService {

    @Resource
    private PoolCycleMapper poolCycleMapper;

    public int insertPoolCycle(PoolCycle poolCycle) {
        return poolCycleMapper.insertSelective(poolCycle);
    }

    public int deletePoolCycle(int poolCycleId) {
        return poolCycleMapper.deleteByPrimaryKey(poolCycleId);
    }

    public int updatePoolCycle(PoolCycle poolCycle) {
        return poolCycleMapper.updateByPrimaryKeySelective(poolCycle);
    }

    public PoolCycle selectPoolCycle(int poolCycleId) {
        return poolCycleMapper.selectByPrimaryKey(poolCycleId);
    }

    public List<PoolCycle> selectPoolCycleList(PoolCycle poolCycle, String orderByClause) {
        return selectPoolCycleList(poolCycle, null, orderByClause);
    }

    public List<PoolCycle> selectPoolCycleList(PoolCycle poolCycle, Map<String, Date> timeParam, String orderByClause) {
        PoolCycleExample example = new PoolCycleExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        PoolCycleExample.Criteria criteria = example.createCriteria();
        if (poolCycle.getPoolId() != null) {
            criteria.andPoolIdEqualTo(poolCycle.getPoolId());
        }
        if (poolCycle.getCycle() != null) {
            criteria.andCycleEqualTo(poolCycle.getCycle());
        }
        if (poolCycle.getUserId() != null) {
            criteria.andUserIdEqualTo(poolCycle.getUserId());
        }
        if (poolCycle.getStatus() != null) {
            criteria.andStatusEqualTo(poolCycle.getStatus());
        }
        if (timeParam != null) {
            Date start = timeParam.get("start");
            Date end = timeParam.get("end");
            if (end == null) {
                end = new Date();
            }
            criteria.andEndTimeBetween(start, end);
        }
        return poolCycleMapper.selectByExample(example);
    }
}
