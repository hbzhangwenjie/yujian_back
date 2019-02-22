package com.yingzi.guanguai.controller;

import com.yingzi.guanguai.constant.Constants;
import com.yingzi.guanguai.dto.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExAdvice {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResponse errorHandler(Exception ex) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setResult(Constants.FAIL.getValue());
        return baseResponse;
    }
}
