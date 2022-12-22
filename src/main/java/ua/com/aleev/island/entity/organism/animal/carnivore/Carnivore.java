package ua.com.aleev.island.entity.organism.animal.carnivore;

import ua.com.aleev.island.entity.organism.Limit;
import ua.com.aleev.island.entity.organism.animal.Animal;

public abstract class Carnivore extends Animal {
    public Carnivore(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }
}
