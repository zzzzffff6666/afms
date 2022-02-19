package com.bjtu.afms.enums;

public enum PlanFinish {
    PUNCTUAL(1, "punctual", "准时完成"),
    ADVANCE(2, "advance", "提前完成"),
    OVERDUE(3, "overdue", "逾期完成"),
    UNDONE(4, "undone", "未完成"),
    CANCEL(5, "cancel", "取消"),
    ERROR(6, "error", "异常"),
    ;

    private final int id;
    private final String name;
    private final String comment;

    PlanFinish(int id, String name, String comment) {
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
