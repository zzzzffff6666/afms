package com.bjtu.afms.service;

import com.bjtu.afms.mapper.AlertMapper;
import com.bjtu.afms.model.Alert;
import com.bjtu.afms.model.AlertExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public List<Alert> selectAlertList(Alert alert, String orderByClause) {
        AlertExample example = new AlertExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        AlertExample.Criteria criteria = example.createCriteria();
        if (alert.getUserId() != null) {
            criteria.andUserIdEqualTo(alert.getUserId());
        }
        if (alert.getType() != null) {
            criteria.andTypeEqualTo(alert.getType());
        }
        if (alert.getName() != null) {
            criteria.andNameLike("%" + alert.getName() + "%");
        }
        if (alert.getLevel() != null) {
            criteria.andLevelEqualTo(alert.getLevel());
        }
        if (alert.getStatus() != null) {
            criteria.andStatusEqualTo(alert.getStatus());
        }
        if (alert.getStartTime() != null) {
            criteria.andStartTimeGreaterThan(alert.getStartTime());
        }
        if (alert.getEndTime() != null) {
            criteria.andEndTimeLessThan(alert.getEndTime());
        }
        return alertMapper.selectByExample(example);
    }


}
