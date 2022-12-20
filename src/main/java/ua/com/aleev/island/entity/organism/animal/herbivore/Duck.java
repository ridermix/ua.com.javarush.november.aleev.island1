package ua.com.aleev.island.entity.organism.animal.herbivore;

import ua.com.aleev.island.entity.organism.Limit;
import ua.com.aleev.island.entity.organism.Organism;
import ua.com.aleev.island.property.Setting;

public class Duck extends Herbivore {
    public Duck(String name, String icon, double weight, Limit limit) {
        super(name, icon, weight, limit);
    }

    public static Organism birth() {
        return new Duck(Setting.DUCK_NAME, Setting.DUCK_ICON, Setting.DUCK_MAX_WEIGHT
                , new Limit(Setting.DUCK_MAX_WEIGHT, Setting.DUCK_MAX_COUNT, Setting.DUCK_MAX_SPEED, Setting.DUCK_MAX_FOOD));
    }
}
