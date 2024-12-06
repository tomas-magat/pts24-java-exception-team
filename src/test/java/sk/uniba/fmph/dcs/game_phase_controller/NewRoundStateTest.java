package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.game_phase_controller.mocks.FigureLocationMock;
import sk.uniba.fmph.dcs.game_phase_controller.mocks.NewTurnMock;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class NewRoundStateTest {

    private FigureLocationMock figureLocationMock1;
    private FigureLocationMock figureLocationMock2;
    private NewRoundState newRoundState;
    private List<NewTurnMock> newTurnMocks;
    @Before
    public void setup(){

        figureLocationMock1 = new FigureLocationMock();
        figureLocationMock2 = new FigureLocationMock();
        NewTurnMock newTurnMock1 = new NewTurnMock();
        NewTurnMock newTurnMock2 = new NewTurnMock();

        newTurnMocks = new LinkedList<>();
        newTurnMocks.addAll(List.of(newTurnMock1, newTurnMock2));

        this.newRoundState = new NewRoundState(List.of(figureLocationMock1, figureLocationMock2), Map.of(
                new PlayerOrder(0,2), newTurnMock1, new PlayerOrder(1, 2), newTurnMock2
        ));
    }

    private HasAction tryToMakeAutomaticAction(){


        newTurnMocks.get(0).newTurnCalled = false;
        newTurnMocks.get(1).newTurnCalled = false;
        return newRoundState.tryToMakeAutomaticAction(new PlayerOrder(0,1));
    }

    private void checkNewTurnCalled(){

        assertTrue(newTurnMocks.get(0).newTurnCalled);
        assertTrue(newTurnMocks.get(1).newTurnCalled);
    }

    @Test
    public void testTryToMakeAutomaticAction() {

        figureLocationMock1.expectedNewTurn = false;
        figureLocationMock2.expectedNewTurn = false;

        assertEquals(HasAction.AUTOMATIC_ACTION_DONE, tryToMakeAutomaticAction());
        checkNewTurnCalled();

        figureLocationMock1.expectedNewTurn = false;
        figureLocationMock2.expectedNewTurn = true;

        assertEquals(HasAction.NO_ACTION_POSSIBLE, tryToMakeAutomaticAction());

        figureLocationMock1.expectedNewTurn = true;
        figureLocationMock2.expectedNewTurn = false;

        assertEquals(HasAction.NO_ACTION_POSSIBLE, tryToMakeAutomaticAction());

        figureLocationMock1.expectedNewTurn = true;
        figureLocationMock2.expectedNewTurn = true;

        assertEquals(HasAction.NO_ACTION_POSSIBLE, tryToMakeAutomaticAction());
    }
}