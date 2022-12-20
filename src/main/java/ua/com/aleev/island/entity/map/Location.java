package ua.com.aleev.island.entity.map;

import ua.com.aleev.island.entity.organism.Organism;
import ua.com.aleev.island.util.Randomizer;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Location {
    private final Map<String, Set<Organism>> residents;
    private List<Location> nextLocation;
    private final Lock lock = new ReentrantLock(true);

    public Location(Map<String, Set<Organism>> residents) {
        this.residents = residents;
    }

    public Map<String, Set<Organism>> getResidents() {
        return residents;
    }

    public void setNextLocation(List<Location> nextLocation) {
        this.nextLocation = nextLocation;
    }

    public List<Location> getNextLocation() {
        return nextLocation;
    }

    public Location getNextLocations(int countStep) {
        Set<Location> visitedLocations = new HashSet<>();
        Location currentLocation = this;
        while (visitedLocations.size() < countStep) {
            var nextLocations = currentLocation
                    .nextLocation
                    .stream()
                    .filter(location -> !visitedLocations.contains(location)).collect(Collectors.toList());
            int countDirections = nextLocations.size();
            if (countDirections > 0) {
                int index = Randomizer.random(0, countDirections);
                currentLocation = nextLocations.get(index);
                visitedLocations.add(currentLocation);
            } else {
                break;
            }
        }
        return currentLocation;
    }

    public Lock getLock() {
        return lock;
    }
}
