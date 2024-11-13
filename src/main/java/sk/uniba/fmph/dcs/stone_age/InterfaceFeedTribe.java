package sk.uniba.fmph.dcs.stone_age;

public interface InterfaceFeedTribe {
    boolean feedTribeIfEnoughFood();

    boolean feedTribe(Effect[] resources);

    boolean doNotFeedThisTurn();

    boolean isTribeFood();
}
