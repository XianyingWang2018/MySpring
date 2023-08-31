package org.springframework.test.commo;

import org.springframework.beans.BeanException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeanException {
        BeanDefinition person = factory.getBeanDefinition("person");
        PropertyValues propertyValues = person.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name","zhangsan"));
    }
}
