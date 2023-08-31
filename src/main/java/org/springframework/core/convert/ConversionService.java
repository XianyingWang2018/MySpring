package org.springframework.core.convert;

/**
 * 类型抽象转换接口
 */
public interface ConversionService {

    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    <T> T convert(Object source, Class<T> targetType);
}
