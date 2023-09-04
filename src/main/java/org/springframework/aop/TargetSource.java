package org.springframework.aop;

/**
 * 封装被代理对象
 */
public class TargetSource {

    private final Object target;

    public TargetSource(Object target){
        this.target = target;
    }

    public Class<?>[] getTargetClass(){
        return this.target.getClass().getInterfaces();
    }

    public Object getTarget(){
        return target;
    }

}
