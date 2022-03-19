package com.bjtu.afms.enums;

import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.utils.ListUtil;

import java.util.Arrays;
import java.util.List;

public enum PlanFinish {
    CREATED(1, "created", "已创建"),
    STARTED(2, "start", "已开始"),
    PUNCTUAL(3, "punctual", "准时完成"),
    ADVANCE(4, "advance", "提前完成"),
    OVERDUE(5, "overdue", "逾期完成"),
    CANCEL(6, "cancel", "取消"),
    ERROR(7, "error", "异常"),
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
        return Arrays.stream(values())
                .filter(planFinish -> planFinish.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static boolean isFinish(int finish) {
        return finish == PUNCTUAL.getId() || finish == ADVANCE.getId()
                || finish == OVERDUE.getId() || finish == CANCEL.getId();
    }

    public static boolean isStart(int finish) {
        return finish == STARTED.getId();
    }

    public static boolean changeCheck(int originFinish, int newFinish) {
        if (originFinish == newFinish) {
            return false;
        }
        PlanFinish finish1 = findPlanFinish(originFinish);
        PlanFinish finish2 = findPlanFinish(newFinish);
        if (finish2 == null) {
            throw new BizException(APIError.UNKNOWN_PLAN_FINISH);
        }
        switch (finish1) {
            case CREATED:
                return finish2 == STARTED || finish2 == CANCEL;
            case STARTED:
            case ERROR:
                return finish2 != CREATED;
            case PUNCTUAL:
            case ADVANCE:
            case OVERDUE:
            case CANCEL:
                return false;
            default:
                throw new BizException(APIError.UNKNOWN_PLAN_FINISH);
        }
    }
}
