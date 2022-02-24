package com.basic.admin.common.security;

import com.basic.admin.common.enums.BizCodeEnum;
import com.basic.admin.common.exception.ApiException;
import com.basic.admin.common.result.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author sys
 */
@Slf4j
@RestControllerAdvice
public class ValidExceptionHandler
{
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonData parameterMissingExceptionHandler(MethodArgumentNotValidException e)
    {
        log.error("系统全局异常！- {}", e.getMessage());
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        return JsonData.buildError(allErrors.get(0).getDefaultMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = ApiException.class)
    public JsonData handle(ApiException e)
    {
        return JsonData.buildCodeAndMsg(BizCodeEnum.OPS_EXCEPTION.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public JsonData ConstraintViolationExceptionHandler(ConstraintViolationException e)
    {
        return JsonData.buildCodeAndMsg(BizCodeEnum.OPS_EXCEPTION.getCode(), e.getMessage());
    }


}
