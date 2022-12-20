package ua.com.aleev.island.entity.organism.animal.carnivore;

import ua.com.aleev.island.entity.organism.Limit;
import ua.com.aleev.island.entity.organism.animal.Animal;

public abstract class Carnivore extends Animal {
    public Carnivore(String name, String icon, double weight, Limit limit) {
        super(name, icon, weight, limit);
    }
}
