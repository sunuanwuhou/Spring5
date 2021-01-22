package com.example.springBoot.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qiumeng
 * @version 1.0
 * @description
 * @date 2021/1/20 14:41
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        System.out.println("MyApplicationContextInitializer.......");
    }
}
