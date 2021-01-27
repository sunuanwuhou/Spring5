package com.example.springBoot.AOP;

import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiumeng
 * @version 1.0
 * @description
 * @date 2021/1/27 14:57
 */
@Aspect
@Configuration
public class UserProxy {


    @Pointcut("execution(* com.example.springBoot.AOP.User.add(..))")
    public void pointCont(){

    }

    @Before(value = "pointCont()")
    @After(value = "pointCont()")
    //方法before之前和after之后都执行
    @Around(value = "pointCont()")
    @AfterReturning(value = "pointCont()")
    @AfterThrowing(value = "pointCont()")
    public void before(){
        System.out.println("before");
    }
}
