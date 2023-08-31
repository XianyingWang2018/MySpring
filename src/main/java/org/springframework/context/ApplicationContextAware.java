package org.springframework.context;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeanException;
}
