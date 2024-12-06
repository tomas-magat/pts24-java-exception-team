package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;

public class PlaceOnToolMakerAdaptor implements InterfaceFigureLocationInternal {
    private final ToolMakerHutFields toolMakerHutFields;

    public PlaceOnToolMakerAdaptor(ToolMakerHutFields toolMakerHutFields) {
        this.toolMakerHutFields = toolMakerHutFields;
    }

    @Override
    public boolean placeFigures(Player player, int figureCount) {
        if (figureCount != 1) return false;
        return toolMakerHutFields.placeOnToolMaker(player);
    }

    @Override
    public HasAction tryToPlaceFigures(Player player, int count) {
        if (count < 1 || !player.getPlayerBoard().hasFigures(count)) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return toolMakerHutFields.canPlaceOnToolMaker(player) ? HasAction.WAITING_FOR_PLAYER_ACTION : HasAction.NO_ACTION_POSSIBLE;
    }


    @Override
    public ActionResult makeAction(Player player, Collection<Effect> inputResources, Collection<Effect> outputResources) {
        boolean success = toolMakerHutFields.actionToolMaker(player);
        return success ? ActionResult.ACTION_DONE : ActionResult.FAILURE;
    }

    @Override
    public boolean skipAction(Player player) {
        return false;
    }

    @Override
    public HasAction tryToMakeAction(Player player) {
        return toolMakerHutFields.canPlaceOnToolMaker(player) ? HasAction.WAITING_FOR_PLAYER_ACTION : HasAction.NO_ACTION_POSSIBLE;
    }

    @Override
    public boolean newTurn() {
        toolMakerHutFields.newTurn();
        return false;
    }
}
