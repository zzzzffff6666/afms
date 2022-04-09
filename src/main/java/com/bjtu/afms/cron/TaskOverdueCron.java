package com.bjtu.afms.cron;

import com.bjtu.afms.model.*;
import com.bjtu.afms.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TaskOverdueCron {

    @Resource
    private DailyTaskService dailyTaskService;

    @Resource
    private PoolTaskService poolTaskService;

    @Resource
    private JobService jobService;

    @Resource
    private UserService userService;

    @Resource
    private ToolService toolService;

    // 提醒日常任务未完成
    @Scheduled(cron = "0 0 22 * * ?")
    public void remindDailyTask() {
        Date now = new Date();
        log.info("start cron job: remindDailyTask at time: {}", now.toString());
        try {
            DailyTaskExample example = new DailyTaskExample();
            example.createCriteria()
                    .andStatusLessThan(3)
                    .andEndPreLessThan(now);
            List<DailyTask> dailyTaskList = dailyTaskService.selectDailyTaskList(example);
            Map<Integer, Long> managerMap = dailyTaskList.stream().collect(Collectors.groupingBy(
                    DailyTask::getUserId,
                    Collectors.counting()));
            Map<String, String> param = new HashMap<>();
            param.put("remindName", "您负责的日常任务部分已逾期");
            for (Map.Entry<Integer, Long> entry : managerMap.entrySet()) {
                User user = userService.selectUser(entry.getKey());
                if (user == null) {
                    continue;
                }
                param.put("message", String.format("总共%d个日常任务逾期", entry.getValue()));
                toolService.sendRemind(user.getPhone(), param);
            }
            log.info("finish cron job: remindDailyTask at time: {}", new Date().toString());
        } catch (Exception e) {
            log.error("execute cron job: remindDailyTask meet exception: {}", e.getMessage());
        }
    }

    // 提醒养殖任务未完成
    @Scheduled(cron = "0 20 22 * * ?")
    public void remindPoolTask() {
        Date now = new Date();
        log.info("start cron job: remindPoolTask at time: {}", now.toString());
        try {
            PoolTaskExample example = new PoolTaskExample();
            example.createCriteria()
                    .andStatusLessThan(3)
                    .andEndPreLessThan(now);
            List<PoolTask> poolTaskList = poolTaskService.selectPoolTaskList(example);
            Map<Integer, Long> managerMap = poolTaskList.stream().collect(Collectors.groupingBy(
                    PoolTask::getUserId,
                    Collectors.counting()));
            Map<String, String> param = new HashMap<>();
            param.put("remindName", "您负责的养殖任务部分已逾期");
            for (Map.Entry<Integer, Long> entry : managerMap.entrySet()) {
                User user = userService.selectUser(entry.getKey());
                if (user == null) {
                    continue;
                }
                param.put("message", String.format("总共%d个养殖任务逾期", entry.getValue()));
                toolService.sendRemind(user.getPhone(), param);
            }
            log.info("finish cron job: remindPoolTask at time: {}", new Date().toString());
        } catch (Exception e) {
            log.error("execute cron job: remindPoolTask meet exception: {}", e.getMessage());
        }
    }

    // 提醒个人工作未完成
    @Scheduled(cron = "0 40 22 * * ?")
    public void remindJob() {
        Date now = new Date();
        log.info("start cron job: remindJob at time: {}", now.toString());
        try {
            JobExample example = new JobExample();
            example.createCriteria()
                    .andStatusLessThan(3)
                    .andDeadlineLessThan(now);
            List<Job> jobList = jobService.selectJobList(example);
            Map<Integer, Long> managerMap = jobList.stream().collect(Collectors.groupingBy(
                    Job::getUserId,
                    Collectors.counting()));
            Map<String, String> param = new HashMap<>();
            param.put("remindName", "您负责的工作部分已逾期");
            for (Map.Entry<Integer, Long> entry : managerMap.entrySet()) {
                User user = userService.selectUser(entry.getKey());
                if (user == null) {
                    continue;
                }
                param.put("message", String.format("总共%d个工作逾期", entry.getValue()));
                toolService.sendRemind(user.getPhone(), param);
            }
            log.info("finish cron job: remindJob at time: {}", new Date().toString());
        } catch (Exception e) {
            log.error("execute cron job: remindJob meet exception: {}", e.getMessage());
        }
    }
}
