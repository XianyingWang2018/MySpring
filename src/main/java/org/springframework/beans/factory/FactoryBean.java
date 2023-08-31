package org.springframework.beans.factory;

/**
 * 泛型bean对象
 * @param <T>
 */
public interface FactoryBean<T>{

    T getObject() throws Exception;

    boolean isSingleton();

}
