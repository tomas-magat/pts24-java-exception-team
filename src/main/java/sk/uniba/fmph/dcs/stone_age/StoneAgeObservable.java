package sk.uniba.fmph.dcs.stone_age;

import java.util.HashMap;
import java.util.Map;


public class StoneAgeObservable implements InterfaceStoneAgeObservable {
    private final Map<Integer, InterfaceStoneAgeObserver> observers = new HashMap<>();

    @Override
    public void registerObserver(int playerId, InterfaceStoneAgeObserver observer) {
        observers.putIfAbsent(playerId, gameState -> {
            System.out.println("Game state has changed:");
            System.out.println(gameState);
        });
    }

    public void notify(String gameState) {
        for(InterfaceStoneAgeObserver observer: observers.values()) {
            observer.update(gameState);
        }
    }
}
