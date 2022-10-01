package ru.nsu.fit.dib.service.impl;

import ru.nsu.fit.dib.service.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public Integer plus(Integer x, Integer y) {
        return x+y;
    }
}
