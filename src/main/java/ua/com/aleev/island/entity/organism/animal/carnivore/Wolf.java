package ua.com.aleev.island.entity.organism.animal.carnivore;

import ua.com.aleev.island.annotation.OrganismTypeData;
import ua.com.aleev.island.entity.organism.Limit;

@OrganismTypeData(name = "Wolf", icon = "\uD83D\uDC3A", maxWeight = 50, maxCount = 30, maxSpeed = 3, maxFood = 8)
public class Wolf extends Carnivore {
    public Wolf(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }

//    public static Organism birth() {
//        return new Wolf(SettingOld.WOLF_NAME, SettingOld.WOLF_ICON, SettingOld.WOLF_MAX_WEIGHT
//                , new Limit(SettingOld.WOLF_MAX_WEIGHT, SettingOld.WOLF_MAX_COUNT, SettingOld.WOLF_MAX_SPEED, SettingOld.WOLF_MAX_FOOD));
//    }
}
