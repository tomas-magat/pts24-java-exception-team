package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.List;

public interface InterfacePlayerResourcesAndFood {
    boolean hasResources(List<Effect> resources);
    boolean takeResources(List<Effect> resources);
    boolean giveResources(List<Effect> resources);
    int numberOfResourcesForFinalPoints();
    String state();
}
