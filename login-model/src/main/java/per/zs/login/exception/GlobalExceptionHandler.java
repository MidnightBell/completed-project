package per.zs.login.exception;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import per.zs.login.beans.enums.HttpCodeEnum;
import per.zs.login.beans.response.ResultBaseRes;


/**
* Create time 2020年8月25日 上午9:34:03 
* @author sheng.zhong 
* @Description 统一异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 参数校验的BindException异常处理
     */
    @SuppressWarnings({"unused"})
    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Object validExceptionHandler(BindException e){
        return ResultBaseRes.builder(HttpCodeEnum.PARAM_ERROR, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 参数校验的MethodArgumentNotValidException异常处理
     */
    @SuppressWarnings({"unused"})
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Object validExceptionHandler(MethodArgumentNotValidException e){
        return ResultBaseRes.builder(HttpCodeEnum.PARAM_ERROR, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     *  GET传参参数不存在抛出的异常
     */
    @SuppressWarnings({"unused"})
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Object missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e){
        return ResultBaseRes.builder(HttpCodeEnum.PARAM_ERROR, e.getMessage());
    }
    
    
    @SuppressWarnings({"unused"})
    @ExceptionHandler(ParamException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Object paramExceptionHandler(ParamException e){
        return ResultBaseRes.builder(HttpCodeEnum.PARAM_ERROR, e.getMessage());
    }
    
    @SuppressWarnings({"unused"})
    @ExceptionHandler(UnLoginException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Object unLoginExceptionHandler(UnLoginException e){
        return ResultBaseRes.builder(HttpCodeEnum.UN_LOGIN, e.getMessage());
    }

    @SuppressWarnings({"unused"})
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Object customExceptionHandler(CustomException e){
        return ResultBaseRes.builder(HttpCodeEnum.FAIL, e.getMessage());
    }
    
    /**
     *  全局其他异常处理
     */
    @SuppressWarnings({"unused"})
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Object exceptionHandler(Exception e){
        e.printStackTrace();
        return ResultBaseRes.builder(HttpCodeEnum.FAIL, e.getMessage());
    }
}
