package org.springframework.beans.factory.support;

import org.springframework.beans.BeanException;
import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 通过jdk动态代理
 */
public class JdkInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeanException {
        Class beanClass = beanDefinition.getBeanClass();
        Object baseObj = null;
        try{
            Constructor declaredConstructor = beanClass.getDeclaredConstructor();
            baseObj = declaredConstructor.newInstance();
        }catch (Exception e){
            throw new BeanException("instantiation failed [" + beanClass.getName() + "]");
        }
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler();
        myInvocationHandler.setTarget(baseObj);
        return myInvocationHandler.getProxy();
    }

    class MyInvocationHandler implements InvocationHandler{
        private Object target;

        public void setTarget(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws BeanException{
            Object result = null;
            try {
                result = method.invoke(target, args);
                return result;
            }catch (Exception e){
                throw new BeanException("invoke method failed", e);
            }
        }

        public Object getProxy(){
            return Proxy.newProxyInstance(this.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
        }
    }
}
