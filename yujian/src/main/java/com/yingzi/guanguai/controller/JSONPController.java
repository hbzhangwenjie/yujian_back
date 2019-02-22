package com.yingzi.guanguai.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;
@ControllerAdvice(basePackages = {"com.yingzi.guanguai.controller"})
public class JSONPController extends AbstractJsonpResponseBodyAdvice {
    public JSONPController(){
        super("callback","jsonp");
    }
}
