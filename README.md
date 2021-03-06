# srping5 新特性

[TOC]

基于Spring5

# 1.Spring概念

+ Spring是轻量级开源javaEE框架
+ 解决企业应用开发的复杂性
+ 核心:IOC、AOP
    + IOC:控制反转，将创建对象交给Spring管理
    + AOP：面向切面，不修改源代码进行功能增强
+ Spring特点
    + 方便解耦，简化开发
    + AOP支持
    + 方便测试 如Junit5
    + 集成其他框架
    + 方便进行事务操作
    + 降低APi开发难度

# 2.IOC容器

## IOC底层原理

**XML解析、工厂模式、反射 通过加载配置文件、获取类的全路径、反射类**
![img.png](img.png)

## IOC接口（BeanFactory）

+ IOC思想基于IOC容器完成,IOC容器底层就是对象工厂。
+ Spring提供IOC容器实现的2种方式:
    + BeanFactory：IOC容器基本实现，Spring内部使用接口
        + 加载配置文件时候**不会**创建对象，获取对象时后才会创建。
    + ApplicationContext：BeanFactory的子接口，提供更多强大的功能，一般是开发人员使用
        + 加载配置文件时候会创建对象。一般采用这种，将耗时放在启动时候

```java
ApplicationContext context=new ClassPathXmlApplicationContext("bean.xml");
        BeanFactory beanFactory=new ClassPathXmlApplicationContext("bean.xml");
```

+ ApplicationContext实现类
    + FileSystemXmlApplicationContext：系统文件
    + ClassPathXmlApplicationContext:相对路径 src下

## IOC操作Bean

什么是Spring管理bean

+ Spring创建对象 IOC
+ Spring注入属性 DI

有XML和注解2种方式,随着Spring的发展，xml基本退出舞台，我们这里只看注解的方式

### 注解

组件注册、注入、赋值。详情可以见Spring注解驱动文章

https://blog.csdn.net/qiumeng_1314/article/details/107144536

# 3.AOP

1.什么是AOP

+ 面向切面编程
+ 不修改源代码 增强功能

## AOP底层原理

1.AOP底层使用动态代理

+ 接口：JDK
  **创建接口实现类代理对象，增强类的方法**

```java
public class InvocationHandlerImpl implements InvocationHandler {

    private Object object;

    public InvocationHandlerImpl(Object object) {
        this.object = object;
    }

    /**
     * 该方法负责集中处理动态代理类上的所有方法调用。
     * 调用处理器根据这三个参数进行预处理或分派到委托类实例上反射执行
     *
     * @param proxy  代理类实例
     * @param method 被调用的方法对象
     * @param args   调用参数
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在代理真实对象前我们可以添加一些自己的操作
        System.out.println("在调用之前，我要干点啥呢？");

        System.out.println("Method:" + method);

        //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        Object returnValue = method.invoke(object, args);

        //在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("在调用之后，我要干点啥呢？");

        return returnValue;

    }

    public static void main(String[] args) {
        UserService userServiceImpl = new UserServiceImpl();
        ClassLoader loader = userServiceImpl.getClass().getClassLoader();
        Class[] interfaces = userServiceImpl.getClass().getInterfaces();
        UserService userService = (UserService) Proxy.newProxyInstance(loader, interfaces, new InvocationHandlerImpl(userServiceImpl));
        userService.login();

    }
}
```

+ 没有接口:CGLB
  **创建当前类子类的代理对象，增强类的方法**

```java
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib before");
        Object result = methodProxy.invokeSuper(obj, objects);
        System.out.println("cglib after");
        return result;
    }

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
//设置父类
        enhancer.setSuperclass(Dog.class);
//设置方法拦截处理器
        enhancer.setCallback(new MyMethodInterceptor());
        //创建代理对象
        Dog dog = (Dog) enhancer.create();
        dog.eat();
    }
}
```

## 术语

1.连接点 类里哪里方法可以被增强，这些方法称为连接点 2.切入点 实际被真正增强的方法，称为切入点 3.通知 实际增强逻辑的部分称为通知 前置通知 后置通知 环绕通知 异常通知 最终通知 4.切面 把通知应用到切入点过程

## AOP操作

1.Spring一般都是基于AsprctJ实现AOP操作

2.切入点表达式 execution(<修饰符模式>?<返回类型模式><方法名模式>(<参数模式>)<异常模式>?)
https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj

