package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.*;

public class FigureLocationMock implements InterfaceFigureLocation {

    ActionResult expectedActionResult;
    HasAction expectedHasAction;
    Boolean expectedBoolean;

    @Override
    public boolean placeFigures(PlayerOrder player, int figureCount) {

        return expectedBoolean;
    }

    @Override
    public HasAction tryToPlaceFigures(PlayerOrder player, int count) {

        return expectedHasAction;
    }

    @Override
    public ActionResult makeAction(PlayerOrder player, Effect[] inputResources, Effect[] outputResources) {

        return expectedActionResult;
    }

    @Override
    public boolean skipAction(PlayerOrder player) {

        return expectedBoolean;
    }

    @Override
    public HasAction tryToMakeAction(PlayerOrder player) {

        return expectedHasAction;

    }

    @Override
    public boolean newTurn() {

        return expectedBoolean;

    }
}

