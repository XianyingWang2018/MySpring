package org.springframework.beans.factory.config;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {


    /**
     * 执行BeanPostProcess的postProcessBeforeInitialization方法
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeanException
     */
    Object applyBeanPostProcessBeforeInitialization(Object existingBean, String beanName) throws BeanException;

    /**
     * 执行beanPostprocess的postProcessAfterInitialization方法
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeanException
     */
    Object applyBeanPostProcessAfterInitialization(Object existingBean, String beanName) throws BeanException;


}
