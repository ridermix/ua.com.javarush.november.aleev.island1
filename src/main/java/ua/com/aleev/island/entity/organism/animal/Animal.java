package ua.com.aleev.island.entity.organism.animal;

import ua.com.aleev.island.action.Eating;
import ua.com.aleev.island.action.Movable;
import ua.com.aleev.island.entity.map.Location;
import ua.com.aleev.island.entity.organism.Limit;
import ua.com.aleev.island.entity.organism.Organism;

import java.util.Set;

public abstract class Animal extends Organism implements Movable, Eating {


    public Animal(String name, String icon, double weight, Limit limit) {
        super(name, icon, weight, limit);
    }

   @Override
    public boolean eat(Location currentLocation){
        if(safeFindFood(currentLocation)){
            return true;
        }
        if(getWeight() > 0 ){
            return safeChangeWeight(currentLocation, -5);
        }
        return !safeDie(currentLocation);
   }

   @Override
    public boolean move(Location startLocation){
        int countStep = this.getLimit().getMaxSpeed();
        Location desinationLocation = startLocation.getNextLocations(countStep);
        return safeMove(startLocation,desinationLocation);
   }
   @Override
    public boolean spawn(Location location){

        return safeSpawnAnimal(location);
   }

   private boolean safeSpawnAnimal(Location location){
        location.getLock().lock();
        try {
            Set<Organism> organisms = location.getResidents().get(getType());
            double maxWeight = getLimit().getMaxWeight();
            if(getWeight() > maxWeight / 2
                    && organisms.contains(this)
                    && organisms.size() >=2
                    && organisms.size() < getLimit().getMaxCount()){
                double childWeight = getWeight() / 2;
                setWeight(childWeight / 2);
                Organism clone = Organism.clone(this);
                clone.setWeight(childWeight);
                organisms.add(clone);
                return true;
            }
        } finally {
            location.getLock().unlock();
        }
        return false;
   }

}
