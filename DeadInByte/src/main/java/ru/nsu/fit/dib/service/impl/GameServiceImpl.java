package ru.nsu.fit.dib.service.impl;

import ru.nsu.fit.dib.service.GameService;

public class GameServiceImpl implements GameService {

    @Override
    public Integer plus(Integer x, Integer y) {
        return x+y;
    }
}
