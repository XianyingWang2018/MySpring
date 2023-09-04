package org.springframework.test.service;

public class WorldServiceImpl implements WorldService{
    @Override
    public void explode() {
        System.out.println("The earth is going to explode!");
    }
}
