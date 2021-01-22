package com.example.springBoot.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author qiumeng
 * @version 1.0
 * @description
 * @date 2021/1/22 14:40
 */
public class config1 {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");

        BeanFactory beanFactory =  new ClassPathXmlApplicationContext("bean.xml");

    }
}
