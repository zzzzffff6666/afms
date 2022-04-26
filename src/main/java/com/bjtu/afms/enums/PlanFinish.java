package com.bjtu.afms.enums;

import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PlanFinish {
    ERROR(1, "error", "异常"),
    CREATED(2, "created", "已创建"),
    APPLIED(3, "applied", "已发布"),
    FINISH(4, "finish", "完成"),
    CANCEL(5, "cancel", "取消"),
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

    public static PlanFinish findPlanFinish(int id) {
        for (PlanFinish planFinish : values()) {
            if (planFinish.getId() == id) {
                return planFinish;
            }
        }
        return null;
    }

    public static boolean isFinish(int finish) {
        return finish == FINISH.getId() || finish == CANCEL.getId();
    }

    public static boolean isStart(int finish) {
        return finish == APPLIED.getId();
    }

    public static boolean changeCheck(int originFinish, int newFinish) {
        if (originFinish == newFinish) {
            return false;
        }
        PlanFinish finish1 = findPlanFinish(originFinish);
        Assert.notNull(finish1, APIError.UNKNOWN_PLAN_FINISH);
        PlanFinish finish2 = findPlanFinish(newFinish);
        Assert.notNull(finish2, APIError.UNKNOWN_PLAN_FINISH);
        switch (finish1) {
            case CREATED:
                return finish2 == APPLIED || finish2 == CANCEL;
            case APPLIED:
            case ERROR:
                return finish2 != CREATED;
            case FINISH:
            case CANCEL:
                return false;
            default:
                throw new BizException(APIError.UNKNOWN_PLAN_FINISH);
        }
    }
}
