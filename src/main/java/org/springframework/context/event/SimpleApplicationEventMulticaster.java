package org.springframework.context.event;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory){
        setBeanFactory(beanFactory);
    }

    /**
     * 实现多播事件
     * @param event
     */
    @Override
    public void multicastEvent(ApplicationEvent event) {
        // 遍历listener
        for(ApplicationListener<ApplicationEvent> applicationListener : applicationListeners){
            // 监听器对事件感兴趣，则通知
            if(supportsEvent(applicationListener, event)){
                applicationListener.onApplicationEvent(event);
            }
        }
    }

    /**
     * 监听器是否对事件感兴趣
     * @param applicationListener
     * @param event
     * @return
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        Type type = applicationListener.getClass().getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try{
            eventClassName = Class.forName(className);
        }catch (ClassNotFoundException e){
            throw new BeanException("wrong event class name : " + className);
        }
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
