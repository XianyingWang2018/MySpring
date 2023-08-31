package org.springframework.beans.factory.config;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {

    /**
     * 在所有beanDefinition加载完成后，但在实例化之前，提供修改beanDefinition属性值的机制
     * @param factory
     * @throws BeanException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeanException;
}
