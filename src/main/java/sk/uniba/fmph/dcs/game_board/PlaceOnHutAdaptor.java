package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;

public class PlaceOnHutAdaptor implements InterfaceFigureLocationInternal {
    private ToolMakerHutFields toolMakerHutFields;

    public PlaceOnHutAdaptor(ToolMakerHutFields toolMakerHutFields) {
        this.toolMakerHutFields = toolMakerHutFields;
    }

    @Override
    public boolean placeFigures(Player player, int figureCount) {
        if (figureCount != 2) return false;
        return toolMakerHutFields.placeOnHut(player);
    }

    @Override
    public HasAction tryToPlaceFigures(Player player, int count) {
        if (count != 2 || !player.getPlayerBoard().hasFigures(count)) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return toolMakerHutFields.canPlaceOnHut(player) ? HasAction.WAITING_FOR_PLAYER_ACTION : HasAction.NO_ACTION_POSSIBLE;
    }

    @Override
    public ActionResult makeAction(Player player, Collection<Effect> inputResources, Collection<Effect> outputResources) {
        boolean success = toolMakerHutFields.actionHut(player);
        return success ? ActionResult.ACTION_DONE : ActionResult.FAILURE;
    }

    @Override
    public boolean skipAction(Player player) {
        return false;
    }

    @Override
    public HasAction tryToMakeAction(Player player) {
        return toolMakerHutFields.canPlaceOnHut(player) ? HasAction.WAITING_FOR_PLAYER_ACTION : HasAction.NO_ACTION_POSSIBLE;
    }

    @Override
    public boolean newTurn() {
        toolMakerHutFields.newTurn();
        return false;
    }
}
//tu uz musi byt 2 pre dve figurky lebo tu uz treba 2 nie ako predosle 1