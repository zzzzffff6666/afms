package com.bjtu.afms.service;

import com.bjtu.afms.mapper.LogMapper;
import com.bjtu.afms.model.Log;
import com.bjtu.afms.model.LogExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LogService {

    @Resource
    private LogMapper logMapper;

    public int insertLog(Log log) {
        return logMapper.insertSelective(log);
    }

    public Log selectLog(int logId) {
        return logMapper.selectByPrimaryKey(logId);
    }

    public List<Log> selectLogList(Log log, String orderByClause) {
        return selectLogList(log, null, orderByClause);
    }

    public List<Log> selectLogList(Log log, Map<String, Date> timeParam, String orderByClause) {
        LogExample example = new LogExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        LogExample.Criteria criteria = example.createCriteria();
        if (log.getType() != null) {
            criteria.andTypeEqualTo(log.getType());
        }
        if (log.getRelateId() != null) {
            criteria.andRelateIdEqualTo(log.getRelateId());
        }
        if (log.getUserId() != null) {
            criteria.andUserIdEqualTo(log.getUserId());
        }
        if (log.getOperationId() != null) {
            criteria.andOperationIdEqualTo(log.getOperationId());
        }
        if (timeParam != null) {
            Date start = timeParam.get("start");
            Date end = timeParam.get("end");
            if (end == null) {
                end = new Date();
            }
            criteria.andAddTimeBetween(start, end);
        }
        return logMapper.selectByExample(example);
    }
}
