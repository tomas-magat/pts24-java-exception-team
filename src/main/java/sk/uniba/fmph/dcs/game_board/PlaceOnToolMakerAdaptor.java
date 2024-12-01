package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;

import java.util.Collection;

public class PlaceOnToolMakerAdaptor implements InterfaceFigureLocationInternal{

    private final ToolMakerHutFields toolMaker;
    public PlaceOnToolMakerAdaptor(ToolMakerHutFields toolMaker) {
        this.toolMaker = toolMaker;
    }

    @Override
    public boolean placeFigures(Player player, int figureCount) {

        if(figureCount > 1){
            return false;
        }
        return toolMaker.placeOnToolMaker(player);
    }

    @Override
    public HasAction tryToPlaceFigures(Player player, int count) {

        if(count > 1){
            return HasAction.WAITING_FOR_PLAYER_ACTION;
        }
        return toolMaker.canPlaceOnToolMaker(player)?HasAction.WAITING_FOR_PLAYER_ACTION:HasAction.NO_ACTION_POSSIBLE;
    }

    @Override
    public ActionResult makeAction(Player player, Collection<Effect> inputResources, Collection<Effect> outputResources) {

        return toolMaker.actionToolMaker(player)?ActionResult.ACTION_DONE:ActionResult.FAILURE;
    }

    @Override
    public boolean skipAction(Player player) {

        return false;
    }

    @Override
    public HasAction tryToMakeAction(Player player) {

        return toolMaker.hasActionOnToolMaker(player)?HasAction.WAITING_FOR_PLAYER_ACTION:HasAction.NO_ACTION_POSSIBLE;
    }

    @Override
    public boolean newTurn() {

        toolMaker.newTurn();
        return false;
    }
}
