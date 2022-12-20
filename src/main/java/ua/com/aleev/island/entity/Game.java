package ua.com.aleev.island.entity;

import ua.com.aleev.island.entity.map.GameMap;
import ua.com.aleev.island.factory.Factory;
import ua.com.aleev.island.view.View;

public class Game {
    private final GameMap gameMap;
    private final Factory entityFactory;
    private final View view;

    public Game(GameMap gameMap, Factory entityFactory, View view) {
        this.gameMap = gameMap;
        this.entityFactory = entityFactory;
        this.view = view;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Factory getEntityFactory() {
        return entityFactory;
    }

    public View getView() {
        return view;
    }
}
