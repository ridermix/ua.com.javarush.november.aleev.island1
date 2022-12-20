package ua.com.aleev.island.util;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
    public static int random(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }

    public static double random(double from, double to) {
        return ThreadLocalRandom.current().nextDouble(from, to);
    }

    public static int random(int maxValue) {
        return ThreadLocalRandom.current().nextInt(maxValue + 1);
    }

    public static boolean get(int limit) {
        return random(0, 100) < limit;
    }
}
