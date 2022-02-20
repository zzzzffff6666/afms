package com.bjtu.afms.http;

public enum APIError {
    SUCCESS(0, "成功"),
    FAILED(1, "失败"),
    NOT_LOGIN(2, "未登录"),
    PARAMETER_ERROR(3, "请求参数异常!"),
    NOT_FUND(4, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(5, "服务器内部错误!"),
    SERVER_BUSY(6, "服务器正忙，请稍后再试!"),
    REQUEST_METHOD_SUPPORT_ERROR(7, "请求方法不支持!")
    ;

    private final int code;
    private final String message;

    APIError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
