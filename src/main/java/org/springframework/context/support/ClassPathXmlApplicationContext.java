package org.springframework.context.support;

import org.springframework.beans.BeanException;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    private final String[] configurations;

    public ClassPathXmlApplicationContext(String configuration) throws BeanException{
        this(new String[]{configuration});
    }

    public ClassPathXmlApplicationContext(String[] configurations) throws BeanException{
        this.configurations = configurations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return this.configurations;
    }
}
