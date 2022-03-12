package com.bjtu.afms.enums;

import java.util.Arrays;

public enum OperationType {
    // 用户相关 1xx
    INSERT_USER(101, "insert:user", "用户登记"),
    DELETE_USER(102, "delete:user", "删除用户"),
    UPDATE_USER_INFO(103, "update:user:info", "修改用户信息"),
    UPDATE_USER_PHONE(104, "update:user:phone", "修改用户手机号"),
    UPDATE_USER_PASSWORD(105, "update:user:password", "修改用户密码"),
    UPDATE_USER_STATUS(106, "update:user:status", "修改用户状态"),
    SELECT_USER_LIST(107, "select:user:list", "查询用户列表"),
    SELECT_MY_INFO(108, "select:my:info", "查询本人信息"),
    SELECT_USER_INFO(109, "select:user:info", "查询用户信息"),
    ADMIN_SELECT_USER_INFO(110, "admin_select:user:info", "管理员查询用户信息"),
    ADMIN_UPDATE_USER_PHONE(111, "admin_update:user:phone", "管理员修改用户手机号"),

    // 权限相关 2xx
    INSERT_PERMISSION(201, "insert:permission", "授予用户权限"),
    DELETE_PERMISSION(202, "delete:permission", "删除用户权限"),
    SELECT_MY_PERMISSION_LIST(203, "select:my_permission:list", "查询本人权限列表"),
    SELECT_USER_PERMISSION_LIST(204, "select:user_permission:list", "查询用户权限列表"),
    SELECT_PERMISSION_USER_LIST(204, "select:permission_user:list", "查询拥有指定权限的用户列表"),

    // 客户相关 3xx
    INSERT_CLIENT(301, "insert:client", "客户登记"),
    DELETE_CLIENT(302, "delete:client", "删除客户"),
    UPDATE_CLIENT_INFO(303, "update:client:info", "修改客户信息"),
    SELECT_CLIENT_LIST(304, "select:client:list", "查询客户列表"),
    SELECT_CLIENT_INFO(305, "select:client:info", "查询客户信息"),

    // 仓库相关 4xx
    INSERT_STORE(401, "insert:store", "仓库登记"),
    DELETE_STORE(402, "delete:store", "删除仓库"),
    UPDATE_STORE_INFO(403, "update_store:info", "修改仓库信息"),
    UPDATE_STORE_MANAGER(404, "update:store:manager", "修改仓库管理员"),
    SELECT_STORE_LIST(405, "select:store:list", "查询仓库列表"),
    SELECT_STORE_INFO(406, "select:store:info", "查询仓库信息"),

    // 物资相关 5xx
    INSERT_ITEM(501, "insert:item", "物资登记"),
    DELETE_ITEM(502, "delete:item", "删除物资"),
    UPDATE_ITEM_INFO(503, "update:item:info", "修改物资信息"),
    UPDATE_ITEM_STATUS(504, "update:item:status", "修改物资状态"),
    UPDATE_ITEM_STORE(505, "update:item:store", "修改物资所在仓库"),
    SELECT_ITEM_LIST(506, "select:item:list", "查询物资列表"),
    SELECT_ITEM_INFO(507, "select:item:info", "查询物资信息"),

    // 养殖池相关 6xx
    INSERT_POOL(601, "insert:pool", "养殖池登记"),
    DELETE_POOL(602, "delete:pool", "删除养殖池"),
    UPDATE_POOL_INFO(603, "update:pool:info", "修改养殖池信息"),
    UPDATE_POOL_DETAIL(604, "update:pool:detail", "修改养殖池详细信息"),
    SELECT_POOL_LIST(605, "select:pool:list", "查询养殖池列表"),
    SELECT_POOL_INFO(606, "select:pool:info", "查询养殖池信息"),

