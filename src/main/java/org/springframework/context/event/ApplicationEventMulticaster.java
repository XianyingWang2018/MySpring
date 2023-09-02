package org.springframework.context.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public interface ApplicationEventMulticaster {

    /**
     * 向容器添加监听器的功能
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 向容器移除监听器的功能
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 多播事件
     * @param event
     */
    void multicastEvent(ApplicationEvent event);

}
