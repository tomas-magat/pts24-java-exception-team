package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

class PlaceFigureLocationMock implements InterfaceFigureLocation {

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

        throw new AssertionError();

    }
}

public class PlaceFiguresStateTest {

    private PlaceFiguresState placeFiguresState;
    private PlaceFigureLocationMock placeFigureLocationMock1;
    private PlaceFigureLocationMock placeFigureLocationMock2;

    @Before
    public void setup(){

        placeFigureLocationMock1 = new PlaceFigureLocationMock();
        placeFigureLocationMock2 = new PlaceFigureLocationMock();
        Map<Location, InterfaceFigureLocation> places = new HashMap<>();
        places.put(Location.BUILDING_TILE1, placeFigureLocationMock1);
        places.put(Location.BUILDING_TILE2, placeFigureLocationMock2);

        this.placeFiguresState = new PlaceFiguresState(places);
    }

    private HasAction tryToMakeAutomaticAction(){

        return placeFiguresState.tryToMakeAutomaticAction(new PlayerOrder(0,1));
    }

    @Test
    public void testTryToMakeAutomaticAction(){

        placeFigureLocationMock1.expectedHasAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        placeFigureLocationMock2.expectedHasAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        assertEquals(tryToMakeAutomaticAction(), HasAction.WAITING_FOR_PLAYER_ACTION);

        placeFigureLocationMock1.expectedHasAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        placeFigureLocationMock2.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;
        assertEquals(tryToMakeAutomaticAction(), HasAction.WAITING_FOR_PLAYER_ACTION);

        placeFigureLocationMock1.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;
        placeFigureLocationMock2.expectedHasAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        assertEquals(tryToMakeAutomaticAction(), HasAction.WAITING_FOR_PLAYER_ACTION);

        placeFigureLocationMock1.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;
        placeFigureLocationMock2.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;

        assertEquals(tryToMakeAutomaticAction(), HasAction.NO_ACTION_POSSIBLE);
    }


    private ActionResult placeFigures(){

        return placeFiguresState.placeFigures(new PlayerOrder(0,1),Location.BUILDING_TILE1,1);

    }

    @Test
    public void testPlaceFigures() {

        placeFigureLocationMock1.expectedBoolean = false;

        assertEquals(placeFigures(), ActionResult.FAILURE);

        placeFigureLocationMock1.expectedBoolean = true;

        assertEquals(placeFigures(), ActionResult.ACTION_DONE);


    }
}