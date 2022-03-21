package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.JobBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.mapper.JobMapper;
import com.bjtu.afms.mapper.PermissionMapper;
import com.bjtu.afms.model.*;
import com.bjtu.afms.service.*;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.AssignJobParam;
import com.bjtu.afms.web.param.query.JobQueryParam;
import com.bjtu.afms.web.vo.JobDetailVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JobBizImpl implements JobBiz {

    @Resource
    private JobService jobService;

    @Resource
    private PoolTaskService poolTaskService;

    @Resource
    private TaskService taskService;

    @Resource
    private UserService userService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public JobDetailVO getJobDetail(int jobId) {
        Job job = jobService.selectJob(jobId);
        if (job == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        Task task = taskService.selectTask(job.getTaskId());
        if (task == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        JobQueryParam param = new JobQueryParam();
        param.setType(job.getType());
        param.setRelateId(job.getRelateId());
        List<Job> jobList = jobService.selectJobList(param);
        Set<Integer> userIdSet = jobList.stream().map(Job::getUserId).collect(Collectors.toSet());
        List<User> userList = userService.selectUserByIdList(new ArrayList<>(userIdSet));
        if (job.getType() == DataType.POOL_TASK.getId()) {
            PoolTask poolTask = poolTaskService.selectPoolTask(job.getRelateId());
            if (poolTask == null) {
                throw new BizException(APIError.NOT_FOUND);
            }
            return new JobDetailVO(job, task, poolTask, userList);
        } else {
            return new JobDetailVO(job, task, userList);
        }
    }

    @Override
    public Page<Job> getJobList(JobQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Job> pageInfo = new PageInfo<>(jobService.selectJobList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional
    public void insertJob(AssignJobParam param) {
        List<Job> jobList = new ArrayList<>();
        for (int userId : param.getUserSet()) {
            Job job = new Job();
            job.setTaskId(param.getTaskId());
            job.setType(param.getType());
            job.setRelateId(param.getRelateId());
            job.setDeadline(param.getDeadline());
            job.setUserId(userId);
            job.setStatus(TaskStatus.CREATED.getId());
            jobList.add(job);
        }
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        JobMapper jobMapper = sqlSession.getMapper(JobMapper.class);
        jobList.forEach(jobMapper::insertSelective);
        sqlSession.commit();
        sqlSession.clearCache();
        PermissionMapper permissionMapper = sqlSession.getMapper(PermissionMapper.class);
        jobList.forEach(job -> {
            Permission permission1 = new Permission();
            permission1.setUserId(LoginContext.getUserId());
            permission1.setAuth(AuthType.OWNER.getId());
            permission1.setType(DataType.JOB.getId());
            permission1.setRelateId(job.getId());
            permissionMapper.insertSelective(permission1);
            Permission permission2 = new Permission();
            permission2.setUserId(job.getUserId());
            permission2.setAuth(AuthType.OWNER.getId());
            permission2.setType(DataType.JOB.getId());
            permission2.setRelateId(job.getId());
            permissionMapper.insertSelective(permission2);
        });
        sqlSession.commit();
        sqlSession.clearCache();
        sqlSession.close();
    }

    @Override
    @Transactional
    public boolean modifyJobStatus(int id, int status) {
        Job job = jobService.selectJob(id);
        if (job == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        if (TaskStatus.changeCheck(job.getStatus(), status)) {
            Job record = new Job();
            record.setId(id);
            if (new Date().after(job.getDeadline()) && status == TaskStatus.FINISH.getId()) {
                record.setStatus(TaskStatus.OVERDUE.getId());
            } else {
                record.setStatus(status);
            }
            return jobService.updateJob(job) == 1;
        } else {
            throw new BizException(APIError.TASK_STATUS_CHANGE_ERROR);
        }
    }

    @Override
    @Transactional
    public void modifyJobUser(AssignJobParam param) {
        JobQueryParam param1 = new JobQueryParam();
        param1.setType(param.getType());
        param1.setRelateId(param.getRelateId());
        List<Job> jobList = jobService.selectJobList(param1);
        Set<Integer> addSet;
        Set<Integer> removeSet;
        if (!CollectionUtils.isEmpty(jobList)) {
            Set<Integer> userIdSet = jobList.stream().map(Job::getUserId).collect(Collectors.toSet());
            addSet = param.getUserSet().stream().filter(id -> !userIdSet.contains(id)).collect(Collectors.toSet());
            removeSet = userIdSet.stream().filter(id -> !param.getUserSet().contains(id)).collect(Collectors.toSet());
        } else {
            throw new BizException(APIError.NOT_FOUND);
        }
        if (!CollectionUtils.isEmpty(addSet)) {
            AssignJobParam param2 = new AssignJobParam();
            param2.setType(param.getType());
            param2.setRelateId(param.getRelateId());
            param2.setTaskId(jobList.get(0).getTaskId());
            param2.setDeadline(jobList.get(0).getDeadline());
            param2.setUserSet(addSet);
            insertJob(param2);
        }
        if (!CollectionUtils.isEmpty(removeSet)) {
            AssignJobParam param3 = new AssignJobParam();
            param3.setType(param.getType());
            param3.setRelateId(param.getRelateId());
            param3.setUserSet(addSet);
            deleteJob(param3);
        }
    }

    @Override
    @Transactional
    public boolean modifyJobDeadline(AssignJobParam param) {
        Job job = new Job();
        job.setDeadline(param.getDeadline());
        return jobService.updateJob(job, param.getType(), param.getRelateId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteJob(int jobId) {
        permissionBiz.deleteResource(DataType.JOB.getId(), jobId);
        return jobService.deleteJob(jobId) == 1;
    }

    @Override
    @Transactional
    public void deleteJob(AssignJobParam param) {
        List<Job> jobList = jobService.selectJobList(param.getType(), param.getRelateId(), new ArrayList<>(param.getUserSet()));
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        PermissionMapper permissionMapper = sqlSession.getMapper(PermissionMapper.class);
        jobList.forEach(job -> {
            PermissionExample example = new PermissionExample();
            example.createCriteria()
                    .andAuthEqualTo(AuthType.OWNER.getId())
                    .andTypeEqualTo(DataType.JOB.getId())
                    .andRelateIdEqualTo(job.getId());
            permissionMapper.deleteByExample(example);
        });
        JobMapper jobMapper = sqlSession.getMapper(JobMapper.class);
        jobList.forEach(job -> jobMapper.deleteByPrimaryKey(job.getId()));
        sqlSession.commit();
        sqlSession.clearCache();
        sqlSession.close();
    }

    @Override
    @Transactional
    public void deleteJobOfTask(AssignJobParam param) {
        JobQueryParam param1 = new JobQueryParam();
        param1.setType(param.getType());
        param1.setRelateId(param.getRelateId());
        List<Job> jobList = jobService.selectJobList(param1);
        permissionBiz.deleteResource(DataType.JOB.getId(), jobList.stream().map(Job::getId).collect(Collectors.toList()));
        jobService.deleteJob(param.getType(), param.getRelateId());
    }
}
