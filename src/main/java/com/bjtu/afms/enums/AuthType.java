package com.bjtu.afms.enums;

import com.bjtu.afms.utils.ListUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AuthType {
    NORMAL(1, "normal", "普通员工"),
    CLIENT_CONTACT(2, "client_contact", "客户联络人"),
    TASK_PRINCIPAL(3, "task_principal", "任务负责人"),
    POOL_MANAGER(4, "pool_manager", "养殖池管理员"),
    STORE_MANAGER(5, "store_manager", "仓库管理员"),
    FUND_REVIEWER(6, "fund_reviewer", "收支审查人"),
    SPECIALIST(7, "specialist", "技术专员"),
    STUFF_MANAGER(8, "stuff_manager", "员工管理"),
    ADMIN(9, "admin", "系统管理员"),
    OWNER(10, "owner", "资源拥有者")
    ;

    private final int id;
    private final String name;
    private final String comment;

    AuthType(int id, String name, String comment) {
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

    public static List<AuthType> getAllPermissionType() {
        return ListUtil.newArrayList(values());
    }
}
