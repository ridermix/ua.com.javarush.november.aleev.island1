package ua.com.aleev.island.services;

import ua.com.aleev.island.entity.organism.Organism;

import java.util.function.Consumer;

public class Task {
    private final Organism organism;
    private final Consumer<Organism> operation;


    public Task(Organism organism, Consumer<Organism> operation) {
        this.organism = organism;
        this.operation = operation;
    }

    public void run(){
        operation.accept(organism);
    }
}
