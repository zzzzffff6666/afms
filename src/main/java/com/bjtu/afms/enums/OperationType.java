package com.bjtu.afms.enums;

import java.util.Arrays;

public enum OperationType {
    // 用户相关 1xx
    INSERT_USER(101, "insert:user", "用户登记"),
    DELETE_USER(102, "delete:user", "删除用户"),
    UPDATE_USER_STATUS(103, "update:user:status", "修改用户状态"),

    // 权限相关 2xx
    INSERT_PERMISSION(201, "insert:permission", "授予用户权限"),
    DELETE_PERMISSION(202, "delete:permission", "删除用户权限"),
    BATCH_INSERT_PERMISSION(203, "batch_insert:permission", "批量添加用户权限"),
    BATCH_DELETE_PERMISSION(204, "batch_delete:permission", "批量删除用户权限"),

    // 客户相关 3xx
    INSERT_CLIENT(301, "insert:client", "客户登记"),
    DELETE_CLIENT(302, "delete:client", "删除客户"),
    UPDATE_CLIENT_INFO(303, "update:client:info", "修改客户信息"),

    // 仓库相关 4xx
    INSERT_STORE(401, "insert:store", "仓库登记"),
    DELETE_STORE(402, "delete:store", "删除仓库"),
    UPDATE_STORE_INFO(403, "update_store:info", "修改仓库信息"),
    UPDATE_STORE_MANAGER(404, "update:store:manager", "修改仓库管理员"),

    // 物资相关 5xx
    INSERT_ITEM(501, "insert:item", "物资登记"),
    DELETE_ITEM(502, "delete:item", "删除物资"),
    UPDATE_ITEM_INFO(503, "update:item:info", "修改物资信息"),
    UPDATE_ITEM_STATUS(504, "update:item:status", "修改物资状态"),
    TAKE_ITEM(505, "take:item", "取用消耗类物资"),
    RETURN_ITEM(506, "return:item", "归还消耗类物资"),

    // 养殖池相关 6xx
    INSERT_POOL(601, "insert:pool", "养殖池登记"),
    DELETE_POOL(602, "delete:pool", "删除养殖池"),
    UPDATE_POOL_INFO(603, "update:pool:info", "修改养殖池信息"),
    UPDATE_POOL_CURRENT_CYCLE(604, "update:pool:current_cycle", "修改养殖池当前周期"),

    // 养殖周期相关 7xx
    INSERT_POOL_CYCLE(701, "insert:pool_cycle", "新建养殖周期"),
    DELETE_POOL_CYCLE(702, "delete:pool_cycle", "删除养殖周期"),
    UPDATE_POOL_CYCLE_USER(703, "update:pool_cycle:user", "修改养殖周期负责人"),
    UPDATE_POOL_CYCLE_STATUS(704, "update:pool_cycle:status", "修改养殖周期状态"),

    // 任务相关 8xx
    INSERT_TASK(801, "insert:task", "新建任务"),
    DELETE_TASK(802, "delete:task", "删除任务"),
    UPDATE_TASK_INFO(803, "update:task:info", "修改任务信息"),

    // 计划相关 9xx
    INSERT_PLAN(901, "insert:plan", "新建计划"),
    DELETE_PLAN(902, "delete:plan", "删除计划"),
    UPDATE_PLAN_FINISH(903, "update:plan:info", "修改计划信息"),
    APPLY_PLAN(904, "apply:plan", "应用计划"),

    // 养殖任务相关 10xx
    INSERT_POOL_TASK(1001, "insert:pool_task", "新建养殖任务"),
    DELETE_POOL_TASK(1002, "delete:pool_task", "删除养殖任务"),
    UPDATE_POOL_TASK_USER(1003, "update:pool_task:user", "修改养殖任务负责人"),
    UPDATE_POOL_TASK_STATUS(1004, "update:pool_task:status", "修改养殖任务状态"),
    BATCH_INSERT_POOL_TASK(1005, "batch_insert:pool_task", "批量新建养殖任务"),
    BATCH_DELETE_POOL_TASK(1006, "batch_delete:pool_task", "批量删除养殖任务"),

    // 日常任务相关 11xx
    INSERT_DAILY_TASK(1101, "insert:daily_task", "新建日常任务"),
    DELETE_DAILY_TASK(1102, "delete:daily_task", "删除日常任务"),
    UPDATE_DAILY_TASK_USER(1103, "update:daily_task:user", "修改日常任务负责人"),
    UPDATE_DAILY_TASK_STATUS(1104, "update:daily_task:status", "修改日常任务状态"),
    BATCH_INSERT_DAILY_TASK(1105, "batch_insert:daily_task", "批量新建日常任务"),
    BATCH_DELETE_DAILY_TASK(1106, "batch_delete:daily_task", "批量删除日常任务"),

    // 工作相关 12xx
    INSERT_JOB(1201, "insert:job", "新建工作"),
    DELETE_JOB(1202, "delete:job", "删除工作"),
    UPDATE_JOB_STATUS(1203, "update:job:status", "修改工作状态"),
    BATCH_INSERT_JOB(1204, "batch_insert:job", "批量新建工作"),
    BATCH_DELETE_JOB(1205, "batch_delete:job", "批量删除工作"),

    // 告警相关 13xx
    INSERT_ALERT(1301, "insert:alert", "新建告警"),
    DELETE_ALERT(1302, "delete:alert", "删除告警"),
    UPDATE_ALERT_INFO(1303, "update:alert:info", "修改告警信息"),
    UPDATE_ALERT_USER(1304, "update:alert:user", "修改告警负责人"),
    UPDATE_ALERT_STATUS(1305, "update:alert:status", "修改告警状态"),

    // 评论相关 14xx
    INSERT_COMMENT(1401, "insert:comment", "新建评论"),
    DELETE_COMMENT(1402, "delete:comment", "删除评论"),
    UPDATE_COMMENT(1403, "update:comment", "修改评论"),

    // 收支相关 15xx
    INSERT_FUND(1501, "insert:fund", "新建收支记录"),
    DELETE_FUND(1502, "delete:fund", "删除收支记录"),
    UPDATE_FUND_INFO(1503, "update:fund:info", "修改收支记录信息"),

    // 验证码相关 16xx
    INSERT_VERIFY(1601, "insert:verify", "生成验证码"),
    UPDATE_VERIFY(1602, "update:verify", "修改验证码"),

    // 日志相关 17xx
    INSERT_LOG(1701, "insert:log", "添加日志"),
    ROLLBACK_LOG(1702, "rollback:log", "从日志中回滚操作")
    ;

    private final int id;
    private final String operation;
    private final String comment;

    OperationType(int id, String operation, String comment) {
        this.id = id;
        this.operation = operation;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public String getOperation() {
        return operation;
    }

    public String getComment() {
        return comment;
    }

    public static OperationType findOperationType(int id) {
        return Arrays.stream(OperationType.values())
                .filter(operationType -> operationType.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
