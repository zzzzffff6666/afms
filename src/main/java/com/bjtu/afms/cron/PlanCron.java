package com.bjtu.afms.cron;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.enums.PlanFinish;
import com.bjtu.afms.enums.TaskStatus;
import com.bjtu.afms.mapper.PoolTaskMapper;
import com.bjtu.afms.model.*;
import com.bjtu.afms.service.*;
import com.bjtu.afms.utils.DateUtil;
import com.bjtu.afms.web.pojo.PlanTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PlanCron {

    @Resource
    private PlanService planService;

    @Resource
    private PoolService poolService;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    // 发布计划中的循环任务
    @Scheduled(cron = "0 0 5 * * ?")
    public void planTaskRelease() {
        Date now = new Date();
        log.info("start cron job: planTaskRelease at time: {}", now.toString());
        try {
            PlanExample example = new PlanExample();
            example.createCriteria().andFinishEqualTo(PlanFinish.APPLIED.getId());
            List<Plan> planList = planService.selectPlanList(example);
            SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            PoolTaskMapper poolTaskMapper = sqlSession.getMapper(PoolTaskMapper.class);
            Map<Integer, Integer> poolCycleMap = new HashMap<>();
            int count = 0;
            for (Plan plan : planList) {
                List<PlanTask> taskList = JSON.parseArray(plan.getTaskList(), PlanTask.class);
                for (PlanTask planTask : taskList) {
                    if (planTask.getApplyInterval() <= 0) {
                        continue;
                    }
                    int sum = planTask.getTaskDuration() + planTask.getApplyInterval();
                    int dayDiffer = DateUtil.getDayDiffer(now, planTask.getStartTime());
                    if (dayDiffer <= 0 || dayDiffer % sum != 0) {
                        continue;
                    }
                    for (int poolId : planTask.getPoolIdList()) {
                        PoolTask poolTask = new PoolTask();
                        poolTask.setPlanId(plan.getId());
                        poolTask.setPoolId(poolId);
                        if (!poolCycleMap.containsKey(poolId)) {
                            Pool pool = poolService.selectPool(poolId);
                            if (pool == null) {
                                continue;
                            }
                            poolCycleMap.put(poolId, pool.getCurrentCycle());
                        }
                        poolTask.setCycle(poolCycleMap.get(poolId) + 1);
                        poolTask.setUserId(planTask.getUserId());
                        poolTask.setTaskId(planTask.getTaskId());
                        poolTask.setStatus(TaskStatus.CREATED.getId());
                        poolTask.setStartPre(now);
                        poolTask.setEndPre(DateUtil.plusDays(planTask.getTaskDuration(), now));
                        poolTaskMapper.insertSelective(poolTask);
                        count++;
                        if (count % 300 == 0) {
                            sqlSession.commit();
                            sqlSession.clearCache();
                        }
                    }
                }
            }
            sqlSession.commit();
            sqlSession.commit();
            sqlSession.close();
            log.info("finish cron job: planTaskRelease at time: {}", new Date().toString());
        } catch (Exception e) {
            log.error("execute cron job: planTaskRelease meet exception: {}", e.getMessage());
        }
    }
}
