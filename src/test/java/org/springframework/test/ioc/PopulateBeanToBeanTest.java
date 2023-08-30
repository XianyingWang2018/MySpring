package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.bean.Car;
import org.springframework.test.bean.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class PopulateBeanToBeanTest {

    @Test
    public void populateBeanToBeanTest(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name","wxy"));
        propertyValues.addPropertyValue(new PropertyValue("age","10"));
        propertyValues.addPropertyValue(new PropertyValue("car", new BeanReference("car")));
        PropertyValues carPropertyValues = new PropertyValues();
        carPropertyValues.addPropertyValue(new PropertyValue("brand", "本田"));
        beanFactory.registryBeanDefinition("car", new BeanDefinition(Car.class, carPropertyValues));
        beanFactory.registryBeanDefinition("person",new BeanDefinition(Person.class, propertyValues));
        Person person = (Person) beanFactory.getBean("person");
        assertThat(person.getAge()).isEqualTo("10");
        assertThat(person.getName()).isEqualTo("wxy");
        assertThat(person.getCar().getBrand()).isEqualTo("本田");



    }
}
