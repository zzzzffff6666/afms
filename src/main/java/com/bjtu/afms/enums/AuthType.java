package com.bjtu.afms.enums;

import com.bjtu.afms.utils.ListUtil;

import java.util.List;

public enum AuthType {
    NORMAL(1, "normal", "普通员工"),
    OWNER(2, "owner", "资源拥有者"),
    CLIENT_CONTACT(3, "client_contact", "客户联络人"),
    TASK_PRINCIPAL(4, "task_principal", "任务负责人"),
    POOL_MANAGER(5, "pool_manager", "养殖池管理员"),
    STORE_MANAGER(6, "store_manager", "仓库管理员"),
    FUND_REVIEWER(7, "fund_reviewer", "收支审查人"),
    SPECIALIST(8, "specialist", "技术专员"),
    STUFF_MANAGER(9, "stuff_manager", "员工管理"),
    ADMIN(10, "admin", "系统管理员"),
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
        return ListUtil.newArrayList(NORMAL, CLIENT_CONTACT, TASK_PRINCIPAL,
                POOL_MANAGER, STORE_MANAGER, FUND_REVIEWER, SPECIALIST, ADMIN);
    }
}
