package ua.com.aleev.island.action;

import ua.com.aleev.island.entity.map.Location;

public interface Movable {
   boolean move(Location startLocation);
}
