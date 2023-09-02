package org.springframework.test.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

import javax.xml.transform.Source;

public class CustomEvent<E extends ApplicationContext> extends ApplicationEvent {

    private String msg;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public CustomEvent(ApplicationContext source) {
        super(source);
    }

    public CustomEvent(ApplicationContext source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
