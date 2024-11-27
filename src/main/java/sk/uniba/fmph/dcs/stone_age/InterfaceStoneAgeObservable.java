package sk.uniba.fmph.dcs.stone_age;

public interface InterfaceStoneAgeObservable {
    void registerObserver(int playerId, InterfaceStoneAgeObserver observer);
}
