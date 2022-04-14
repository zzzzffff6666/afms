package com.bjtu.afms.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FundType {
    EXPENSE(1, "expense", "支出"),
    INCOME(2, "income", "收入")
    ;

    private final int id;
    private final String name;
    private final String comment;

    FundType(int id, String name, String comment) {
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
