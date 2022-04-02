package com.bjtu.afms.http;

public interface APIError {
    String SUCCESS = "成功!";
    String FAILED = "失败!";

    String INTERNAL_SERVER_ERROR = "服务器内部错误";
    String REQUEST_METHOD_SUPPORT_ERROR = "请求方法不支持";
    String PARAMETER_ERROR = "请求参数异常";
    String NOT_FOUND = "未找到资源";

    String INSERT_ERROR = "新建资源失败";
    String DELETE_ERROR = "删除资源失败";
    String UPDATE_ERROR = "更新资源失败";
    String IMPORT_ERROR = "导入资源失败";

    String NOT_LOGIN = "未登录!";
    String USER_NOT_REGISTER = "用户未注册，请联系管理员注册";
    String USER_NOT_EXIST = "用户不存在";
    String PHONE_ALREADY_EXIST = "手机号已存在";
    String PASSWORD_ERROR = "密码错误";
    String PHONE_ERROR = "手机号码格式不正确";
    String LOGIN_ERROR = "手机号或密码错误";
    String VERIFY_ERROR = "验证码错误";

    String OPERATION_CANNOT_ROLLBACK = "该操作不可回滚";
    String OPERATION_ROLLBACK_FAILED = "操作回滚失败";

    String PERMISSION_ALREADY_EXIST = "用户已有该权限";
    String NO_PERMISSION = "无权限";

    String UNKNOWN_DATA_TYPE = "未知的资源类型";
    String UNKNOWN_OPERATION_TYPE = "未知的操作类型";

    String ITEM_TYPE_ERROR = "物品类型错误";
    String UNKNOWN_ITEM_STATUS = "未知的物品状态";
    String ITEM_STATUS_CHANGE_ERROR = "物品状态变更错误";
    String ITEM_CANNOT_USE = "物品不可使用";
    String ITEM_AMOUNT_NOT_ENOUGH = "物品数量不足";

    String TASK_NOW_USED = "该任务仍在被使用";
    String UNKNOWN_TASK_STATUS = "未知的任务状态";
    String TASK_STATUS_CHANGE_ERROR = "任务状态变更错误";
    String CURRENT_CYCLE_NOT_FINISH = "当前养殖周期并未结束";

    String UNKNOWN_PLAN_FINISH = "未知的计划完成状态";
    String PLAN_FINISH_CHANGE_ERROR = "计划完成状态变更错误";
    String PLAN_APPLY_FAILED = "计划应用失败";
}
