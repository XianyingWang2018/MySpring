package org.springframework.test.ioc;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.JdkInstantiationStrategy;
import org.springframework.test.service.HelloService;
import org.springframework.test.service.HelloServiceImpl;
import org.springframework.test.service.TestService;


public class BeanDefinitionAndBeanRegistryTest {

    @Test
    public void testBeanDefinitionAndRegistry(){
        DefaultListableBeanFactory beanDefinitionRegistry = new DefaultListableBeanFactory();
        //修改策略为cglib生成
        //beanDefinitionRegistry.setInstantiationStrategy(new CglibSubclassingInstantiationStrategy());
        //修改策略为jdk代理生成
        //beanDefinitionRegistry.setInstantiationStrategy(new JdkInstantiationStrategy());
        BeanDefinition helloBeanDefinition = new BeanDefinition(HelloServiceImpl.class);
        beanDefinitionRegistry.registryBeanDefinition("helloService", helloBeanDefinition);
        HelloService helloService = (HelloService) beanDefinitionRegistry.getBean("helloService");
        assertThat(helloService).isNotNull();
        assertThat(helloService.sayHello()).isEqualTo("hello");
        HelloService helloService2 = (HelloService) beanDefinitionRegistry.getBean("helloService");
        assertThat(helloService2).isNotNull();
        assertThat(helloService2.sayHello()).isEqualTo("hello");
    }

    @Test
    public void testBeanDefinitionAndRegistry2(){
        DefaultListableBeanFactory beanDefinitionRegistry = new DefaultListableBeanFactory();
        //修改策略为cglib生成
        //beanDefinitionRegistry.setInstantiationStrategy(new CglibSubclassingInstantiationStrategy());
        //修改策略为jdk代理生成
        beanDefinitionRegistry.setInstantiationStrategy(new JdkInstantiationStrategy());
        BeanDefinition helloBeanDefinition = new BeanDefinition(TestService.class);
        beanDefinitionRegistry.registryBeanDefinition("testService", helloBeanDefinition);
        HelloService testService = (HelloService) beanDefinitionRegistry.getBean("testService");
        assertThat(testService).isNotNull();
        assertThat(testService.sayHello()).isEqualTo("hello");
        /*TestService testService2 = (TestService) beanDefinitionRegistry.getBean("testService");
        assertThat(testService2).isNotNull();
        assertThat(testService2.sayHello()).isEqualTo("hello");*/
    }
}
