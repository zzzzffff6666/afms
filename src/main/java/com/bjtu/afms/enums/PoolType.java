package com.bjtu.afms.enums;

public enum PoolType {
    ROOM_INSULATION(1, "room_insulation", "室内保温"),
    OUTDOOR_INSULATION(2, "outdoor_insulation", "室外保温"),
    OUTDOORS(3, "outdoors", "露天")
    ;

    private final int id;
    private final String name;
    private final String comment;

    PoolType(int id, String name, String comment) {
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
