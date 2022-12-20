package ua.com.aleev.island.entity.organism;

import ua.com.aleev.island.action.Reproducible;
import ua.com.aleev.island.entity.map.Location;
import ua.com.aleev.island.exception.GameException;
import ua.com.aleev.island.property.Setting;
import ua.com.aleev.island.util.Randomizer;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Organism implements Cloneable, Reproducible {

    private final static AtomicLong idCounter = new AtomicLong(System.currentTimeMillis());
    public final String type = this.getClass().getSimpleName();
    private long id = idCounter.incrementAndGet();
    private String name;
    private String icon;
    private double weight;
    private Limit limit;

    public Organism(String name, String icon, double weight, Limit limit) {
        this.name = name;
        this.icon = icon;
        this.weight = weight;
        this.limit = limit;
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



    public Organism clone() {
        try {
            Organism clone = (Organism) super.clone();
            clone.id = idCounter.incrementAndGet();
            clone.weight = Randomizer.random(limit.getMaxWeight() / 2, limit.getMaxWeight());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new GameException("Can't clone " + this);
        }
    }

    protected boolean safeDie(Location currentLocation) {
        currentLocation.getLock().lock();
        try {
            return currentLocation.getResidents().get(type).remove(this);
        } finally {
            currentLocation.getLock().unlock();
        }
    }

    protected boolean safeChangeWeight(Location currentLocation, int percent) {
        currentLocation.getLock().lock();
        try {
            double maxWeight = limit.getMaxWeight();
            weight += maxWeight * percent / 100;
            weight = Math.max(0, weight);
            return currentLocation.getResidents().get(type).contains(this);
        } finally {
            currentLocation.getLock().unlock();
        }
    }

    protected boolean safeMove(Location sourse, Location destination) {
        if (safeAddTo(destination)) {
            if (safePollFrom(sourse)) {
                return true;
            } else {
                safePollFrom(destination);
            }
        }
        return false;
    }

    protected boolean safeAddTo(Location location) {
        location.getLock().lock();
        try {
            Set<Organism> organisms = location.getResidents().get(getType());
            int maxCount = getLimit().getMaxCount();
            int size = organisms.size();
            return size < maxCount && organisms.add(this);
        } finally {
            location.getLock().unlock();
        }
    }

    protected boolean safePollFrom(Location location) {
        location.getLock().lock();
        try {
            Map<String, Set<Organism>> residents = location.getResidents();
            Set<Organism> organisms = residents.get(getType());
            return organisms.remove(this);
        } finally {
            location.getLock().unlock();
        }
    }

    protected boolean safeFindFood(Location currentLocation) {
        currentLocation.getLock().lock();
        boolean foodFound = false;
        try {
            double needFood = getNeedFood();
            if (!(needFood <= 0)) {
                var foodMap = Setting.rationMap.get(getType()).entrySet();
                var iterator = foodMap.iterator();
                while (needFood > 0 && iterator.hasNext()) {
                    Map.Entry<String, Integer> entry = iterator.next();
                    String keyFood = entry.getKey();
                    Integer probably = entry.getValue();
                    Map<String, Set<Organism>> residents = currentLocation.getResidents();
                    var foods = residents.get(keyFood);
                    if (foods != null && !foods.isEmpty() && Randomizer.get(probably)) {
                        for (Iterator<Organism> organismIterator = foods.iterator(); organismIterator.hasNext();) {
                            Organism o = organismIterator.next();
                            double foofWeight = o.getWeight();
                            double delta = Math.min(foofWeight, needFood);
                            setWeight(getWeight() + delta);
                            o.setWeight(foofWeight - delta);
                            if (o.getWeight() <= 0) {
                                organismIterator.remove();
                            }
                            needFood -= delta;
                            foodFound = true;
                            if (needFood <= 0) {
                                break;
                            }
                        }
                    }
                }
            }
        } finally {
            currentLocation.getLock().unlock();
        }
        return foodFound;
    }

    private double getNeedFood() {

        return Math.min(getLimit().getMaxFood(), getLimit().getMaxWeight() - getWeight());
    }

    public static <T extends Organism> T clone(T ordinal) {

        return (T) ordinal.clone();
    }

}
