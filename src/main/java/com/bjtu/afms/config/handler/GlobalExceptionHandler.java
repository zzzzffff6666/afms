package com.bjtu.afms.config.handler;

import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.net.BindException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理请求方法不支持的异常
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Result exceptionHandler(HttpServletRequest req, HttpRequestMethodNotSupportedException e) {
        log.error("请求方法不支持！原因是:", e);
        return Result.error(APIError.REQUEST_METHOD_SUPPORT_ERROR);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class, })
    public Result exceptionHandler(HttpServletRequest req, MissingRequestValueException e) {
        log.error("请求参数异常！原因是:", e);
        return Result.error(APIError.PARAMETER_ERROR);
    }

    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(value = BizException.class)
    public Result exceptionHandler(HttpServletRequest req, BizException e) {
        log.error("发生业务异常！原因是：{}", e.getMessage());
        return Result.error(e.getErrorCode(), e.getErrorMsg());
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
