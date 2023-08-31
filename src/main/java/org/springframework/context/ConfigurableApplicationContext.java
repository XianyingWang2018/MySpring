package org.springframework.context;

import org.springframework.beans.BeanException;

public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 刷新容器
     * @throws BeanException
     */
    void refresh() throws BeanException;
}
