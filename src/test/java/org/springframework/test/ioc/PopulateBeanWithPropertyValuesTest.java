package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.bean.Person;
import static org.assertj.core.api.Assertions.*;

public class PopulateBeanWithPropertyValuesTest {

    @Test
    public void testPopulateBeanWithPropertyValues(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 设置策略
        //beanFactory.setInstantiationStrategy(new CglibSubclassingInstantiationStrategy());
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name","wxy"));
        propertyValues.addPropertyValue(new PropertyValue("age","10"));
        beanFactory.registryBeanDefinition("person",new BeanDefinition(Person.class, propertyValues));
        Person person = (Person) beanFactory.getBean("person");
        assertThat(person.getAge()).isEqualTo("10");
        assertThat(person.getName()).isEqualTo("wxy");
    }

}
