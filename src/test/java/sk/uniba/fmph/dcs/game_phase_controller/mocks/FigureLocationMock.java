package sk.uniba.fmph.dcs.game_phase_controller.mocks;

import sk.uniba.fmph.dcs.stone_age.*;

public class FigureLocationMock implements InterfaceFigureLocation {

    public boolean expectedPlaceFigures;
    public HasAction expectedTryToPlaceFigures;
    public ActionResult expectedMakeAction;
    public boolean expectedSkipAction;
    public HasAction expectedTryToMakeAction;
    public boolean expectedNewTurn;
    public int maxFigures = Integer.MAX_VALUE;
    public int maxActions = Integer.MAX_VALUE;
    public PlayerOrder targetPlayer = new PlayerOrder(0,1);
    public boolean limitOnlyTargetPlayer = false;

    @Override
    public boolean placeFigures(PlayerOrder player, int figureCount) {

        if(!expectedPlaceFigures || maxFigures - figureCount < 0) {

            return false;
        }

        maxFigures-=figureCount;
        return true;
    }

    @Override
    public HasAction tryToPlaceFigures(PlayerOrder player, int count) {

        if(maxFigures - count < 0){
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return expectedTryToPlaceFigures;
    }

    @Override
    public ActionResult makeAction(PlayerOrder player, Effect[] inputResources, Effect[] outputResources) {

        if(expectedMakeAction == ActionResult.FAILURE || (maxActions - 1 < 0 &&
                (!limitOnlyTargetPlayer || player == targetPlayer))){
            return ActionResult.FAILURE;
        }
        maxActions--;
        return expectedMakeAction;
    }

    @Override
    public boolean skipAction(PlayerOrder player) {

        return expectedSkipAction;
    }

    @Override
    public HasAction tryToMakeAction(PlayerOrder player) {

        if(maxActions <= 0 && (!limitOnlyTargetPlayer || player == targetPlayer)){
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return expectedTryToMakeAction;

    }

    @Override
    public boolean newTurn() {

        return expectedNewTurn;

    }
}
