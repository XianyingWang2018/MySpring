package org.springframework.beans.factory.support;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition){
        // 通过策略生成对象
        Object bean = null;
        try{
            // 调用不同策略生成bean对象
            bean = createBeanInstance(beanDefinition);
            // 为bean填充属性
            applyPropertyValues(beanName,bean,beanDefinition);
            // 执行bean的初始化方法和beanPostProcessor的前置和后置处理方法
            bean = initializeBean(beanName,bean,beanDefinition);
        }catch (Exception e){
            throw new BeanException("Instantiation of bean failed", e);
        }
        // 注册有销毁方法的bean
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 放入到beanMap里
        addSingleton(beanName, bean);
        return bean;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if(bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())){
            registerDisposableBean(beanName,new DisposableBeanAdapter(bean,beanName, beanDefinition));
        }
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


    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 加入beanFactoryAware感知beanFactory
        if(bean instanceof BeanFactoryAware){
            ((BeanFactoryAware)bean).setBeanFactory(this);
        }

        // 执行前置处理器
        Object wrappedBean = applyBeanPostProcessBeforeInitialization(bean, beanName);

        try{
            //执行bean的初始化方法
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        }catch (Throwable ex){
            throw new BeanException("Invocation of init method of bean [" + beanName + "] failed", ex);
        }

        // 执行后置处理器
        wrappedBean = applyBeanPostProcessAfterInitialization(bean, beanName);

        return wrappedBean;
    }

    @Override
    public Object applyBeanPostProcessBeforeInitialization(Object existingBean, String beanName) throws BeanException {
        Object result = existingBean;
        for(BeanPostProcessor postProcessor : getBeanPostProcessors()){
            Object current = postProcessor.postProcessBeforeInitialization(result, beanName);
            if(current == null){
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessAfterInitialization(Object existingBean, String beanName) throws BeanException {
        Object result = existingBean;
        for(BeanPostProcessor postProcessor : getBeanPostProcessors()){
            Object current = postProcessor.postProcessAfterInitialization(result, beanName);
            if(current == null){
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * 顺序： setProperties->postProcessor(before)-> invokeInitMethods - > postProcessor(after)
     * 执行bean的init方法
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @throws Throwable
     */
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Throwable {
        if(bean instanceof InitializingBean){
            ((InitializingBean)bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        if(StrUtil.isNotEmpty(initMethodName)){
            Method initMethod = ClassUtil.getPublicMethod(bean.getClass(), initMethodName);
            if(initMethod == null){
                throw new BeanException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }


    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }




}
