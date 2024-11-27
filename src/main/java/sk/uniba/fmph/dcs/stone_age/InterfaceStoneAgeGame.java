package sk.uniba.fmph.dcs.stone_age;

public interface InterfaceStoneAgeGame {
    boolean placeFigures(int playerId, Location location, int figuresCount);
    boolean makeAction(int playerId, Location location, Effect[] usedResources, Effect[] desiredResources);
    boolean skipAction(int playerId, Location location); // only if resources are required
    boolean useTools(int playerId, int toolIndex); // affects last action
    boolean noMoreToolsThisThrow(int playerId);
    boolean feedTribe(int playerId, Effect[] resources);
    boolean doNotFeedThisTurn(int playerId);
    boolean makeAllPlayersTakeARewardChoice(int playerId, Effect reward);
}
