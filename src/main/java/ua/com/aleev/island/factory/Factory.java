package ua.com.aleev.island.factory;

import ua.com.aleev.island.entity.map.Location;
import ua.com.aleev.island.entity.organism.Organism;
import java.util.List;

public interface Factory {
    Location createRandomLocation();

    List<Organism> getAllPrototypes();
}