```java

@Aspect
//数值越小 优先级越高
@Order(1)
@Configuration
public class UserProxy {
    @Pointcut("execution(* com.example.springBoot.AOP.User.add(..))")
    public void pointCont() {
    }

    @Before(value = "pointCont()")
    //不管有没有异常都会执行
    @After(value = "pointCont()")
    //方法before之前和after之前都执行
    @Around(value = "pointCont()")
    @AfterReturning(value = "pointCont()")
    @AfterThrowing(value = "pointCont()")
    public void before() {
        System.out.println("before");
    }
}
```

# 4.Spring5新特性

## Webflux

### 1.基本介绍

+ 是spring5新添加的模块，用于web开发，使用响应式编程出现的框架
+ 传统的web都是基于servler。webflux是基于异步非阻塞的，servlet3.1以后才支持。核心是Reactor相关API实现的
+ 异步非阻塞
    + 异步和同步,都是争对调用者
    + 阻塞和非阻塞,都是争对被调用者
+ 特点
    + 非阻赛式:可以提高系统吞吐量。
    + 函数式：可以使用java8函数式编程特点，实现路由请求

### 2.响应式编程

响应式编程是一种面向数据流和变化传播的编程范式

提供观察者模式的2个类Observer和Observable

#### java版本
```java
        //java8版本
       ObserverDemo observerDemo=new ObserverDemo();
               observerDemo.addObserver((o,arg)->{
               System.out.println("change1");
               });
               observerDemo.addObserver((o,arg)->{
               System.out.println("change2");
               });
               observerDemo.setChanged();
               observerDemo.notifyObservers();
```

```java
//java9
Flow.Publisher<String> publisher=subscriber->{
        subscriber.onNext("1");
        subscriber.onNext("2");
        subscriber.onError(new RuntimeException("error"));
        };

        publisher.subscribe(new Flow.Subscriber<String>(){
@Override
public void onSubscribe(Flow.Subscription subscription){
        subscription.cancel();
        }

@Override
public void onNext(String item){
        System.out.println(item);
        }

@Override
public void onError(Throwable throwable){

        }

@Override
public void onComplete(){

        }
        });

```

#### Reactor
 
+ Reactor是满足Reactive规范框架
+ Reactor有2个核心类，Mono和Flux 都实现了Publisher
    + Flux实现发布者，返回N个元素
    + Mono返回0或者一个
+ Flux和Mono都是数据流的发布者，都可以发出三种数据信号，元素值、错误信号、完成信号。错误信号和完成信号都代表终止信号。用于告诉订阅者数据流结束了。
    
```java
  //just直接申请元素
        Flux.just(1, 2, 3, 4);
        Mono.just(1);

        Integer[] arr = {1, 2, 3, 4};
        Flux.fromArray(arr);
        // Flux.fromIterable()
        // Flux.fromStream()
        Flux.just(1, 2, 3, 4).subscribe(System.out::print);
```
+ 三种信号特点
    + 错误信号和完成信号不能共存
    + 如果没有发送任何元素值，而是直接发送错误或者完成。表示是空数据流。
    + 如果没有错误信号，没有完成信号，表示无限数据流。
+ 调用just只是申明了数据流，只有订阅之后才会触发数据流。    
+ 操作符
    + 对数据流进行操作
    + map：将元素映射为新元素
    + flatMap：把每个元素转为流，在合并为大的流
### 3.Webflux执行流程和核心api 

基于Reator,默认容器时Netty。

1.DispatchHandler

实现WebHandler

```java
//ServerWebExchange 存放http请求信息
    public Mono<Void> handle(ServerWebExchange exchange) {
        return this.handlerMappings == null ? this.createNotFoundError() :
        Flux.fromIterable(this.handlerMappings).concatMap((mapping) -> {
        //根据请求地址获取对应的mapping
            return mapping.getHandler(exchange);
        }).next().switchIfEmpty(this.createNotFoundError()).flatMap((handler) -> {
        //调用具体的业务方法
            return this.invokeHandler(exchange, handler);
        }).flatMap((result) -> {
            //处理结果返回
            return this.handleResult(exchange, result);
        });
    }

```
2.HandlerMapping:请求处理
3.HandlerAdapter：真正负责请求处理
4.HandlerResultHandler：相应结果处理
5.函数式编程
    + RouterFunction
    + HandlerFunction
### 4.基于注解 




### 5.基于函数式


















































