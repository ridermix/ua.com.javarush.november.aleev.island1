package ua.com.aleev.island.property;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ua.com.aleev.island.entity.organism.Organism;
import ua.com.aleev.island.entity.organism.animal.carnivore.Wolf;
import ua.com.aleev.island.entity.organism.animal.herbivore.Caterpillar;
import ua.com.aleev.island.entity.organism.animal.herbivore.Duck;
import ua.com.aleev.island.entity.organism.animal.herbivore.Horse;
import ua.com.aleev.island.entity.organism.plants.Plant;
import ua.com.aleev.island.exception.GameException;
import ua.com.aleev.island.factory.OrganismCreator;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Setting {
    public static final String SETTING_YAML = "src/main/resources/config.yaml";
    private static final Class<?>[] TYPES = {Wolf.class, Horse.class, Caterpillar.class, Duck.class, Plant.class};
    public static final Organism[] PROTOTYPES = OrganismCreator.createPrototype(TYPES);

    private static volatile Setting SETTING;

    public static Setting getSetting() {
        Setting setting = SETTING;
        if (Objects.isNull(setting)) {
            synchronized (Setting.class) {
                if (Objects.isNull(setting = SETTING)) {
                    setting = SETTING = new Setting();
                }
            }
        }
        return setting;
    }

    //DATA
    private int rows;
    private int cols;
    private int period;
    private int percentAnimalSlim;

    public static final Map<String, Map<String, Integer>> rationMap = new LinkedHashMap<>();

    static {
        for (int i = 0, n = DefaultSettings.names.length; i < n; i++) {
            String animalKey = DefaultSettings.names[i];
            rationMap.putIfAbsent(animalKey, new LinkedHashMap<>());
            for (int j = 0; j < n; j++) {
                int ration = DefaultSettings.setProbablyTable[i][j];
                if (ration > 0) {
                    rationMap.get(animalKey).put(DefaultSettings.names[j], ration);
                }
            }
        }
    }
//    public Map<String, Map<String, Integer>> getRationMap(String keyName){
//        this.rationMap.putIfAbsent(keyName,new LinkedHashMap<>());
//        return rationMap.get(keyName);
//    }


    // INIT
    private Setting() {
        loadFromDefaultSetting();
        updateFromYaml();
    }

    private void loadFromDefaultSetting() {
        rows = DefaultSettings.ROWS;
        cols = DefaultSettings.COLS;
        period = DefaultSettings.PERIOD;
        percentAnimalSlim = DefaultSettings.PERCENT_ANIMAL_SLIM;
        for (int i = 0, n = DefaultSettings.names.length; i < n; i++) {
            String key = DefaultSettings.names[i];
            this.rationMap.putIfAbsent(key, new LinkedHashMap<>());
            for (int j = 0; j < n; j++) {
                int ratio = DefaultSettings.setProbablyTable[i][j];
                if (ratio > 0) {
                    this.rationMap.get(key).put(DefaultSettings.names[j], ratio);
                }
            }
        }
    }

    private void updateFromYaml() {
        URL resourse = Setting.class.getClassLoader().getResource(SETTING_YAML);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        ObjectReader readerForUpdating = objectMapper.readerForUpdating(this);


        if (resourse != null) {
            try {
                readerForUpdating.readValue(resourse.openStream());
            } catch (IOException e) {
                throw new GameException("Yaml file with settings not found");
            }
        }
    }

    @Override
    public String toString() {
        ObjectMapper yaml = new ObjectMapper(new YAMLFactory());
        yaml.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return yaml.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int colls) {
        this.cols = colls;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPercentAnimalSlim() {
        return percentAnimalSlim;
    }

    public void setPercentAnimalSlim(int percentAnimalSlim) {
        this.percentAnimalSlim = percentAnimalSlim;
    }


}
