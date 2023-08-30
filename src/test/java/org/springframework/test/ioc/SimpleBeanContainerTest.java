package org.springframework.test.ioc;

import org.springframework.beans.factory.BeanFactory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;


public class SimpleBeanContainerTest {

    /*
    版本1的测试
     */
    /*@Test
    public void testGetBean() throws Exception{
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.registerBean("helloService", new HelloService());
        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        assertThat(helloService).isNotNull();
        assertThat(helloService.sayHello()).isEqualTo("hello");
    }*/


}
