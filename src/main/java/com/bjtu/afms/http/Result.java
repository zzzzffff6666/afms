package com.bjtu.afms.http;

public class Result {
    private static final int FAILED_CODE = 0;
    private static final int SUCCESS_CODE = 1;

    private int code;
    private String msg;
    private Object data;

    public static Result ok() {
        return ok(null);
    }

    public static Result ok(Object data) {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setMsg(null);
        result.setData(data);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(FAILED_CODE);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
