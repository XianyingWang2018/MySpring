package org.springframework.test.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshEvent event) {
        System.out.println("context refreshed " + this.getClass().getName());
    }
}
