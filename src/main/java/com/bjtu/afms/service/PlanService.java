package com.bjtu.afms.service;

import com.bjtu.afms.mapper.PlanMapper;
import com.bjtu.afms.model.Plan;
import com.bjtu.afms.model.PlanExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PlanService {

    @Resource
    private PlanMapper planMapper;

    public int insertPlan(Plan plan) {
        return planMapper.insertSelective(plan);
    }

    public int deletePlan(int planId) {
        return planMapper.deleteByPrimaryKey(planId);
    }

    public int updatePlan(Plan plan) {
        return planMapper.updateByPrimaryKeySelective(plan);
    }

    public Plan selectPlan(int planId) {
        return planMapper.selectByPrimaryKey(planId);
    }

    public List<Plan> selectPlanList(Plan plan, String orderByClause) {
        PlanExample example = new PlanExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        PlanExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(plan.getName())) {
            criteria.andNameLike("%" + plan.getName() + "%");
        }
        return planMapper.selectByExample(example);
    }
}
