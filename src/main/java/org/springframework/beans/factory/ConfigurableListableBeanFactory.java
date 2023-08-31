package org.springframework.beans.factory;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;

public interface ConfigurableListableBeanFactory  extends ListableBeanFactory, AutowireCapableBeanFactory {

    /**
     * 根据名称查找BeanDefinition
     * @param beanName
     * @return
     * @throws BeanException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    /**
     * 提前实例化所有单例实例
     * @throws BeanException
     */
    void preInstantiateSingletons() throws BeanException;


    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
