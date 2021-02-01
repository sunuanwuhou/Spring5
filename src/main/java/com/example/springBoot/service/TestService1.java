package com.example.springBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService1 {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TestService2 testService2;


    public TestService1() {
        System.out.println("实例化TestService1");
    }
    @Async
    @Transactional
    public void test1() {
    }
}