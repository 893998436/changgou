package com.changgou.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BaseExceptionHandler {
/*
* 异常处理
* */
@ExceptionHandler(value = Exception.class)
@ResponseBody
public Result error(Exception e){
  e.printStackTrace();
    return new Result(false, StatusCode.ERROR,e.getMessage());
}
}
