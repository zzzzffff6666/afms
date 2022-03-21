package com.bjtu.afms.service;

import com.bjtu.afms.mapper.JobMapper;
import com.bjtu.afms.model.Job;
import com.bjtu.afms.model.JobExample;
import com.bjtu.afms.web.param.query.JobQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    public void deleteJob(int type, int relateId) {
        JobExample example = new JobExample();
        example.createCriteria().andTypeEqualTo(type).andRelateIdEqualTo(relateId);
        jobMapper.deleteByExample(example);
    }

    public int deleteJob(int type, int relateId, List<Integer> userIdList) {
        JobExample example = new JobExample();
        example.createCriteria().andTypeEqualTo(type).andRelateIdEqualTo(relateId).andUserIdIn(userIdList);
        return jobMapper.deleteByExample(example);
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

    public List<Job> selectJobList(JobQueryParam param) {
        JobExample example = new JobExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        JobExample.Criteria criteria = example.createCriteria();
        if (param.getUserId() != null) {
            criteria.andUserIdEqualTo(param.getUserId());
        }
        if (param.getType() != null) {
            criteria.andTypeEqualTo(param.getType());
        }
        if (param.getRelateId() != null) {
            criteria.andRelateIdEqualTo(param.getRelateId());
        }
        if (param.getStatus() != null) {
            criteria.andStatusEqualTo(param.getStatus());
        }
        if (param.getDeadlineBegin() != null || param.getDeadlineLast() != null) {
            if (param.getDeadlineBegin() == null) {
                param.setDeadlineBegin(new Date(0L));
            }
            if (param.getDeadlineLast() == null) {
                param.setDeadlineLast(new Date());
            }
            criteria.andDeadlineBetween(param.getDeadlineBegin(), param.getDeadlineLast());
        }
        return jobMapper.selectByExample(example);
    }

    public List<Job> selectJobList(int type, int relateId, List<Integer> userIdList) {
        JobExample example = new JobExample();
        example.createCriteria().andTypeEqualTo(type).andRelateIdEqualTo(relateId).andUserIdIn(userIdList);
        return jobMapper.selectByExample(example);
    }
}
