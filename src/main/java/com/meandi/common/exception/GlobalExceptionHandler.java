package com.meandi.common.exception;

import com.meandi.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常捕获
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //标注捕获哪种异常
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handler(RuntimeException e){
        log.error("运行时异常:------------{}",e);
        return Result.fail(e.getMessage());
    }

    //shiro异常
    @ExceptionHandler(value = ShiroException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handler(ShiroException e){
        log.error("运行时异常:------------{}",e);
        return Result.fail(401,e.getMessage(),null);
    }

    //实体不完整异常
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handler(MethodArgumentNotValidException e){
        log.error("实体校验异常:------------{}",e);
        return Result.fail(401,e.getMessage(),null);
    }
}
