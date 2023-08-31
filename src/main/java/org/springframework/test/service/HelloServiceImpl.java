package org.springframework.test.service;


import org.springframework.beans.BeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;

public class HelloServiceImpl implements HelloService {
    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;

    @Override
    public String sayHello() {
        System.out.println("hello!");
        return "hello";
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeanException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
