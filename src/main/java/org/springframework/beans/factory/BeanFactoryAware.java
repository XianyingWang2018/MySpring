package org.springframework.beans.factory;

import org.springframework.beans.BeanException;

public interface BeanFactoryAware extends Aware{

    /**
     * BeanFactoryAware需要支持设置beanFactory
     * @param beanFactory
     * @throws BeanException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeanException;
}
