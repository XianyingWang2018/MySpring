package org.springframework.context.support;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * 抽象引用上下文
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeanException {
        // 创建beanFactory,并加载beanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 在bean实例化前，执行beanFactoryProcessor
        invokeBeanFactoryProcessor(beanFactory);

        // BeanPostProcessor需要在其他bean实例化前注册
        registerBeanPostProcessor(beanFactory);

        // 提前实例化单例bean
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 注册beanPostProcessor
     * @param beanFactory
     */
    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        // 取出所有的beanPostProcessor初始化
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for(BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()){
            // beanFactory中加入beanPostProcessor
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }

    }

    /**
     * 执行beanFactoryPostProcessor
     * @param beanFactory
     */
    private void invokeBeanFactoryProcessor(ConfigurableListableBeanFactory beanFactory) {
        // 从beanFactory中优先取出beanFactoryPostProcessor对象进行beanFactory的修改
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for(BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()){
            // 执行每个beanFactoryPostProcessor的修改beanFactory的方法
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }


    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public Object getBean(String name) throws BeanException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }



    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    protected abstract void refreshBeanFactory();

    public void close(){
        doClose();
    }

    private void doClose() {
        destroyBeans();
    }

    protected void destroyBeans(){
        getBeanFactory().destroySingletons();
    }

    /**
     * 钩子函数注册
     */
    public void registerShutdownHook(){
        Thread shutdownHook = new Thread(()->{
            doClose();
        });
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }
}
