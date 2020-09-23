package com.lgyun.common.exception;

import com.lgyun.common.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 自定义的公共异常处理器
 * 1.声明异常处理器
 * 2.对异常统一处理
 */
@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    //处理DTO参数判断抛出的异常ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public R exceptionHandler(ConstraintViolationException e) {
        return R.fail(e.getMessage().replaceAll("[^\\u4e00-\\u9fa5]", ""));
    }

    //处理DTO参数判断抛出的异常MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R exceptionHandler(MethodArgumentNotValidException e) {
        return R.fail(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    //处理自定义异常CustomException
    @ExceptionHandler(CustomException.class)
    public R exceptionHandler(CustomException e) {
        log.error(String.valueOf(e));
        return R.fail(e.getResultCode(), e.getMessage());
    }

    //处理非以上异常问题
    @ExceptionHandler(value = Exception.class)
    public R exceptionHandler(Exception e) {
        log.error(String.valueOf(e));
        return R.fail("服务器异常, 操作失败");
    }

}
