package ua.com.aleev.island.factory;

import ua.com.aleev.island.entity.map.Location;
import ua.com.aleev.island.entity.organism.Organism;
import ua.com.aleev.island.property.Setting;
import ua.com.aleev.island.util.Randomizer;

import java.util.*;

import static ua.com.aleev.island.property.Setting.PROTOTYPES;

public class EntityFactory implements Factory {

//    private static final Organism[] PROTOTYPES = OrganismCreator.createPrototype(SettingOld.TYPES);

    public EntityFactory() {
    }

    @Override
    public Location createRandomLocation() {
        Map<String, Set<Organism>> residents = new HashMap<>();
        boolean fill = Randomizer.get(50);
        if (fill) {
            for (Organism prototype : Setting.PROTOTYPES) { //(Organism prototype : PROTOTYPES)
                String type = prototype.getType();
                boolean born = Randomizer.get(50);
                if (born) {
                    residents.putIfAbsent(type, new HashSet<>());
                    Set<Organism> organisms = residents.get(prototype.getType());
                    int currentCount = organisms.size();
                    int max = prototype.getLimit().getMaxCount() - currentCount;
                    int count = Randomizer.random(0, max);
                    for (int i = 0; i < count; i++) {
                        organisms.add(prototype.clone());
                    }
                }
            }
        }
        Location location = new Location(residents);
        location.setNextLocation(new ArrayList<>());
        return location;
    }

    @Override
    public List<Organism> getAllPrototypes() {
        return Arrays.asList(PROTOTYPES);
    }
}
