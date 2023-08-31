package org.springframework.test.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String name;

    private String age;

    private Car car;

    public void init(){
        System.out.println("person init after properties");
    }

    public void destroy(){
        System.out.println("person destroy");
    }

}
