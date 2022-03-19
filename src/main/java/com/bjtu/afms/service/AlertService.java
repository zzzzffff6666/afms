package com.bjtu.afms.service;

import com.bjtu.afms.mapper.AlertMapper;
import com.bjtu.afms.model.Alert;
import com.bjtu.afms.model.AlertExample;
import com.bjtu.afms.web.param.query.AlertQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AlertService {

    @Resource
    private AlertMapper alertMapper;

    public int insertAlert(Alert alert) {
        return alertMapper.insertSelective(alert);
    }

    public int deleteAlert(int alertId) {
        return alertMapper.deleteByPrimaryKey(alertId);
    }

    public int updateAlert(Alert alert) {
        return alertMapper.updateByPrimaryKeySelective(alert);
    }

    public Alert selectAlert(int alertId) {
        return alertMapper.selectByPrimaryKey(alertId);
    }

    public List<Alert> selectAlertList(AlertQueryParam param) {
        AlertExample example = new AlertExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        AlertExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getName())) {
            criteria.andNameLike("%" + param.getName() + "%");
        }
        if (param.getType() != null) {
            criteria.andTypeEqualTo(param.getType());
        }
        if (param.getRelateId() != null) {
            criteria.andRelateIdEqualTo(param.getRelateId());
        }
        if (param.getUserId() != null) {
            criteria.andUserIdEqualTo(param.getUserId());
        }
        if (param.getLevel() != null) {
            criteria.andLevelEqualTo(param.getLevel());
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
        if (param.getHandleBegin() != null || param.getHandleLast() != null) {
            if (param.getHandleBegin() == null) {
                param.setHandleBegin(new Date(0L));
            }
            if (param.getHandleLast() == null) {
                param.setHandleLast(new Date());
            }
            criteria.andHandleTimeBetween(param.getHandleBegin(), param.getHandleLast());
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
        return alertMapper.selectByExample(example);
    }


}
