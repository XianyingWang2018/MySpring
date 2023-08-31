package org.springframework.test.commo;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.test.bean.Car;

public class CustomBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        System.out.println("CustomerBeanPostProcessor#postProcessBeforeInitialization:[" + beanName + "]");
        if("car".equals(beanName)){
            if(bean instanceof FactoryBean){
                /*try {
                    // 这段就无效了，是一个新的对象，所以改值没用 当使用factoryBean
                    ((Car)((FactoryBean)bean).getObject()).setBrand("兰博基尼FactoryBean");;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }*/
            }else{
                ((Car)bean).setBrand("兰博基尼");
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        System.out.println("CustomerBeanPostProcessor#postProcessAfterInitialization:[" + beanName + "]");
        return bean;
    }
}
