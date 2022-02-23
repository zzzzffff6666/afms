package com.bjtu.afms.enums;

public enum PoolStatus {
    EMPTY(1, "empty", "空的"),
    FARMING(2, "farming", "养殖中"),
    DISUSED(3, "disused", "弃用")
    ;

    private final int id;
    private final String name;
    private final String comment;

    PoolStatus(int id, String name, String comment) {
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
