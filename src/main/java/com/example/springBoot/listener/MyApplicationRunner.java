package com.example.springBoot.listener;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @author qiumeng
 * @version 1.0
 * @description
 * @date 2021/1/20 14:50
 */
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("MyApplicationRunner....");
    }
}
