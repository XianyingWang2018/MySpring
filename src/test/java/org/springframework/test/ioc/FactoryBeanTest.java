package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.bean.Car;

public class FactoryBeanTest {

    @Test
    public void testFactoryBean(){

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:factoryBean.xml");

        Car car = applicationContext.getBean("car", Car.class);
        Car car2 = (Car) applicationContext.getBean("car");
        System.out.println(car);
        System.out.println(car2);

    }
}
