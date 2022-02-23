package com.bjtu.afms.service;

import com.bjtu.afms.mapper.FundMapper;
import com.bjtu.afms.model.Fund;
import com.bjtu.afms.model.FundExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FundService {

    @Resource
    private FundMapper fundMapper;

    public int insertFund(Fund fund) {
        return fundMapper.insertSelective(fund);
    }

    public int deleteFund(int fundId) {
        return fundMapper.deleteByPrimaryKey(fundId);
    }

    public int updateFund(Fund fund) {
        return fundMapper.updateByPrimaryKeySelective(fund);
    }

    public Fund selectFund(int fundId) {
        return fundMapper.selectByPrimaryKey(fundId);
    }

    public List<Fund> selectFundList(Fund fund, String orderByClause) {
        return selectFundList(fund, null, orderByClause);
    }

    public List<Fund> selectFundList(Fund fund, Map<String, Date> timeParam, String orderByClause) {
        FundExample example = new FundExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        FundExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(fund.getName())) {
            criteria.andNameLike("%" + fund.getName() + "%");
        }
        if (fund.getType() != null) {
            criteria.andTypeEqualTo(fund.getType());
        }
        if (timeParam != null) {
            Date start = timeParam.get("start");
            Date end = timeParam.get("end");
            if (end == null) {
                end = new Date();
            }
            criteria.andAddTimeBetween(start, end);
        }
        return fundMapper.selectByExample(example);
    }
}
