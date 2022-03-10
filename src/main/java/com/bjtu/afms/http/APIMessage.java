package com.bjtu.afms.http;

public interface APIMessage {
    String SUCCESS = "成功!";
    String FAILED = "失败!";
    String NOT_LOGIN = "未登录!";
    String PARAMETER_ERROR = "请求参数异常!";
    String NOT_FUND = "未找到该资源!";
    String RESOURCE_INVALID = "资源已失效";
    String INTERNAL_SERVER_ERROR = "服务器内部错误!";
    String SERVER_BUSY = "服务器正忙，请稍后再试!";
    String REQUEST_METHOD_SUPPORT_ERROR = "请求方法不支持!";
    String USER_NOT_REGISTER = "用户未注册，请联系管理员注册";
}
