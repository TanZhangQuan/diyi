package com.lgyun.common.exception;

import com.lgyun.common.api.R;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * 自定义的公共异常处理器
 * 1.声明异常处理器
 * 2.对异常统一处理
 */
@ControllerAdvice
public class BaseExceptionHandler {

    //处理DTO参数判断抛出的异常ConstraintViolationException
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public R exceptionHandler(ConstraintViolationException e) {
        return R.fail(e.getMessage().replaceAll("[^\\u4e00-\\u9fa5]", ""));
    }

    //处理DTO参数判断抛出的异常MethodArgumentNotValidException
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R exceptionHandler(MethodArgumentNotValidException e) {
        return R.fail(e.getBindingResult().getFieldError().getDefaultMessage());
    }

}
