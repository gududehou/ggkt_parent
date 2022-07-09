package com.atguigu.ggkt.exception;

import com.atguigu.ggkt.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        //数据校验异常
        if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            ObjectError objectError = allErrors.get(0);
            String message = objectError.getDefaultMessage();
            log.error("数据校验异常{}", e.getMessage());
            return Result.fail(message);
        }
        if (e instanceof DuplicateKeyException) {
            log.error("数据重复异常{}", e.getMessage());
            return Result.fail("数据重复啦");
        }
        //业务异常
        if (e instanceof GgktException) {
            log.error("业务异常{}", ((GgktException) e).getMsg());
            return Result.fail(((GgktException) e).getMsg());
        }
        e.printStackTrace();
        return Result.fail("全局异常处理");
    }
}