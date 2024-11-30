package sk.uniba.fmph.dcs.game_phase_controller.mocks;

import sk.uniba.fmph.dcs.stone_age.*;

public class FigureLocationMock implements InterfaceFigureLocation {

    public boolean expectedPlaceFigures;
    public HasAction expectedTryToPlaceFigures;
    public ActionResult expectedMakeAction;
    public boolean expectedSkipAction;
    public HasAction expectedTryToMakeAction;
    public boolean expectedNewTurn;

    @Override
    public boolean placeFigures(PlayerOrder player, int figureCount) {

        return expectedPlaceFigures;
    }

    @Override
    public HasAction tryToPlaceFigures(PlayerOrder player, int count) {

        return expectedTryToPlaceFigures;
    }

    @Override
    public ActionResult makeAction(PlayerOrder player, Effect[] inputResources, Effect[] outputResources) {

        return expectedMakeAction;
    }

    @Override
    public boolean skipAction(PlayerOrder player) {

        return expectedSkipAction;
    }

    @Override
    public HasAction tryToMakeAction(PlayerOrder player) {

        return expectedTryToMakeAction;

    }

    @Override
    public boolean newTurn() {

        return expectedNewTurn;

    }
}
