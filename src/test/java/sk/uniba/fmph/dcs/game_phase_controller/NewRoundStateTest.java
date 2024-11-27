package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.List;

import static org.junit.Assert.*;
class RoundFigureLocationMock implements InterfaceFigureLocation {

    Boolean expectedBoolean;

    @Override
    public boolean placeFigures(PlayerOrder player, int figureCount) {

        throw new AssertionError();
    }

    @Override
    public HasAction tryToPlaceFigures(PlayerOrder player, int count) {

        throw new AssertionError();
    }

    @Override
    public ActionResult makeAction(PlayerOrder player, Effect[] inputResources, Effect[] outputResources) {

        throw new AssertionError();
    }

    @Override
    public boolean skipAction(PlayerOrder player) {

        throw new AssertionError();
    }

    @Override
    public HasAction tryToMakeAction(PlayerOrder player) {

        throw new AssertionError();

    }

    @Override
    public boolean newTurn() {

        return expectedBoolean;

    }
}

class NewTurnMock implements InterfaceNewTurn{

    boolean newTurnCalled = false;
    @Override
    public void newTurn() {

        newTurnCalled = true;
    }
}

public class NewRoundStateTest {

    private RoundFigureLocationMock figureLocationMock1;
    private RoundFigureLocationMock figureLocationMock2;
    private NewRoundState newRoundState;
    private NewTurnMock newTurnMock;
    @Before
    public void setup(){

        figureLocationMock1 = new RoundFigureLocationMock();
        figureLocationMock2 = new RoundFigureLocationMock();
        newTurnMock = new NewTurnMock();

        this.newRoundState = new NewRoundState(List.of(figureLocationMock1, figureLocationMock2), newTurnMock);
    }

    private HasAction tryToMakeAutomaticAction(){


        newTurnMock.newTurnCalled = false;
        return newRoundState.tryToMakeAutomaticAction(new PlayerOrder(0,1));
    }

    @Test
    public void testTryToMakeAutomaticAction() {

        figureLocationMock1.expectedBoolean = false;
        figureLocationMock2.expectedBoolean = false;

        assertEquals(tryToMakeAutomaticAction(), HasAction.AUTOMATIC_ACTION_DONE);
        assertTrue(newTurnMock.newTurnCalled);

        figureLocationMock1.expectedBoolean = false;
        figureLocationMock2.expectedBoolean = true;

        assertEquals(tryToMakeAutomaticAction(), HasAction.AUTOMATIC_ACTION_DONE);
        assertTrue(newTurnMock.newTurnCalled);

        figureLocationMock1.expectedBoolean = true;
        figureLocationMock2.expectedBoolean = false;

        assertEquals(tryToMakeAutomaticAction(), HasAction.AUTOMATIC_ACTION_DONE);
        assertTrue(newTurnMock.newTurnCalled);

        figureLocationMock1.expectedBoolean = true;
        figureLocationMock2.expectedBoolean = true;

        assertEquals(tryToMakeAutomaticAction(), HasAction.NO_ACTION_POSSIBLE);
        assertFalse(newTurnMock.newTurnCalled);
    }
}