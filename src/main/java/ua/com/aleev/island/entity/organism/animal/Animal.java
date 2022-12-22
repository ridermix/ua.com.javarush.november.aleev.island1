package ua.com.aleev.island.entity.organism.animal;

import ua.com.aleev.island.action.Eating;
import ua.com.aleev.island.action.Movable;
import ua.com.aleev.island.entity.map.Location;
import ua.com.aleev.island.entity.organism.Limit;
import ua.com.aleev.island.entity.organism.Organism;
import ua.com.aleev.island.property.Setting;
import ua.com.aleev.island.util.Randomizer;

import java.util.*;

public abstract class Animal extends Organism implements Movable, Eating {


    public Animal(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }

    @Override
    public Location move(Location startLocation) {
        int countStep = this.getLimit().getMaxSpeed();
        Location destinationLocation = startLocation.getNextLocations(countStep);
        removeMe(startLocation);
        addMe(destinationLocation);
        return destinationLocation;
    }

    private void addMe(Location location) {
        location.getLock().lock();
        try {
            Set<Organism> set = location.getResidents().get(getType());
            if (Objects.nonNull(set)) {
                if(set.size() < getLimit().getMaxCount()){
                    set.add(this);
                }
            } else {
                Map<String, Set<Organism>> residents = location.getResidents();
                residents.put(this.getType(), new HashSet<>());
                Set<Organism> organisms = residents.get(getType());
                organisms.add(this);
            }

        } finally {
            location.getLock().unlock();
        }
    }

    private void removeMe(Location location) {
        location.getLock().lock();
        try {
            location.getResidents().get(getType()).remove(this);
        } finally {
            location.getLock().unlock();
        }
    }

    @Override
    public void spawn(Location currentLocation) {
        safeSpawnAnimal(currentLocation);
    }

    private void safeSpawnAnimal(Location currentLocation) {
        currentLocation.getLock().lock();
        try {
            Set<Organism> organisms = currentLocation.getResidents().get(getType());
            double maxWeight = getLimit().getMaxWeight();
            if (this.getWeight() > maxWeight / 2 &&
                    organisms.contains(this) &&
                    organisms.size() >= 2 &&
                    organisms.size() < getLimit().getMaxCount()) {
                double childWeight = getLimit().getMaxWeight() / 2;
                this.setWeight(this.getWeight() - childWeight);
                Organism clone = this.clone();
                clone.setWeight(childWeight);
                organisms.add(clone);
            }
        } finally {
            currentLocation.getLock().unlock();
        }
    }

    @Override
    public void eat(Location currentLocation) {
        if (safeFindFood(currentLocation)) {
        } else if (getWeight() > 0) {
            safeChangeWeight(currentLocation, Setting.getSetting().getPercentAnimalSlim());
        } else {
            safeDie(currentLocation);
        }
    }

    protected boolean safeFindFood(Location currentLocation) {
        currentLocation.getLock().lock();
        try {
            double needFood = getNeedFood();
            if (!(needFood <= 0)) {
                Setting setting = Setting.getSetting();
                var foodMap = Setting.rationMap.get(getType()).entrySet();

                var iterator = foodMap.iterator();
                while (needFood > 0 && iterator.hasNext()) {
                    Map.Entry<String,Integer> entry = iterator.next();
                    String keyFood = entry.getKey();
                    Integer probably = entry.getValue();
                    var foods = currentLocation.getResidents().get(keyFood);
                    if(foods !=null) {
                        if (foods.size() > 0 && probably > Randomizer.random(0, 100)) {
                            for (Iterator<Organism> organismIterator = foods.iterator(); organismIterator.hasNext(); ) {
                                Organism o = organismIterator.next();
                                double foodWeight = o.getWeight();
                                double delta = Math.min(foodWeight, needFood);
                                double weight = getWeight();
                                setWeight(Math.min(weight + delta, getLimit().getMaxWeight()));
                                o.setWeight(foodWeight - delta);
                                if (o.getWeight() <= 0) {
                                    organismIterator.remove();
                                }
                                needFood -= delta;
                                if (needFood <= 0) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else {
                return false;
            }
        } finally {
            currentLocation.getLock().unlock();
        }
        return false;
    }

    private double getNeedFood() {
        return Math.min(
                getLimit().getMaxFood(),
                getLimit().getMaxWeight() - getWeight());
    }

    protected void safeChangeWeight(Location currentLocation, int percent) {
        currentLocation.getLock().lock();
        double weight = this.getWeight();
        try {
            double maxWeight = getLimit().getMaxWeight();
            weight += maxWeight * percent / 100;
            weight = Math.max(0, weight);
            this.setWeight(Math.min(weight, maxWeight));
            currentLocation.getResidents().get(this.getClass().getSimpleName());
        } finally {
            currentLocation.getLock().unlock();
        }
    }

}
