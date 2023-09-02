package org.springframework.context.support;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 抽象引用上下文
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeanException {
        // 创建beanFactory,并加载beanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 添加ApplicationContextAwareProcessor,让继承自ApplicationContextAware的bean能够感知到applicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 在bean实例化前，执行beanFactoryProcessor
        invokeBeanFactoryProcessor(beanFactory);

        // BeanPostProcessor需要在其他bean实例化前注册
        registerBeanPostProcessor(beanFactory);

        // 初始化事件发布者
        initApplicationEventMulticaster();

        // 注册事件监听器
        registerListeners();

        // 提前实例化单例bean
        beanFactory.preInstantiateSingletons();

        // 发布容器刷新完成事件
        finishRefresh();
    }

    /**
     * 发布容器刷新完成事件
     */
    protected void finishRefresh() {
        publishEvent(new ContextRefreshEvent(this));
    }

    /**
     * 多播事件
     * @param event
     */
    public void publishEvent(ApplicationEvent event){
        applicationEventMulticaster.multicastEvent(event);
    }

    /**
     * 注册事件监听器
     */
    protected void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for(ApplicationListener applicationListener : applicationListeners){
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    /**
     * 初始化事件发布者
     */
    protected void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.addSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
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

    protected void doClose() {
        publishEvent(new ContextClosedEvent(this));
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
