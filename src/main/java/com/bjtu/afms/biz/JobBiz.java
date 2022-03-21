package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Job;
import com.bjtu.afms.web.param.AssignJobParam;
import com.bjtu.afms.web.param.query.JobQueryParam;
import com.bjtu.afms.web.vo.JobDetailVO;

public interface JobBiz {

    JobDetailVO getJobDetail(int jobId);

    Page<Job> getJobList(JobQueryParam param, Integer page);

    void insertJob(AssignJobParam param);

    boolean modifyJobStatus(int id, int status);

    void modifyJobUser(AssignJobParam param);

    boolean modifyJobDeadline(AssignJobParam param);

    boolean deleteJob(int jobId);

    void deleteJob(AssignJobParam param);

    void deleteJobOfTask(AssignJobParam param);
}
