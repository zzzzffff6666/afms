package com.bjtu.afms.service;

import com.bjtu.afms.mapper.PoolPlanMapper;
import com.bjtu.afms.model.PoolPlan;
import com.bjtu.afms.model.PoolPlanExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public List<PoolPlan> selectPoolPlanList(PoolPlan poolPlan, String orderByClause) {
        return selectPoolPlanList(poolPlan, null, orderByClause);
    }

    public List<PoolPlan> selectPoolPlanList(PoolPlan poolPlan, Map<String, Date> timeParam, String orderByClause) {
        PoolPlanExample example = new PoolPlanExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        PoolPlanExample.Criteria criteria = example.createCriteria();
        if (poolPlan.getPlanId() != null) {
            criteria.andPlanIdEqualTo(poolPlan.getPlanId());
        }
        if (poolPlan.getPoolId() != null) {
            criteria.andPoolIdEqualTo(poolPlan.getPoolId());
        }
        if (poolPlan.getCycle() != null) {
            criteria.andCycleEqualTo(poolPlan.getCycle());
        }
        if (poolPlan.getFinish() != null) {
            criteria.andFinishEqualTo(poolPlan.getFinish());
        }
        if (timeParam != null) {
            Date start = timeParam.get("start");
            Date end = timeParam.get("end");
            if (end == null) {
                end = new Date();
            }
            criteria.andEndActBetween(start, end);
        }
        return poolPlanMapper.selectByExample(example);
    }
}
