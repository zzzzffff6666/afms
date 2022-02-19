package com.bjtu.afms.enums;

public enum ItemStatus {
    // 通用状态
    ACTIVE(1, "active", "可使用"),
    DISUSED(2, "disused", "弃用"),

    // 工具状态
    BROKEN(3, "broken", "损坏"),
    UPKEEP(4, "upkeep", "维修中"),
    LENT(5, "lent", "借出"),

    // 消耗品状态
    EXPIRED(6, "expired", "过期"),
    DEPLETED(7, "depleted", "耗尽")
    ;

    private final int id;
    private final String name;
    private final String comment;

    ItemStatus(int id, String name, String comment) {
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
