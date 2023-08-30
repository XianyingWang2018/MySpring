package org.springframework.beans.factory;

import org.springframework.beans.BeanException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory{

    /**
     * 返回指定类型的所有实例
     * @param type
     * @return
     * @param <T>
     * @throws BeanException
     */
    <T> Map<String,T> getBeansOfType(Class<T> type) throws BeanException;

    /**
     * 返回定义的所有bean的名称
     * @return
     */
    String[] getBeanDefinitionNames();

}
