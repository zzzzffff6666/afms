package com.bjtu.afms.enums;

import com.bjtu.afms.utils.ListUtil;

import java.util.List;

public enum DataType {
    USER(1, "user", "用户"),
    PERMISSION(2, "permission", "权限"),
    CLIENT(3, "client", "客户"),
    STORE(4, "store", "仓库"),
    ITEM(5, "item", "物资"),
    POOL(6, "pool", "养殖池"),
    POOL_CYCLE(7, "pool_cycle", "养殖周期"),
    TASK(8, "task", "任务"),
    PLAN(9, "plan", "计划"),
    POOL_PLAN(10, "pool_plan", "养殖计划"),
    POOL_TASK(11, "pool_task", "养殖任务"),
    DAILY_TASK(12, "daily_task", "日常任务"),
    JOB(13, "job", "工作"),
    ALERT(14, "alert", "告警"),
    COMMENT(15, "comment", "评论"),
    FUND(16, "fund", "收支"),
    VERIFY(17, "verify", "验证码"),
    LOG(18, "log", "日志")
    ;

    private final int id;
    private final String name;
    private final String comment;

    DataType(int id, String name, String comment) {
        this.id = id;
        this.name = name;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public static List<DataType> getAllDataType() {
        return ListUtil.newArrayList(USER, PERMISSION, CLIENT, STORE, ITEM, POOL, POOL_CYCLE, TASK,
                PLAN, POOL_PLAN, POOL_TASK, DAILY_TASK, JOB, ALERT, COMMENT, FUND, VERIFY, LOG);
    }
}
