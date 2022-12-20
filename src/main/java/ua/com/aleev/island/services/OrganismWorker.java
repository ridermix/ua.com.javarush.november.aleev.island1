package ua.com.aleev.island.services;

import ua.com.aleev.island.entity.map.GameMap;
import ua.com.aleev.island.entity.map.Location;
import ua.com.aleev.island.entity.organism.Organism;
import ua.com.aleev.island.entity.organism.animal.Animal;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrganismWorker implements Runnable {

    private final Organism prototype;
    private final GameMap gameMap;
    private final Queue<Task> tasks = new ConcurrentLinkedQueue<>();


    public OrganismWorker(Organism prototype, GameMap gameMap) {
        this.prototype = prototype;
        this.gameMap = gameMap;
    }

    @Override
    public void run() {
        Location[][] locations = gameMap.getLocations();
        for (Location[] row : locations) {
            for (Location location : row) {
                processOneLocation(location);
            }
        }
    }

    private void processOneLocation(Location location) {
        String type = prototype.getType();
        Set<Organism> organisms = location.getResidents().get(type);
        if (organisms != null) {
            location.getLock().lock();
            try {

                organisms.forEach(organism -> {
                    Task task = new Task(organism, o -> {
                        o.spawn(location);
                        if (organism instanceof Animal) {
                            ((Animal) organism).eat(location);
                            ((Animal) organism).move(location);
                        }
                    });
                    tasks.add(task);
                });
            } finally {
                location.getLock().unlock();
            }
            tasks.forEach(Task::run);
            tasks.clear();
        }
    }
}
