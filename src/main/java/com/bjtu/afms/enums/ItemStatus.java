package com.bjtu.afms.enums;

import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ItemStatus {
    // 通用状态
    ACTIVE(1, "active", "可使用"),
    DISUSED(2, "disused", "弃用"),

    // 工具状态
    BROKEN(3, "broken", "损坏"),
    UPKEEP(4, "upkeep", "维修中"),
    LENT(5, "lent", "借出"),

    // 消耗品状态
    EXPIRED(6, "expired", "过期"),
    DEPLETED(7, "depleted", "耗尽")
    ;

    private final int id;
    private final String name;
    private final String comment;

    ItemStatus(int id, String name, String comment) {
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

    public static ItemStatus findItemStatus(int id) {
        for (ItemStatus itemStatus : values()) {
            if (itemStatus.getId() == id) {
                return itemStatus;
            }
        }
        return null;
    }

    public static ItemStatus findItemStatus(String name) {
        for (ItemStatus itemStatus : values()) {
            if (itemStatus.getName().equals(name)) {
                return itemStatus;
            }
        }
        return null;
    }

    public static boolean changeCheck(int status1, int status2) {
        ItemStatus originStatus = findItemStatus(status1);
        Assert.notNull(originStatus, APIError.UNKNOWN_ITEM_STATUS);
        ItemStatus newStatus = findItemStatus(status2);
        Assert.notNull(newStatus, APIError.UNKNOWN_ITEM_STATUS);
        return changeCheck(originStatus, newStatus);
    }

    public static boolean changeCheck(int status1, ItemStatus newStatus) {
        ItemStatus originStatus = findItemStatus(status1);
        Assert.notNull(originStatus, APIError.UNKNOWN_ITEM_STATUS);
        if (status1 == newStatus.getId()) {
            return false;
        }
        return changeCheck(originStatus, newStatus);
    }

    public static boolean changeCheck(ItemStatus originStatus, ItemStatus newStatus) {
        switch (originStatus) {
            case ACTIVE:
                return true;
            case DISUSED:
            case UPKEEP:
                return newStatus == ACTIVE;
            case BROKEN:
                return newStatus == UPKEEP;
            case LENT:
                return newStatus == ACTIVE || newStatus == BROKEN;
            case EXPIRED:
            case DEPLETED:
                return false;
            default:
                throw new BizException(APIError.UNKNOWN_ITEM_STATUS);
        }
    }
}
