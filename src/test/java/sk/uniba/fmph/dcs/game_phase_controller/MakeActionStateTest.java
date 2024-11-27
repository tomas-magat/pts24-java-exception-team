package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static org.junit.Assert.*;

class MakeFigureLocationMock implements InterfaceFigureLocation {

    ActionResult expectedActionResult;
    HasAction expectedHasAction;
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

        throw new AssertionError();

    }
}


public class MakeActionStateTest {

    private MakeActionState makeActionState;
    private MakeFigureLocationMock figureLocationMock1;
    private MakeFigureLocationMock figureLocationMock2;
    @Before
    public void setup(){

        Map<Location, InterfaceFigureLocation> places;
        figureLocationMock1 = new MakeFigureLocationMock();
        figureLocationMock2 = new MakeFigureLocationMock();
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

        figureLocationMock1.expectedHasAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        figureLocationMock2.expectedHasAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        assertEquals(tryToMakeAutomaticAction(), HasAction.WAITING_FOR_PLAYER_ACTION);

        figureLocationMock1.expectedHasAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        figureLocationMock2.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;

        assertEquals(tryToMakeAutomaticAction(), HasAction.WAITING_FOR_PLAYER_ACTION);

        figureLocationMock1.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;
        figureLocationMock2.expectedHasAction = HasAction.WAITING_FOR_PLAYER_ACTION;

        assertEquals(tryToMakeAutomaticAction(), HasAction.WAITING_FOR_PLAYER_ACTION);

        figureLocationMock1.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;
        figureLocationMock2.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;

        assertEquals(tryToMakeAutomaticAction(), HasAction.NO_ACTION_POSSIBLE);
    }

    private ActionResult makeAction(){

        return makeActionState.makeAction(new PlayerOrder(0,1),Location.BUILDING_TILE1,List.of(), List.of());
    }

    @Test
    public void testMakeAction(){

        figureLocationMock1.expectedActionResult = ActionResult.FAILURE;

        assertEquals(makeAction(), ActionResult.FAILURE);

        figureLocationMock1.expectedActionResult = ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE;

        assertEquals(makeAction(), ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE);
    }

    private ActionResult skipAction(){

        return makeActionState.skipAction(new PlayerOrder(0, 1), Location.BUILDING_TILE1);
    }

    @Test
    public void testSkipAction(){

        figureLocationMock1.expectedBoolean = false;

        assertEquals(skipAction(), ActionResult.FAILURE);

        figureLocationMock1.expectedBoolean = true;

        assertEquals(skipAction(), ActionResult.ACTION_DONE);
    }
}