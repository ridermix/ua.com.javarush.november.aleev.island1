package ua.com.aleev.island.entity.organism.plants;

import ua.com.aleev.island.entity.map.Location;
import ua.com.aleev.island.entity.organism.Limit;
import ua.com.aleev.island.entity.organism.Organism;
import ua.com.aleev.island.property.Setting;
import ua.com.aleev.island.util.Randomizer;

import java.util.Set;

public class Plant extends Organism {

    public Plant(String name, String icon, double weight, Limit limit) {
        super(name, icon, weight, limit);
    }

    public static Organism birth() {
        return new Plant(Setting.PLANT_NAME, Setting.PLANT_ICON, Setting.PLANT_MAX_WEIGHT
                , new Limit(Setting.PLANT_MAX_WEIGHT, Setting.PLANT_MAX_COUNT, Setting.PLANT_MAX_SPEED, Setting.PLANT_MAX_FOOD));
    }

    @Override
    public boolean spawn(Location location) {
        this.safeChangeWeight(location, Setting.PERCENT_PLANT_GROW);
        boolean born = false;
        for (int i = 0; i < 6; i++) {
            Location neighborLocation = location.getNextLocations(Randomizer.random(0, 2));
            born |= safePlantSpawn(neighborLocation);
        }
        return born;
    }

    private boolean safePlantSpawn(Location currentLocation) {
        Limit limit = getLimit();
        currentLocation.getLock().lock();
        try {
            Set<Organism> plants = currentLocation.getResidents().get(this.getType());

                if (plants.size() < limit.getMaxCount()) {
                    Organism newPlant = Organism.clone(this);
                    double childWeight = getWeight() / 10;
                    newPlant.setWeight(childWeight);
                    return plants.add(newPlant);
                }
        } finally {
            currentLocation.getLock().unlock();
        }
        return false;
    }
}
