package ua.com.aleev.island.factory;

import ua.com.aleev.island.entity.map.GameMap;
import ua.com.aleev.island.entity.map.Location;
import java.util.List;

public class GameMapCreator {
    private final Factory entityFactory;


    public GameMapCreator(Factory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public GameMap createRandomFilledGameMap(int rows, int cols) {
        GameMap gameMap = new GameMap(rows, cols);
        Location[][] locations = gameMap.getLocations();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[i].length; j++) {
                locations[i][j] = entityFactory.createRandomLocation();
            }
        }
        for (int row = 0; row < locations.length; row++) {
            for (int col = 0; col < locations[row].length; col++) {
                Location location = locations[row][col];
                List<Location> nextLocation = location.getNextLocation();
                if (row > 0) nextLocation.add(locations[row - 1][col]);
                if (col > 0) nextLocation.add(locations[row][col - 1]);
                if (row < rows - 1) nextLocation.add(locations[row + 1][col]);
                if (col < cols - 1) nextLocation.add(locations[row][col + 1]);
            }
        }
        return gameMap;
    }
}
