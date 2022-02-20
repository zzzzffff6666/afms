package com.bjtu.afms.exception;

import com.bjtu.afms.http.APIError;

public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int errorCode;
    private String errorMsg;

    public BizException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        errorCode = APIError.FAILED.getCode();
    }

    public BizException(APIError apiError) {
        super(apiError.getMessage());
        this.errorMsg = apiError.getMessage();
        this.errorCode = apiError.getCode();
    }

    public BizException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorMsg = errorMsg;
        this.errorCode = APIError.FAILED.getCode();
    }

    public BizException(APIError apiError, Throwable cause) {
        super(apiError.getMessage(), cause);
        this.errorMsg = apiError.getMessage();
        this.errorCode = apiError.getCode();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
