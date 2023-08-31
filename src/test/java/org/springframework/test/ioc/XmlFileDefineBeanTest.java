package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.test.bean.Person;

import static org.assertj.core.api.Assertions.*;

public class XmlFileDefineBeanTest {

    @Test
    public void testXmlFiledDefineBean(){
        String xmlPath = "classpath:spring.xml";
        // beanDefinitionRegistry
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 创建reader
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(xmlPath);
        // 读取bean对象
        BeanDefinition personBeanDefinition = beanFactory.getBeanDefinition("person");
        Person person = (Person) beanFactory.getBean("person");
        assertThat(person.getAge()).isEqualTo("10");
        assertThat(person.getName()).isEqualTo("wxy");
        assertThat(person.getCar().getBrand()).isEqualTo("本田");

    }
}
