package org.springframework.test.bean;

import org.springframework.beans.factory.FactoryBean;

public class CarFactoryBean implements FactoryBean<Car> {

    private String brand;

    @Override
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setBrand(brand);
        return car;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
