package com.bjtu.afms.service;

import com.bjtu.afms.mapper.PoolPlanMapper;
import com.bjtu.afms.model.PoolPlan;
import com.bjtu.afms.model.PoolPlanExample;
import com.bjtu.afms.web.param.query.PoolPlanQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PoolPlanService {

    @Resource
    private PoolPlanMapper poolPlanMapper;

    public int insertPoolPlan(PoolPlan poolPlan) {
        return poolPlanMapper.insertSelective(poolPlan);
    }

    public int deletePoolPlan(int poolPlanId) {
        return poolPlanMapper.deleteByPrimaryKey(poolPlanId);
    }

    public int updatePoolPlan(PoolPlan poolPlan) {
        return poolPlanMapper.updateByPrimaryKeySelective(poolPlan);
    }

    public PoolPlan selectPoolPlan(int poolPlanId) {
        return poolPlanMapper.selectByPrimaryKey(poolPlanId);
    }

    public List<PoolPlan> selectPoolPlanList(PoolPlanQueryParam param) {
        PoolPlanExample example = new PoolPlanExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        PoolPlanExample.Criteria criteria = example.createCriteria();
        if (param.getPlanId() != null) {
            criteria.andPlanIdEqualTo(param.getPlanId());
        }
        if (param.getPoolId() != null) {
            criteria.andPoolIdEqualTo(param.getPoolId());
        }
        if (param.getCycle() != null) {
            criteria.andCycleEqualTo(param.getCycle());
        }
        if (param.getFinish() != null) {
            criteria.andFinishEqualTo(param.getFinish());
        }
        if (param.getStartActBegin() != null || param.getStartActLast() != null) {
            if (param.getStartActBegin() == null) {
                param.setStartActBegin(new Date(0L));
            }
            if (param.getStartActLast() == null) {
                param.setStartActLast(new Date());
            }
            criteria.andStartActBetween(param.getStartActBegin(), param.getStartActLast());
        }
        if (param.getEndActBegin() != null || param.getEndActLast() != null) {
            if (param.getEndActBegin() == null) {
                param.setEndActBegin(new Date(0L));
            }
            if (param.getEndActLast() == null) {
                param.setEndActLast(new Date());
            }
            criteria.andEndActBetween(param.getEndActBegin(), param.getEndActLast());
        }
        return poolPlanMapper.selectByExample(example);
    }
}
