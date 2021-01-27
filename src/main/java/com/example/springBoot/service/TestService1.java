package com.example.springBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestService1 {

    @Autowired
    private TestService2 testService2;

    public TestService1() {
        System.out.println("实例化TestService1");
    }
    @Async
    public void test1() {
    }
}