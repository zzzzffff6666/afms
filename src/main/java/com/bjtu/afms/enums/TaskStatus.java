package com.bjtu.afms.enums;

public enum TaskStatus {
    CREATED(1, "created", "已创建"),
    HANDLING(2, "handling", "处理中"),
    FINISH(3, "finish", "完成"),
    OVERDUE(4, "overdue", "逾期"),
    CANCEL(5, "cancel", "取消"),
    ERROR(6, "error", "异常")
    ;

    private final int id;
    private final String name;
    private final String comment;

    TaskStatus(int id, String name, String comment) {
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
}
