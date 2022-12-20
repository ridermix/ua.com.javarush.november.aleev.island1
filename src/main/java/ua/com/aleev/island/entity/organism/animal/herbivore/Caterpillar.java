package ua.com.aleev.island.entity.organism.animal.herbivore;

import ua.com.aleev.island.entity.organism.Limit;
import ua.com.aleev.island.entity.organism.Organism;
import ua.com.aleev.island.property.Setting;

public class Caterpillar extends Herbivore {
    public Caterpillar(String name, String icon, double weight, Limit limit) {
        super(name, icon, weight, limit);
    }

    public static Organism birth() {
        return new Caterpillar(Setting.CATERPILLAR_NAME, Setting.CATERPILLAR_ICON, Setting.CATERPILLAR_MAX_WEIGHT
                , new Limit(Setting.CATERPILLAR_MAX_WEIGHT, Setting.CATERPILLAR_MAX_COUNT, Setting.CATERPILLAR_MAX_SPEED, Setting.CATERPILLAR_MAX_FOOD));
    }
}
