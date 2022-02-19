package com.bjtu.afms.http;

public interface APIError {
    String BODY_NOT_MATCH = "请求的数据格式不符!";
    String SIGNATURE_NOT_MATCH = "请求的数字签名不匹配!";
    String NOT_FUND = "未找到该资源!";
    String INTERNAL_SERVER_ERROR = "服务器内部错误!";
    String SERVER_BUSY = "服务器正忙，请稍后再试!";
    String REQUEST_METHOD_SUPPORT_ERROR = "当前请求方法不支持";
}
