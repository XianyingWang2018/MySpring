package org.springframework.test.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.Arrays;

public class CustomEventListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        ApplicationContext source = (ApplicationContext)event.getSource();
        System.out.println(Arrays.toString(source.getBeanDefinitionNames()));
        System.out.println("custom :" + this.getClass().getName() +  " " + event.getMsg());
        // 我在这里发送邮件
    }
}
