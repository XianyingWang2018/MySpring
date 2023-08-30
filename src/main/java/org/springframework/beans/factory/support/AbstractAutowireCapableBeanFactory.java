package org.springframework.beans.factory.support;

import org.springframework.beans.BeanException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Field;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition){
        // 通过策略生成对象
        Object bean = null;
        try{
            bean = createBeanInstance(beanDefinition);
            applyPropertyValues(beanName,bean,beanDefinition);
        }catch (Exception e){
            throw new BeanException("Instantiation of bean failed", e);
        }
        // 放入到beanMap里
        addSingleton(beanName, bean);
        return bean;
    }

    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try{
            for(PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()){
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                if(value instanceof BeanReference){
                    // 说明是引用，则要看是否已经创建了，如果没有创建，则进行创建
                    BeanReference beanReference = (BeanReference) value;
                    String referBeanName = beanReference.getBeanName();
                    value = getBean(referBeanName);
                }
                // 反射设置属性
                setFieldValue(bean,name,value);
            }
        }catch (Exception e){
            throw new BeanException("Error setting property values for bean:"+ beanName, e);
        }
    }

    protected void setFieldValue(Object bean, String name, Object value) {
        Class aClass = bean.getClass();
        try {
            Field declaredField =aClass.getDeclaredField(name);
            declaredField.setAccessible(true);
            declaredField.set(bean, value);
        } catch (Exception e) {
            throw new BeanException("set field ["+ name +"] value [" + value + "] failed",e);
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition){
        return getInstantiationStrategy().instantiate(beanDefinition);
    }


    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }
}
