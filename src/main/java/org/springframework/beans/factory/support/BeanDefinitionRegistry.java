package org.springframework.beans.factory.support;


import org.springframework.beans.BeanException;
import org.springframework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注册beanDefinition
     * @param beanName
     * @param beanDefinition
     */
    void registryBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 根据名称查找beanDefinition
     * @param beanName
     * @return
     * @throws BeanException  如果找不到抛出异常
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    /**
     * 查询包含指定名称的beanDefinition
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取所有定义的的bean的名称
     * @return
     */
    String[] getBeanDefinitionNames();
}
