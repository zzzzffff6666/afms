package com.bjtu.afms.enums;

import com.bjtu.afms.utils.ListUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DataType {
    USER(1, "user", "用户"),
    PERMISSION(2, "permission", "权限"),
    CLIENT(3, "client", "客户"),
    STORE(4, "store", "仓库"),
    ITEM(5, "item", "物资"),
    WORKPLACE(6, "workplace", "车间"),
    POOL(7, "pool", "养殖池"),
    POOL_CYCLE(8, "pool_cycle", "养殖周期"),
    TASK(9, "task", "任务"),
    PLAN(10, "plan", "计划"),
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
        return ListUtil.newArrayList(values());
    }

    public static DataType findDataType(int id) {
        for (DataType dataType : values()) {
            if (dataType.getId() == id) {
                return dataType;
            }
        }
        return null;
    }

    public static DataType findDataType(String name) {
        for (DataType dataType : values()) {
            if (dataType.getName().equals(name)) {
                return dataType;
            }
        }
        return null;
    }
}
