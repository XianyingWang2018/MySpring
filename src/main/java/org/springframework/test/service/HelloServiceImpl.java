package org.springframework.test.service;


public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        System.out.println("hello!");
        return "hello";
    }
}
