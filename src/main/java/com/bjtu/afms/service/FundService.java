package com.bjtu.afms.service;

import com.bjtu.afms.mapper.FundMapper;
import com.bjtu.afms.model.Fund;
import com.bjtu.afms.model.FundExample;
import com.bjtu.afms.web.param.query.FundQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    public List<Fund> selectFundList(FundQueryParam param) {
        FundExample example = new FundExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        FundExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getName())) {
            criteria.andNameLike("%" + param.getName() + "%");
        }
        if (param.getType() != null) {
            criteria.andTypeEqualTo(param.getType());
        }
        if (param.getAddBegin() != null || param.getAddLast() != null) {
            if (param.getAddBegin() == null) {
                param.setAddBegin(new Date(0L));
            }
            if (param.getAddLast() == null) {
                param.setAddLast(new Date());
            }
            criteria.andAddTimeBetween(param.getAddBegin(), param.getAddLast());
        }
        return fundMapper.selectByExample(example);
    }

    public List<Fund> selectFundByIdList(List<Integer> idList) {
        FundExample example = new FundExample();
        example.createCriteria().andIdIn(idList);
        return fundMapper.selectByExample(example);
    }
}
