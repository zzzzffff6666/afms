package com.bjtu.afms.service;

import com.bjtu.afms.mapper.PlanMapper;
import com.bjtu.afms.model.Plan;
import com.bjtu.afms.model.PlanExample;
import com.bjtu.afms.web.param.query.PlanQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    public List<Plan> selectPlanList(PlanQueryParam param) {
        PlanExample example = new PlanExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        PlanExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getName())) {
            criteria.andNameLike("%" + param.getName() + "%");
        }
        if (param.getFinish() != null) {
            criteria.andFinishEqualTo(param.getFinish());
        }
        if (param.getApplyBegin() != null || param.getApplyLast() != null) {
            if (param.getApplyBegin() == null) {
                param.setApplyBegin(new Date(0L));
            }
            if (param.getApplyLast() == null) {
                param.setApplyLast(new Date());
            }
            criteria.andApplyTimeBetween(param.getApplyBegin(), param.getApplyLast());
        }
        if (param.getFinishBegin() != null || param.getFinishLast() != null) {
            if (param.getFinishBegin() == null) {
                param.setFinishBegin(new Date(0L));
            }
            if (param.getFinishLast() == null) {
                param.setFinishLast(new Date());
            }
            criteria.andFinishTimeBetween(param.getFinishBegin(), param.getFinishLast());
        }
        return planMapper.selectByExample(example);
    }

    public List<Plan> selectPlanList(PlanExample example) {
        return planMapper.selectByExample(example);
    }
}
