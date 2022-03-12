package com.bjtu.afms.http;

public interface APIError {
    String SUCCESS = "成功!";

    String FAILED = "失败!";

    String INTERNAL_SERVER_ERROR = "服务器内部错误!";
    String SERVER_BUSY = "服务器正忙，请稍后再试!";
    String REQUEST_METHOD_SUPPORT_ERROR = "请求方法不支持!";
    String PARAMETER_ERROR = "请求参数异常!";
    String NOT_FUND = "未找到该资源!";

    String INSERT_ERROR = "新建资源失败";
    String DELETE_ERROR = "删除资源失败";
    String UPDATE_ERROR = "更新资源失败";
    String SELECT_ERROR = "查找资源失败";

    String NOT_LOGIN = "未登录!";
    String USER_NOT_REGISTER = "用户未注册，请联系管理员注册";
    String USER_NOT_EXIST = "用户不存在";
    String PHONE_EXIST = "手机号已存在";
    String LOGIN_ERROR = "手机号或密码错误";
    String VERIFY_ERROR = "验证码错误";

    String OPERATION_CANNOT_ROLLBACK = "该操作不可回滚";
    String OPERATION_ROLLBACK_FAILED = "操作回滚失败";


}
