package ua.com.aleev.island.entity.organism;

import ua.com.aleev.island.action.Reproducible;
import ua.com.aleev.island.entity.map.Location;
import ua.com.aleev.island.util.Randomizer;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Organism implements Cloneable, Reproducible {

    private final static AtomicLong idCounter = new AtomicLong(System.currentTimeMillis());
    public final String type = this.getClass().getSimpleName();
    private long id = idCounter.incrementAndGet();
    private String name;
    private String icon;
    private double weight;
    private Limit limit;

    public Organism(String name, String icon, Limit limit) {
        this.name = name;
        this.icon = icon;
       // this.weight = weight;
        this.limit = limit;
        weight = Randomizer.random(limit.getMaxWeight()/2,limit.getMaxWeight());
    }

    public long getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public String getType() {
        return type;
    }



    @Override
    public Organism clone() {
        try {
            Organism clone = (Organism) super.clone();
            clone.id = idCounter.incrementAndGet();
            clone.weight = Randomizer.random(limit.getMaxWeight() / 2, limit.getMaxWeight());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("cannot clone " + this);
        }
    }

    public void safeDie(Location target) {
        target.getLock().lock();
        try {
            target.getResidents().get(type).remove(this);
        } finally {
            target.getLock().unlock();
        }
    }

}
