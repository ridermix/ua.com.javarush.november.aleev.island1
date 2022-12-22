package ua.com.aleev.island.entity.organism.plants;

import ua.com.aleev.island.annotation.OrganismTypeData;
import ua.com.aleev.island.entity.map.Location;
import ua.com.aleev.island.entity.organism.Limit;
import ua.com.aleev.island.entity.organism.Organism;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@OrganismTypeData(name = "Plant", icon = "\uD83C\uDF3F", maxWeight = 1, maxCount = 200, maxSpeed = 0, maxFood = 0)
public class Plant extends Organism {

    public Plant(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }

//    public static Organism birth() {
//        return new Plant(SettingOld.PLANT_NAME, SettingOld.PLANT_ICON, SettingOld.PLANT_MAX_WEIGHT
//                , new Limit(SettingOld.PLANT_MAX_WEIGHT, SettingOld.PLANT_MAX_COUNT, SettingOld.PLANT_MAX_SPEED, SettingOld.PLANT_MAX_FOOD));
//    }

    @Override
    public void spawn(Location currentLocation) {
        Location nextLocation = currentLocation.getNextLocations(1);
        safePlantSpawn(nextLocation);
    }

    private void safePlantSpawn(Location currentLocation) {
        currentLocation.getLock().lock();
        try {
            Set<Organism> plants = currentLocation.getResidents().get(this.getType());

            if (plants != null) {
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
