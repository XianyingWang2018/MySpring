package org.springframework.test.commo;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.test.bean.Car;

public class CustomBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        System.out.println("CustomerBeanPostProcessor#postProcessBeforeInitialization:[" + beanName + "]");
        if("car".equals(beanName)){
            ((Car)bean).setBrand("兰博基尼");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        System.out.println("CustomerBeanPostProcessor#postProcessAfterInitialization:[" + beanName + "]");
        return bean;
    }
}
