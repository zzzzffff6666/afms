package com.bjtu.afms.enums;

public enum TaskStatus {
    PREPARE(1, "prepare", "未完成"),
    HANDLE(2, "handle", "处理中"),
    FINISH(3, "finish", "完成"),
    OVERDUE(4, "overdue", "逾期"),
    CANCEL(4, "cancel", "取消"),
    ERROR(5, "error", "异常")
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
