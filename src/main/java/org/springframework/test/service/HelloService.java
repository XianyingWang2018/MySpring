package org.springframework.test.service;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContextAware;

public interface HelloService extends BeanFactoryAware, ApplicationContextAware {

    String sayHello();
}
