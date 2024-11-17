package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;

import java.util.Collection;

public class BuildingTile implements InterfaceFigureLocationInternal {
    @Override
    public boolean placeFigures(Player player, int figureCount) {
        // TODO
        return false;
    }

    @Override
    public HasAction tryToPlaceFigures(Player player, int count) {
        // TODO
        return null;
    }

    @Override
    public ActionResult makeAction(Player player, Collection<Effect> inputResources, Collection<Effect> outputResources) {
        // TODO
        return null;
    }

    @Override
    public boolean skipAction(Player player) {
        // TODO
        return false;
    }

    @Override
    public HasAction tryToMakeAction(Player player) {
        // TODO
        return null;
    }

    @Override
    public boolean newTurn() {
        // TODO
        return false;
    }
}
