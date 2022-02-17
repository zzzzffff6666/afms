package com.bjtu.afms.enums;

public enum OperationType {
    ;
    private final int id;
    private final String operation;
    private final String comment;

    OperationType(int id, String comment, String operation) {
        this.id = id;
        this.operation = operation;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public String getOperation() {
        return operation;
    }

    public String getComment() {
        return comment;
    }
}
