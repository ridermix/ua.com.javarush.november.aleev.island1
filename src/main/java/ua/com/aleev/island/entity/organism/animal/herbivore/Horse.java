package ua.com.aleev.island.entity.organism.animal.herbivore;

import ua.com.aleev.island.entity.organism.Limit;
import ua.com.aleev.island.entity.organism.Organism;
import ua.com.aleev.island.property.Setting;

public class Horse extends Herbivore {
    public Horse(String name, String icon, double weight, Limit limit) {
        super(name, icon, weight, limit);
    }

    public static Organism birth() {
        return new Horse(Setting.HORSE_NAME, Setting.HORSE_ICON, Setting.HORSE_MAX_WEIGHT
                , new Limit(Setting.HORSE_MAX_WEIGHT, Setting.HORSE_MAX_COUNT, Setting.HORSE_MAX_SPEED, Setting.HORSE_MAX_FOOD));
    }
}
