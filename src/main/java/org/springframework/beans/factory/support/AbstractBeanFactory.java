package org.springframework.beans.factory.support;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private final Map<String, Object> factoryBeanObjectCache = new HashMap<>();

    @Override
    public Object getBean(String name) throws BeanException {
        Object sharedInstance = getSingleton(name);
        if(sharedInstance != null){
            return getObjectForBeanInstance(sharedInstance, name);
        }
        // 交给子类去实现
        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition);
        return getObjectForBeanInstance(bean, name);
    }

    /**
     * 如果是FactoryBean,从FactoryBean#getObject中创建bean
     * @param beanInstance
     * @param beanName
     * @return
     */
    protected Object getObjectForBeanInstance(Object beanInstance, String beanName){
        Object object = beanInstance;
        if(object instanceof FactoryBean){
            FactoryBean factoryBean = (FactoryBean)beanInstance;
            try{
                if(factoryBean.isSingleton()){
                    object = factoryBeanObjectCache.get(beanName);
                    if(object == null){
                        object = factoryBean.getObject();
                        factoryBeanObjectCache.put(beanName, object);
                    }
                }else{
                    object = factoryBean.getObject();
                }
            }catch (Exception e){
                throw new BeanException("FactoryBean threw exception on object[" + beanName + "] creation", e);
            }
        }
        return object;
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        // 有则覆盖，顺序改变 因为不同的顺序，可能结果不同，允许用户自定义顺序
        this.getBeanPostProcessors().remove(beanPostProcessor);
        this.getBeanPostProcessors().add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return (T)getBean(name);
    }
}
