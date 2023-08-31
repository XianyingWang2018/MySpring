package org.springframework.beans.factory.config;

import org.springframework.beans.BeanException;

public interface BeanPostProcessor {

    /**
     * 在bean执行初始化前执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeanException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException;

    /**
     * 在bean执行初始化方法后执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeanException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException;

}
