package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;

import java.util.Collection;

public class PlaceOnFieldsAdaptor implements InterfaceFigureLocationInternal {
    private final ToolMakerHutFields toolMakerHutFields;

    public PlaceOnFieldsAdaptor(ToolMakerHutFields toolMakerHutFields) {
        this.toolMakerHutFields = toolMakerHutFields;
    }

    @Override
    public boolean placeFigures(Player player, int figureCount) {
        if(figureCount != 1) return false;
        return toolMakerHutFields.placeOnFields(player);
    }

    @Override
    public HasAction tryToPlaceFigures(Player player, int count) {
        if(count != 1 || !player.getPlayerBoard().hasFigures(count)) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return toolMakerHutFields.canPlaceOnFields(player) ? HasAction.WAITING_FOR_PLAYER_ACTION : HasAction.NO_ACTION_POSSIBLE;
    }

    @Override
    public ActionResult makeAction(Player player, Collection<Effect> inputResources, Collection<Effect> outputResources) {
        boolean madeAction = toolMakerHutFields.actionFields(player);
        return madeAction ? ActionResult.ACTION_DONE : ActionResult.FAILURE;
    }

    @Override
    public boolean skipAction(Player player) {
        return false;
    }

    @Override
    public HasAction tryToMakeAction(Player player) {
        return toolMakerHutFields.canPlaceOnFields(player) ? HasAction.WAITING_FOR_PLAYER_ACTION : HasAction.NO_ACTION_POSSIBLE;
    }

    @Override
    public boolean newTurn() {
        toolMakerHutFields.newTurn();
        return false;
    }
}
