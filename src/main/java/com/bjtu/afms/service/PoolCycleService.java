package com.bjtu.afms.service;

import com.bjtu.afms.mapper.PoolCycleMapper;
import com.bjtu.afms.model.PoolCycle;
import com.bjtu.afms.model.PoolCycleExample;
import com.bjtu.afms.web.param.query.PoolCycleQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    public PoolCycle selectPoolCycle(int poolId, int cycle) {
        PoolCycleExample example = new PoolCycleExample();
        example.createCriteria().andPoolIdEqualTo(poolId).andCycleEqualTo(cycle);
        List<PoolCycle> poolCycleList = poolCycleMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(poolCycleList)) {
            return null;
        } else {
            return poolCycleList.get(0);
        }
    }

    public List<PoolCycle> selectPoolCycleList(PoolCycleQueryParam param) {
        PoolCycleExample example = new PoolCycleExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        PoolCycleExample.Criteria criteria = example.createCriteria();
        if (param.getPoolId() != null) {
            criteria.andPoolIdEqualTo(param.getPoolId());
        }
        if (param.getCycle() != null) {
            criteria.andCycleEqualTo(param.getCycle());
        }
        if (param.getUserId() != null) {
            criteria.andUserIdEqualTo(param.getUserId());
        }
        if (param.getStatus() != null) {
            criteria.andStatusEqualTo(param.getStatus());
        }
        if (param.getStartBegin() != null || param.getStartLast() != null) {
            if (param.getStartBegin() == null) {
                param.setStartBegin(new Date(0L));
            }
            if (param.getStartLast() == null) {
                param.setStartLast(new Date());
            }
            criteria.andStartTimeBetween(param.getStartBegin(), param.getStartLast());
        }
        if (param.getEndBegin() != null || param.getEndLast() != null) {
            if (param.getEndBegin() == null) {
                param.setEndBegin(new Date(0L));
            }
            if (param.getEndLast() == null) {
                param.setEndLast(new Date());
            }
            criteria.andEndTimeBetween(param.getEndBegin(), param.getEndLast());
        }
        return poolCycleMapper.selectByExample(example);
    }
}
