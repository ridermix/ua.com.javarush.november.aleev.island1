package ua.com.aleev.island.view;

import ua.com.aleev.island.entity.map.GameMap;
import ua.com.aleev.island.entity.map.Location;
import ua.com.aleev.island.entity.organism.Organism;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConsoleView implements View {
    private final GameMap gameMap;

    public ConsoleView(GameMap gameMap) {
        this.gameMap = gameMap;
    }


    @Override
    public void showStatistics() {
        Location[][] locations = gameMap.getLocations();
        Map<String, Integer> map = new HashMap<>();
        for (Location[] row : locations) {
            for (Location location : row) {
                Map<String, Set<Organism>> residents = location.getResidents();
                residents.values().stream()
                        .filter(s -> s.size() > 0)
                        .forEach(s -> map.put(s.stream().findAny().get().getIcon(), s.size()));
                System.out.print(map);
                map.clear();
            }
            System.out.println();

        }

    }

    @Override
    public void showGeneralStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        Location[][] locations = gameMap.getLocations();
        for (Location[] row : locations) {
            for (Location location : row) {
                Map<String, Set<Organism>> residents = location.getResidents();
                if (residents != null) {
                    residents.values().stream()
                            .filter(set -> set.size() > 0)
                            .forEach(set -> {
                                        String icon = set.stream().findAny().get().getIcon();
                                        statistics.put(icon, statistics.getOrDefault(icon, 0) + set.size());
                                    }
                            );
                }
            }
        }
        System.out.println("=".repeat(82));
        System.out.print(statistics + "\n");
        System.out.println("=".repeat(82));

    }
}
