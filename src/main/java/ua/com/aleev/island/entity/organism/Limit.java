package ua.com.aleev.island.entity.organism;

public class Limit {
    private final double maxWeight;
    private final int maxCount;
    private final int maxSpeed;
    private final double maxFood;

    public Limit(double maxWeight, int maxCount, int maxSpeed, double maxFood) {
        this.maxWeight = maxWeight;
        this.maxCount = maxCount;
        this.maxSpeed = maxSpeed;
        this.maxFood = maxFood;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public double getMaxFood() {
        return maxFood;
    }
}
