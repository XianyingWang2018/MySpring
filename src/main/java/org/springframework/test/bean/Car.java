package org.springframework.test.bean;


import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements InitializingBean, DisposableBean {

    private String brand;

    @Override
    public void destroy() throws Exception {
        System.out.println("car destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("car init afterProperties");
    }
}
