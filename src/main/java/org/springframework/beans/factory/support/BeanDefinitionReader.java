package org.springframework.beans.factory.support;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeanException;


    void loadBeanDefinitions(String location) throws BeanException;


    void loadBeanDefinitions(String[] locations) throws BeanException;
}
