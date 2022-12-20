package ua.com.aleev.island.property;

import ua.com.aleev.island.entity.organism.animal.carnivore.Wolf;
import ua.com.aleev.island.entity.organism.animal.herbivore.Caterpillar;
import ua.com.aleev.island.entity.organism.animal.herbivore.Duck;
import ua.com.aleev.island.entity.organism.animal.herbivore.Horse;
import ua.com.aleev.island.entity.organism.plants.Plant;

import java.util.LinkedHashMap;
import java.util.Map;

public class Setting {

    //General setting
    //Island setting
    public static final int MAP_ROWS = 2;
    public static final int MAP_COLS = 2;

    public static final int PERIOD = 500;

    //Organisms settings
    public static final Class<?>[] TYPES = {Wolf.class, Horse.class, Caterpillar.class, Duck.class, Plant.class};

    public static final int PERCENT_ANIMAL_SLIM = 5;
    public static final int PERCENT_PLANT_GROW = 15;

    //Wolf setting
    public static final String WOLF_NAME = "Wolf";
    public static final String WOLF_ICON = "\uD83D\uDC3A";
    public static final double WOLF_MAX_WEIGHT = 50;
    public static final int WOLF_MAX_COUNT = 30;
    public static final int WOLF_MAX_SPEED = 3;
    public static final int WOLF_MAX_FOOD = 8;

    //Horse settings
    public static final String HORSE_NAME = "Horse";
    public static final String HORSE_ICON = "\uD83D\uDC0E";
    public static final double HORSE_MAX_WEIGHT = 400;
    public static final int HORSE_MAX_COUNT = 20;
    public static final int HORSE_MAX_SPEED = 4;
    public static final int HORSE_MAX_FOOD = 60;

    //Duck settings
    public static final String DUCK_NAME = "Duck";
    public static final String DUCK_ICON = "\uD83E\uDD86";
    public static final double DUCK_MAX_WEIGHT = 1;
    public static final int DUCK_MAX_COUNT = 200;
    public static final int DUCK_MAX_SPEED = 4;
    public static final double DUCK_MAX_FOOD = 0.15;

    //Caterpillar settings
    public static final String CATERPILLAR_NAME = "Caterpillar";
    public static final String CATERPILLAR_ICON = "\uD83D\uDC1B";
    public static final double CATERPILLAR_MAX_WEIGHT = 0.01;
    public static final int CATERPILLAR_MAX_COUNT = 1000;
    public static final int CATERPILLAR_MAX_SPEED = 0;
    public static final double CATERPILLAR_MAX_FOOD = 0;

    //Plant settings
    public static final String PLANT_NAME = "Plant";
    public static final String PLANT_ICON = "\uD83C\uDF3F";
    public static final double PLANT_MAX_WEIGHT = 1;
    public static final int PLANT_MAX_COUNT = 2000;
    public static final int PLANT_MAX_SPEED = 0;
    public static final int PLANT_MAX_FOOD = 0;


    // fill map with chance to be eaten
    public static final Map<String, Map<String, Integer>> rationMap = new LinkedHashMap<>();

    static {
        for (int i = 0, n = AnnimalsRation.names.length; i < n; i++) {
            String animalKey = AnnimalsRation.names[i];
            rationMap.putIfAbsent(animalKey, new LinkedHashMap<>());
            for (int j = 0; j < n; j++) {
                int ration = AnnimalsRation.setProbablyTable[i][j];
                if (ration > 0) {
                    rationMap.get(animalKey).put(AnnimalsRation.names[j], ration);
                }
            }
        }
    }
}
