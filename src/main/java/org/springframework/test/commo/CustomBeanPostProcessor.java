package org.springframework.test.commo;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.bean.Car;
import org.springframework.test.event.CustomEvent;

public class CustomBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    public AbstractApplicationContext applicationContext;

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
                applicationContext.publishEvent(new CustomEvent(applicationContext, "定制化信息"));
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        System.out.println("CustomerBeanPostProcessor#postProcessAfterInitialization:[" + beanName + "]");
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeanException {
        this.applicationContext = (AbstractApplicationContext) applicationContext;
    }
}
