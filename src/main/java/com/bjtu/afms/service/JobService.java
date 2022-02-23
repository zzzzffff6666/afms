package com.bjtu.afms.service;

import com.bjtu.afms.mapper.JobMapper;
import com.bjtu.afms.model.Job;
import com.bjtu.afms.model.JobExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    @Resource
    private JobMapper jobMapper;

    public int insertJob(Job job) {
        return jobMapper.insertSelective(job);
    }

    public int deleteJob(int jobId) {
        return jobMapper.deleteByPrimaryKey(jobId);
    }

    public int updateJob(Job job) {
        return jobMapper.updateByPrimaryKeySelective(job);
    }

    public int updateJob(Job job, int type, int relateId) {
        JobExample example = new JobExample();
        example.createCriteria().andTypeEqualTo(type).andRelateIdEqualTo(relateId);
        return jobMapper.updateByExampleSelective(job, example);
    }

    public Job selectJob(int jobId) {
        return jobMapper.selectByPrimaryKey(jobId);
    }

    public List<Job> selectJobList(Job job, String orderByClause) {
        return selectJobList(job, null, orderByClause);
    }

    public List<Job> selectJobList(Job job, Map<String, Date> timeParam, String orderByClause) {
        JobExample example = new JobExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        JobExample.Criteria criteria = example.createCriteria();
        if (job.getUserId() != null) {
            criteria.andUserIdEqualTo(job.getUserId());
        }
        if (job.getType() != null) {
            criteria.andTypeEqualTo(job.getType());
        }
        if (job.getRelateId() != null) {
            criteria.andRelateIdEqualTo(job.getRelateId());
        }
        if (job.getStatus() != null) {
            criteria.andStatusEqualTo(job.getStatus());
        }
        if (timeParam != null) {
            Date start = timeParam.get("start");
            Date end = timeParam.get("end");
            if (end == null) {
                end = new Date();
            }
            criteria.andDeadlineBetween(start, end);
        }
        return jobMapper.selectByExample(example);
    }
}
