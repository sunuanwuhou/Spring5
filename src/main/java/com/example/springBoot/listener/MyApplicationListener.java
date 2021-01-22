package com.example.springBoot.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author qiumeng
 * @version 1.0
 * @description
 * @date 2021/1/20 14:44
 */
public class MyApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.println("MyApplicationListener.......");
    }
}
