package com.beehive.rocketmq.producer.handler;


import com.beehive.rocketmq.producer.utils.RequestResult;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Auther: ouyangxiang
 * @Date: 2019-3-22 15:34
 * @Description: 监听系统异常处理器
 */
@RestControllerAdvice
public final class ExceptionAdviceHandler {

    private final static String SERVER_ERROR_TXT = "服务器内部错误";
    private final static String ARGUMENTS_ERROR_TXT = "参数错误";
    private final static String BAD_REQUEST_TXT = "错误的请求";

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RequestResult unKnowExceptionHandler() {
        return this.serverErrorHandler();
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RequestResult runtimeExceptionHandler(RuntimeException ex) {

        ex.printStackTrace();

        return this.serverErrorHandler();
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RequestResult nullPointerExceptionHandler(Exception e) {

        e.printStackTrace();

        return new RequestResult(400, "参数异常");
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RequestResult classCastExceptionHandler() {
        return this.serverErrorHandler();
    }

    /**
     * IO异常
     */
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RequestResult iOExceptionHandler() {
        return this.serverErrorHandler();
    }

    /**
     *  未知方法异常
     */
    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RequestResult noSuchMethodExceptionHandler() {
        return this.serverErrorHandler();
    }

    /**
     * 数组越界异常
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RequestResult indexOutOfBoundsExceptionHandler() {
        return this.serverErrorHandler();
    }

    /**
     * 400错误
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RequestResult requestNotReadable() {
        return new RequestResult(400, BAD_REQUEST_TXT);
    }

    /**
     * 400错误 类型不匹配
     */
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RequestResult requestTypeMismatch() {
        return this.argumentsError();
    }

    /**
     * 400错误 缺少参数
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RequestResult requestMissingServletRequest() {
        return this.argumentsError();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RequestResult methodArgumentNotValidExceptionHandler() {
        return new RequestResult(400, "参数错误");
    }



    /**
     * 405错误
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public RequestResult request405(HttpServletResponse resp) {
        return new RequestResult(405, "请求方法不正确");
    }

    /**
     * 406错误
     */

    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public RequestResult request406(HttpServletResponse resp) {
        return new RequestResult(406, "不接受该请求");

    }
    /**
     * 406错误
     */

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RequestResult request407(HttpServletResponse resp) {
        return new RequestResult(400, "参数异常");

    }

    /**
     * 500错误
     */
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)    public RequestResult server500(HttpServletResponse resp, Exception e) {
        return new RequestResult(400, "参数异常");
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public RequestResult httpMediaTypeNotSupportedExceptionHandler(HttpServletResponse resp) {
        return new RequestResult(415, "服务器无法处理请求附带的媒体格式");
    }

    /**
     * 404
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RequestResult notFoundException(HttpServletResponse response) {
        return new RequestResult(404, "找不到服务");
    }

    @ExceptionHandler(value = ServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RequestResult serverErrorExceptionHandler(HttpServletResponse response) {
        return this.serverErrorHandler();
    }


    private RequestResult serverErrorHandler() {
        return new RequestResult(500, SERVER_ERROR_TXT);
    }

    private RequestResult argumentsError() {
        return new RequestResult(400, ARGUMENTS_ERROR_TXT);
    }

    /**
     * @param code 1 认证错误（未认证）、2 未授权/没有权限
     * @param msg
     * @return
     */
    private RequestResult authErrorHandler(int code, String msg) {
        RequestResult result=new RequestResult();
        result.setCode(code);
        result.setDesc(msg);
        return result;
    }

}
