package com.bjtu.afms.config.handler;

import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public Result exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return Result.error(APIError.BODY_NOT_MATCH);
    }

    /**
     * 处理请求方法不支持的异常
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Result exceptionHandler(HttpServletRequest req, HttpRequestMethodNotSupportedException e) {
        log.error("发生请求方法不支持异常！原因是:", e);
        return Result.error(APIError.REQUEST_METHOD_SUPPORT_ERROR);
    }

    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result bizExceptionHandler(HttpServletRequest req, RuntimeException e) {
        log.error("发生业务异常！原因是：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常！原因是:", e);
        return Result.error(APIError.INTERNAL_SERVER_ERROR);
    }
}
