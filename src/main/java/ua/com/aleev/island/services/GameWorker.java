package ua.com.aleev.island.services;

import ua.com.aleev.island.entity.Game;
import ua.com.aleev.island.exception.GameException;
import ua.com.aleev.island.property.Setting;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GameWorker extends Thread {
    private final Game game;

    public GameWorker(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        //TO DO ScheduledTreadPool
        ScheduledExecutorService mainPool = Executors.newScheduledThreadPool(4);

        List<OrganismWorker> workers = game.getEntityFactory().getAllPrototypes()
                .stream()
                .map(o -> new OrganismWorker(o, game.getGameMap())).collect(Collectors.toList());
        mainPool.scheduleAtFixedRate(()->{
            ExecutorService servicePool = Executors.newFixedThreadPool(4);
            workers.forEach(servicePool::submit);
            servicePool.shutdown();
            try {
                if(servicePool.awaitTermination(Setting.PERIOD,TimeUnit.MILLISECONDS)){
                    game.getView().showGeneralStatistics();
                    game.getView().showStatistics();
                }
            } catch (InterruptedException e) {
                throw new GameException(e);
            }
        }, Setting.PERIOD,Setting.PERIOD, TimeUnit.MILLISECONDS);


    }
}