    // 养殖周期相关 7xx
    INSERT_POOL_CYCLE(701, "insert:pool_cycle", "新建养殖周期"),
    DELETE_POOL_CYCLE(702, "delete:pool_cycle", "删除养殖周期"),
    UPDATE_POOL_CYCLE_USER(703, "update:pool_cycle:user", "修改养殖周期负责人"),
    UPDATE_POOL_CYCLE_STATUS(704, "update:pool_cycle:status", "修改养殖周期状态"),
    UPDATE_POOL_CYCLE_FUND(705, "update:pool_cycle:fund", "修改养殖周期收支信息"),
    SELECT_POOL_CYCLE_LIST(706, "select:pool_cycle:list", "查询养殖周期列表"),
    SELECT_POOL_CYCLE_INFO(707, "select:pool_cycle:info", "查询养殖周期信息"),
    SELECT_MY_POOL_CYCLE_LIST(708, "select:my_pool_cycle:list", "查询本人负责的养殖周期列表"),

    // 任务相关 8xx
    INSERT_TASK(801, "insert:task", "新建任务"),
    DELETE_TASK(802, "delete:task", "删除任务"),
    UPDATE_TASK_INFO(803, "update:task:info", "修改任务信息"),
    UPDATE_TASK_LEVEL(804, "update:task:level", "修改任务优先等级"),
    SELECT_TASK_LIST(805, "select:task:list", "查询任务列表"),
    SELECT_TASK_INFO(806, "select:task:info", "查询任务信息"),
    SELECT_MY_TASK_LIST(807, "select:my_task:list", "查询本人建立的任务列表"),

    // 计划相关 9xx
    INSERT_PLAN(901, "insert:plan", "新建计划"),
    DELETE_PLAN(902, "delete:plan", "删除计划"),
    UPDATE_PLAN_INFO(903, "update:plan:info", "修改计划信息"),
    SELECT_PLAN_LIST(904, "select:plan:list", "查询计划列表"),
    SELECT_PLAN_INFO(905, "select:plan:info", "查询计划信息"),
    SELECT_MY_PLAN_LIST(906, "select:my_plan:list", "查询本人建立的计划列表"),

    // 养殖计划相关 10xx
    INSERT_POOL_PLAN(1001, "insert:pool_plan", "应用计划到养殖池"),
    DELETE_POOL_PLAN(1002, "delete:pool_plan", "删除养殖计划"),
    UPDATE_POOL_PLAN_ACT_TIME(1003, "update:pool_plan:act_time", "修改养殖计划实际时间"),
    UPDATE_POOL_PLAN_FINISH(1004, "update:pool_plan:finish", "修改养殖计划完成情况"),
    IMPORT_POOL_PLAN(1005, "import:pool_plan", "导入养殖计划"),
    SELECT_POOL_PLAN_LIST(1006, "select:pool_plan:list", "查询养殖计划列表"),
    SELECT_POOL_PLAN_INFO(1007, "select:pool_plan:info", "查询养殖计划信息"),
    SELECT_MY_POOL_PLAN_LIST(1008, "select:my_pool_plan:list", "查询本人建立的养殖计划列表"),

    // 养殖任务相关 11xx
    INSERT_POOL_TASK(1101, "insert:pool_task", "新建养殖任务"),
    DELETE_POOL_TASK(1102, "delete:pool_task", "删除养殖任务"),
    UPDATE_POOL_TASK_USER(1103, "update:pool_task:user", "修改养殖任务负责人"),
    UPDATE_POOL_TASK_STATUS(1104, "update:pool_task:status", "修改养殖任务状态"),
    UPDATE_POOL_TASK_PRE_TIME(1105, "update:pool_task:pre_time", "修改养殖任务预期时间"),
    BATCH_INSERT_POOL_TASK(1106, "batch_insert:pool_task", "批量新建养殖任务"),
    BATCH_DELETE_POOL_TASK(1107, "batch_delete:pool_task", "批量删除养殖任务"),
    SELECT_POOL_TASK_LIST(1108, "select:pool_task:list", "查询养殖池任务列表"),
    SELECT_POOL_TASK_INFO(1109, "select:pool_task:info", "查询养殖池任务信息"),
    SELECT_MY_POOL_TASK_LIST(1110, "select:my_pool_task:list", "查询本人的养殖池任务"),

