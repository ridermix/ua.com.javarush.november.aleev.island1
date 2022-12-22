package ua.com.aleev.island.entity.organism.animal.herbivore;

import ua.com.aleev.island.annotation.OrganismTypeData;
import ua.com.aleev.island.entity.organism.Limit;

@OrganismTypeData(name = "Horse", icon = "\uD83D\uDC0E", maxWeight = 400, maxCount = 20, maxSpeed = 4, maxFood = 60)
public class Horse extends Herbivore {
    public Horse(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }

//    public static Organism birth() {
//        return new Horse(SettingOld.HORSE_NAME, SettingOld.HORSE_ICON, SettingOld.HORSE_MAX_WEIGHT
//                , new Limit(SettingOld.HORSE_MAX_WEIGHT, SettingOld.HORSE_MAX_COUNT, SettingOld.HORSE_MAX_SPEED, SettingOld.HORSE_MAX_FOOD));
//    }
}
