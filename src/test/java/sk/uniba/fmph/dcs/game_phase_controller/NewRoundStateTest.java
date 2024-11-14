package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.List;

import static org.junit.Assert.*;
class FigureLocationMock implements InterfaceFigureLocation {

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

class NewTurnMock implements InterfaceNewTurn{

    @Override
    public void newTurn() {

    }
}

public class NewRoundStateTest {

    private FigureLocationMock figureLocationMock1;
    private FigureLocationMock figureLocationMock2;
    private NewRoundState newRoundState;
    @Before
    public void setup(){

        figureLocationMock1 = new FigureLocationMock();
        figureLocationMock2 = new FigureLocationMock();

        this.newRoundState = new NewRoundState(List.of(figureLocationMock1, figureLocationMock2), new NewTurnMock());
    }

    private HasAction tryToMakeAutomaticAction(){

        return newRoundState.tryToMakeAutomaticAction(new PlayerOrder(0,1));
    }

    @Test
    public void testTryToMakeAutomaticAction() {

        figureLocationMock1.expectedBoolean = false;
        figureLocationMock2.expectedBoolean = true;

        assertEquals(tryToMakeAutomaticAction(), HasAction.AUTOMATIC_ACTION_DONE);

        figureLocationMock1.expectedBoolean = true;
        figureLocationMock2.expectedBoolean = false;

        assertEquals(tryToMakeAutomaticAction(), HasAction.AUTOMATIC_ACTION_DONE);

        figureLocationMock1.expectedBoolean = true;
        figureLocationMock2.expectedBoolean = true;

        assertEquals(tryToMakeAutomaticAction(), HasAction.NO_ACTION_POSSIBLE);
    }
}