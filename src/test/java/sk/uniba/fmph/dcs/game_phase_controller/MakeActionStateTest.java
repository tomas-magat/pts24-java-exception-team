package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.game_phase_controller.mocks.FigureLocationMock;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static org.junit.Assert.*;


public class MakeActionStateTest {

    private MakeActionState makeActionState;
    private FigureLocationMock figureLocationMock1;
    private FigureLocationMock figureLocationMock2;
    @Before
    public void setup(){

        Map<Location, InterfaceFigureLocation> places;
        figureLocationMock1 = new FigureLocationMock();
        figureLocationMock2 = new FigureLocationMock();
        places = new HashMap<>();
        places.put(Location.BUILDING_TILE1, figureLocationMock1);
        places.put(Location.BUILDING_TILE2, figureLocationMock2);

        this.makeActionState = new MakeActionState(places);
    }

    private HasAction tryToMakeAutomaticAction(){

        return makeActionState.tryToMakeAutomaticAction(new PlayerOrder(0,1));
    }

    @Test
    public void testTryToMakeAutomaticAction(){

        figureLocationMock1.expectedTryToMakeAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        figureLocationMock2.expectedTryToMakeAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        assertEquals(HasAction.WAITING_FOR_PLAYER_ACTION, tryToMakeAutomaticAction());

        figureLocationMock1.expectedTryToMakeAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        figureLocationMock2.expectedTryToMakeAction = HasAction.NO_ACTION_POSSIBLE;
        assertEquals(HasAction.WAITING_FOR_PLAYER_ACTION, tryToMakeAutomaticAction());

        figureLocationMock1.expectedTryToMakeAction = HasAction.NO_ACTION_POSSIBLE;
        figureLocationMock2.expectedTryToMakeAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        assertEquals(HasAction.WAITING_FOR_PLAYER_ACTION, tryToMakeAutomaticAction());

        figureLocationMock1.expectedTryToMakeAction = HasAction.NO_ACTION_POSSIBLE;
        figureLocationMock2.expectedTryToMakeAction = HasAction.NO_ACTION_POSSIBLE;
        assertEquals(HasAction.NO_ACTION_POSSIBLE, tryToMakeAutomaticAction());
    }

    private ActionResult makeAction(){

        return makeActionState.makeAction(new PlayerOrder(0,1),Location.BUILDING_TILE1,List.of(), List.of());
    }

    @Test
    public void testMakeAction(){

        figureLocationMock1.expectedMakeAction = ActionResult.FAILURE;

        assertEquals(ActionResult.FAILURE, makeAction());

        figureLocationMock1.expectedMakeAction = ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE;

        assertEquals(ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE, makeAction());
    }

    private ActionResult skipAction(){

        return makeActionState.skipAction(new PlayerOrder(0, 1), Location.BUILDING_TILE1);
    }

    @Test
    public void testSkipAction(){

        figureLocationMock1.expectedSkipAction = false;

        assertEquals(ActionResult.FAILURE, skipAction());

        figureLocationMock1.expectedSkipAction = true;

        assertEquals(ActionResult.ACTION_DONE, skipAction());
    }
}