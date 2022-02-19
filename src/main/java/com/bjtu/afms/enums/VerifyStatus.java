package com.bjtu.afms.enums;

public enum VerifyStatus {
    VALID(1, "valid", "有效"),
    VERIFIED(2, "verified", "已验证")
    ;

    private final int id;
    private final String name;
    private final String comment;

    VerifyStatus(int id, String name, String comment) {
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
