package ua.com.aleev.island.entity.organism.animal.herbivore;

import ua.com.aleev.island.annotation.OrganismTypeData;
import ua.com.aleev.island.entity.organism.Limit;

@OrganismTypeData(name = "Duck", icon = "\uD83E\uDD86", maxWeight = 1, maxCount = 200, maxSpeed = 4, maxFood = 0.15)
public class Duck extends Herbivore {
    public Duck(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }

//    public static Organism birth() {
//        return new Duck(SettingOld.DUCK_NAME, SettingOld.DUCK_ICON, SettingOld.DUCK_MAX_WEIGHT
//                , new Limit(SettingOld.DUCK_MAX_WEIGHT, SettingOld.DUCK_MAX_COUNT, SettingOld.DUCK_MAX_SPEED, SettingOld.DUCK_MAX_FOOD));
//    }
}
