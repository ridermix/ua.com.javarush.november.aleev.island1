package ua.com.aleev.island.entity.organism.animal.carnivore;

import ua.com.aleev.island.entity.organism.Limit;
import ua.com.aleev.island.entity.organism.Organism;
import ua.com.aleev.island.property.Setting;

public class Wolf extends Carnivore {
    public Wolf(String name, String icon, double weight, Limit limit) {
        super(name, icon, weight, limit);
    }

    public static Organism birth() {
        return new Wolf(Setting.WOLF_NAME, Setting.WOLF_ICON, Setting.WOLF_MAX_WEIGHT
                , new Limit(Setting.WOLF_MAX_WEIGHT, Setting.WOLF_MAX_COUNT, Setting.WOLF_MAX_SPEED, Setting.WOLF_MAX_FOOD));
    }
}
