package com.bjtu.afms.service;

import com.bjtu.afms.mapper.LogMapper;
import com.bjtu.afms.model.Log;
import com.bjtu.afms.model.LogExample;
import com.bjtu.afms.web.param.query.LogQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    public List<Log> selectLogList(LogQueryParam param) {
        LogExample example = new LogExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        LogExample.Criteria criteria = example.createCriteria();
        if (param.getType() != null) {
            criteria.andTypeEqualTo(param.getType());
        }
        if (param.getRelateId() != null) {
            criteria.andRelateIdEqualTo(param.getRelateId());
        }
        if (param.getUserId() != null) {
            criteria.andUserIdEqualTo(param.getUserId());
        }
        if (param.getOperationId() != null) {
            criteria.andOperationIdEqualTo(param.getOperationId());
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
        return logMapper.selectByExample(example);
    }
}
