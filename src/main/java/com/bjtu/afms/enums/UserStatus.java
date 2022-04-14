package com.bjtu.afms.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserStatus {
    VISIT(1, "visit", "游客"),
    WORK(2, "work", "在职"),
    RESIGN(3, "resign", "离职")
    ;

    private final int id;
    private final String name;
    private final String comment;

    UserStatus(int id, String name, String comment) {
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
