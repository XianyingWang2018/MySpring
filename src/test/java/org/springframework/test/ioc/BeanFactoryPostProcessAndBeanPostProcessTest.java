package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.beans.BeanException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.JdkInstantiationStrategy;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.test.bean.Car;
import org.springframework.test.bean.Person;
import org.springframework.test.commo.CustomBeanFactoryPostProcessor;
import org.springframework.test.service.HelloService;
import org.springframework.test.service.HelloServiceImpl;
import org.springframework.test.service.TestService;

import static org.assertj.core.api.Assertions.assertThat;


public class BeanFactoryPostProcessAndBeanPostProcessTest {

    @Test
    public void testBeanFactoryPostProcess(){
        // 定义registry
        DefaultListableBeanFactory beanDefinitionRegistry = new DefaultListableBeanFactory();
        // 定义reader
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanDefinitionRegistry);
        // 加载xml文件
        reader.loadBeanDefinitions("classpath:spring.xml");
        // 配置beanFactoryPostProcess
        CustomBeanFactoryPostProcessor customBeanFactoryPostProcessor = new CustomBeanFactoryPostProcessor();
        // 对registry进行前置处理
        customBeanFactoryPostProcessor.postProcessBeanFactory(beanDefinitionRegistry);

        // 尝试获取person对象
        Person person = (Person) beanDefinitionRegistry.getBean("person");
        System.out.println(person);
    }

    @Test
    public void testBeanPostProcess(){
        // 定义registry
        DefaultListableBeanFactory beanDefinitionRegistry = new DefaultListableBeanFactory();
        // 定义reader
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanDefinitionRegistry);
        // 加载xml文件
        reader.loadBeanDefinitions("classpath:spring.xml");
        // 配置beanFactoryPostProcess
        CustomBeanFactoryPostProcessor customBeanFactoryPostProcessor = new CustomBeanFactoryPostProcessor();
        // 对registry进行前置处理
        customBeanFactoryPostProcessor.postProcessBeanFactory(beanDefinitionRegistry);
        // 配置beanPostProcess
        beanDefinitionRegistry.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
                System.out.println("CustomerBeanPostProcessor#postProcessBeforeInitialization:[" + beanName + "]");
                if("car".equals(beanName)){
                    ((Car)bean).setBrand("兰博基尼");
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
                System.out.println("CustomerBeanPostProcessor#postProcessAfterInitialization:[" + beanName + "]");
                return bean;
            }
        });

        // 尝试获取person对象
        Person person = (Person) beanDefinitionRegistry.getBean("person");
        System.out.println(person);
    }
}
