package com.bjtu.afms.enums;

import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.utils.ListUtil;

import java.util.Arrays;
import java.util.List;

public enum TaskStatus {
    CREATED(1, "created", "已创建"),
    HANDLING(2, "handling", "处理中"),
    FINISH(3, "finish", "完成"),
    OVERDUE(4, "overdue", "逾期"),
    CANCEL(5, "cancel", "取消"),
    ERROR(6, "error", "异常")
    ;

    private final int id;
    private final String name;
    private final String comment;

    TaskStatus(int id, String name, String comment) {
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

    public static TaskStatus findTaskStatus(int id) {
        return Arrays.stream(values())
                .filter(taskStatus -> taskStatus.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static boolean isFinish(int status) {
        return status == FINISH.getId() || status == OVERDUE.getId() || status == CANCEL.getId();
    }

    public static boolean isStart(int status) {
        return status == HANDLING.getId();
    }

    public static List<TaskStatus> getUnfinished() {
        return ListUtil.newArrayList(CREATED, HANDLING, ERROR);
    }

    public static List<TaskStatus> getFinished() {
        return ListUtil.newArrayList(FINISH, OVERDUE, CANCEL);
    }

    public static boolean changeCheck(int originStatus, int newStatus) {
        if (originStatus == newStatus) {
            return false;
        }
        TaskStatus status1 = findTaskStatus(originStatus);
        TaskStatus status2 = findTaskStatus(newStatus);
        if (status2 == null) {
            throw new BizException(APIError.UNKNOWN_TASK_STATUS);
        }
        switch (status1) {
            case CREATED:
                return status2 == HANDLING || status2 == CANCEL;
            case ERROR:
            case HANDLING:
                return status2 != CREATED;
            case FINISH:
            case OVERDUE:
            case CANCEL:
                return false;
            default:
                throw new BizException(APIError.UNKNOWN_TASK_STATUS);
        }
    }
}
