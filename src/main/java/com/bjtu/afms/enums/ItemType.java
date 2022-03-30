package com.bjtu.afms.enums;

public enum ItemType {
    TOOL(1, "tool", "工具"),
    FEED(2, "feed", "饲料"),
    MEDICINE(3, "medicine", "药品"),
    CONSUMABLE(4, "consumable", "其他消耗品")
    ;
    private final int id;
    private final String name;
    private final String comment;

    ItemType(int id, String name, String comment) {
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
