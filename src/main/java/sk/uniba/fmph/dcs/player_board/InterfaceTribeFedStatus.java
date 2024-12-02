package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.List;

public interface InterfaceTribeFedStatus {
    void addField();
    int getFields();
    void newTurn();
    boolean feedTribeIfEnoughFood();
    boolean feedTribe(List<Effect> resources);
    boolean setTribeFed();
    boolean isTribeFed();
    String state();

}
