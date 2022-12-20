package ua.com.aleev.island.factory;

import ua.com.aleev.island.entity.organism.Organism;
import ua.com.aleev.island.exception.GameException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OrganismCreator {
    public OrganismCreator() {
    }

    public static Organism[] createPrototype(Class<?>[] TYPES) {
        Organism[] organisms = new Organism[TYPES.length];
        int index = 0;
        for (Class<?> type : TYPES) {
            try {
                Method getBirth = type.getDeclaredMethod("birth");
                organisms[index++] = (Organism) getBirth.invoke(type);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new GameException("not found Entity constructor ", e);
            }

        }
        return organisms;
    }
}
