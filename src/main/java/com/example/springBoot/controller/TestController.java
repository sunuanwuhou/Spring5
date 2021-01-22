package com.example.springBoot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiumeng
 * @version 1.0
 * @description
 * @date 2020/7/15 15:05
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/test")
    public String test(){
        return "111";
    }
}
