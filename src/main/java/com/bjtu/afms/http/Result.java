package com.bjtu.afms.http;

public class Result {
    private static final int FAILED_CODE = 0;
    private static final int SUCCESS_CODE = 1;
    private static final int NOT_LOGIN = 2;

    private int code;
    private String msg;
    private Object data;

    public static Result ok() {
        return ok(null);
    }

    public static Result ok(Object data) {
        Result result = new Result();
        result.setCode(APIError.SUCCESS.getCode());
        result.setMsg(APIError.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(APIError.FAILED.getCode());
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static Result error(APIError apiError) {
        Result result = new Result();
        result.setCode(apiError.getCode());
        result.setMsg(apiError.getMessage());
        result.setData(null);
        return result;
    }

    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
