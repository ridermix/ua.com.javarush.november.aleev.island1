package ua.com.aleev.island.entity.organism.plants;

import ua.com.aleev.island.entity.map.Location;
import ua.com.aleev.island.entity.organism.Limit;
import ua.com.aleev.island.entity.organism.Organism;
import ua.com.aleev.island.property.Setting;

import java.util.HashSet;
import java.util.Map;
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
    public void spawn(Location currentLocation) {
        Location nextLocation = currentLocation.getNextLocations(1);
        safePlantSpawn(nextLocation);
    }

    private void safePlantSpawn(Location currentLocation) {
        currentLocation.getLock().lock();
        try {
            Set<Organism> plants = currentLocation.getResidents().get(this.getType());

            if(plants !=null){
                if (plants.size() < this.getLimit().getMaxCount()) {
                    Organism clone = this.clone();
                    clone.setWeight(this.getLimit().getMaxWeight());
                    plants.add(clone);
                }
            } else {
                Map<String, Set<Organism>> residents = currentLocation.getResidents();
                residents.put(this.getType(), new HashSet<>());
                Set<Organism> organisms = residents.get(getType());
                organisms.add(this);
            }
        } finally {
            currentLocation.getLock().unlock();
        }
    }
}
