package ua.com.aleev.island.entity.organism.animal.herbivore;

import ua.com.aleev.island.entity.organism.Limit;
import ua.com.aleev.island.entity.organism.animal.Animal;

public abstract class Herbivore extends Animal {
    public Herbivore(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }
}
