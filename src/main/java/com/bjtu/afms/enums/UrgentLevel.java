package com.bjtu.afms.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UrgentLevel {
    U1(1, "u1", "不紧急"),
    U2(2, "u2", "有点紧急"),
    U3(3, "u3", "紧急"),
    U4(4, "u4", "非常紧急"),
    ACCIDENT(5, "ACCIDENT", "重大事故")
    ;

    private final int id;
    private final String name;
    private final String comment;

    UrgentLevel(int id, String name, String comment) {
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

    public static String getInfo(int id) {
        for (UrgentLevel urgentLevel : values()) {
            if (id == urgentLevel.getId()) {
                return urgentLevel.getComment();
            }
        }
        return null;
    }
}