    // 日常任务相关 12xx
    INSERT_DAILY_TASK(1201, "insert:daily_task", "新建日常任务"),
    DELETE_DAILY_TASK(1202, "delete:daily_task", "删除日常任务"),
    UPDATE_DAILY_TASK_USER(1203, "update:daily_task:user", "修改日常任务负责人"),
    UPDATE_DAILY_TASK_STATUS(1204, "update:daily_task:status", "修改日常任务状态"),
    UPDATE_DAILY_TASK_PRE_TIME(1205, "update:daily_task:pre_time", "修改日常任务预期时间"),
    BATCH_INSERT_DAILY_TASK(1206, "batch_insert:daily_task", "批量新建日常任务"),
    BATCH_DELETE_DAILY_TASK(1207, "batch_delete:daily_task", "批量删除日常任务"),
    SELECT_DAILY_TASK_LIST(1208, "select:pool_task:list", "查询日常任务列表"),
    SELECT_DAILY_TASK_INFO(1209, "select:pool_task:info", "查询日常任务信息"),
    SELECT_MY_DAILY_TASK_LIST(1210, "select:my_pool_task:list", "查询本人的日常任务列表"),

    // 工作相关 13xx
    INSERT_JOB(1301, "insert:job", "新建工作"),
    DELETE_JOB(1302, "delete:job", "删除工作"),
    UPDATE_JOB_DEADLINE(1303, "update:job:deadline", "修改工作截止时间"),
    UPDATE_JOB_STATUS(1304, "update:job:status", "修改工作状态"),
    UPDATE_JOB_USER(1305, "update:job:status", "修改工作执行人"),
    BATCH_INSERT_JOB(1306, "batch_insert:job", "批量新建工作"),
    BATCH_DELETE_JOB(1307, "batch_delete:job", "批量删除工作"),
    SELECT_JOB_LIST(1308, "select:job:list", "查询工作列表"),
    SELECT_JOB_INFO(1309, "select:job:info", "查询工作信息"),
    SELECT_MY_JOB_LIST(1310, "select:my_job:list", "查询本人的工作列表"),

    // 告警相关 14xx
    INSERT_ALERT(1401, "insert:alert", "新建告警"),
    DELETE_ALERT(1402, "delete:alert", "删除告警"),
    UPDATE_ALERT_INFO(1403, "update:alert:info", "修改告警信息"),
    UPDATE_ALERT_USER(1404, "update:alert:user", "修改告警负责人"),
    UPDATE_ALERT_LEVEL(1405, "update:alert:level", "修改告警等级"),
    UPDATE_ALERT_STATUS(1406, "update:alert:status", "修改告警状态"),
    SELECT_ALERT_LIST(1407, "select:alert:list", "查询告警列表"),
    SELECT_ALERT_INFO(1408, "select:alert:info", "查询告警信息"),
    SELECT_MY_ALERT_LIST(1409, "select:my_alert:list", "查询本人的告警列表"),

    // 评论相关 15xx
    INSERT_COMMENT(1501, "insert:comment", "新建评论"),
    DELETE_COMMENT(1502, "delete:comment", "删除评论"),
    UPDATE_COMMENT(1503, "update:comment", "修改评论"),
    SELECT_COMMENT_LIST(1504, "select:comment:list", "查询评论列表"),
    SELECT_COMMENT_INFO(1505, "select:comment:info", "查询评论信息"),
    SELECT_MY_COMMENT_LIST(1506, "select:my_comment:list", "查询本人的评论列表"),

    // 收支相关 16xx
    INSERT_FUND(1601, "insert:fund", "新建收支记录"),
    DELETE_FUND(1602, "delete:fund", "删除收支记录"),
    UPDATE_FUND_INFO(1603, "update:fund:info", "修改收支记录信息"),
    SELECT_FUND_LIST(1604, "select:fund:list", "查询收支列表"),
    SELECT_FUND_INFO(1605, "select:fund:info", "查询收支信息"),
    SELECT_MY_FUND_LIST(1606, "select:my_fund:info", "查询本人建立的收支列表"),

    // 验证码相关 17xx
    INSERT_VERIFY(1701, "insert:verify", "生成验证码"),
    UPDATE_VERIFY(1702, "update:verify", "修改验证码"),

    // 日志相关 18xx
    INSERT_LOG(1801, "insert:log", "添加日志"),
    ROLLBACK_LOG(1802, "rollback:log", "从日志中回滚操作"),
    SELECT_LOG_LIST(1803, "select:log:list", "查询日志列表"),
    SELECT_LOG_INFO(1804, "select:log:info", "查询日志信息")
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
