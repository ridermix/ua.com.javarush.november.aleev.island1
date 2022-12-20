package ua.com.aleev.island.entity.map;

public class GameMap {
    private final Location[][] locations;

    public GameMap(int rows, int cols) {
        this.locations = new Location[rows][cols];
    }

    public Location[][] getLocations() {
        return locations;
    }

    public int getRows() {
        return locations.length;
    }

    public int getCols() {
        return locations[0].length;
    }
}
