package org.springframework.beans.factory;

import org.springframework.beans.BeanException;

import java.util.HashMap;
import java.util.Map;


/**
 * 版本1 beanFactory自己有保存和获取的功能
 */
/*public class BeanFactory {

    private Map<String,Object> beanMap = new HashMap<>();

    public void registerBean(String name, Object bean){
        beanMap.put(name, bean);
    }

    public Object getBean(String name){
        return beanMap.get(name);
    }

}*/

public interface BeanFactory {

    Object getBean(String name) throws BeanException;
}
