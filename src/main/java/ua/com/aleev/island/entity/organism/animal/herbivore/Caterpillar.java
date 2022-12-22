package ua.com.aleev.island.entity.organism.animal.herbivore;

import ua.com.aleev.island.annotation.OrganismTypeData;
import ua.com.aleev.island.entity.organism.Limit;

@OrganismTypeData(name = "Caterpillar", icon = "\uD83D\uDC1B", maxWeight = 0.01, maxCount = 1000, maxSpeed = 0, maxFood = 0)
public class Caterpillar extends Herbivore {
    public Caterpillar(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }

//    public static Organism birth() {
//        return new Caterpillar(SettingOld.CATERPILLAR_NAME, SettingOld.CATERPILLAR_ICON, SettingOld.CATERPILLAR_MAX_WEIGHT
//                , new Limit(SettingOld.CATERPILLAR_MAX_WEIGHT, SettingOld.CATERPILLAR_MAX_COUNT, SettingOld.CATERPILLAR_MAX_SPEED, SettingOld.CATERPILLAR_MAX_FOOD));
//    }
}
