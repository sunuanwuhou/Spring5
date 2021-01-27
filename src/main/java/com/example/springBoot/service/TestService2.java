package com.example.springBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService2 {

    @Autowired
    private TestService1 testService1;

    public TestService2() {
        System.out.println("实例化TestService2");
    }

    public void test2() {
    }
}