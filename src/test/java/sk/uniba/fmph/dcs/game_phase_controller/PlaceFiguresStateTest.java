package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
public class PlaceFiguresStateTest {

    private PlaceFiguresState placeFiguresState;
    private FigureLocationMock figureLocationMock1;
    private FigureLocationMock figureLocationMock2;
    private Map<Location, InterfaceFigureLocation> places;
    @Before
    public void setup(){

        figureLocationMock1 = new FigureLocationMock();
        figureLocationMock2 = new FigureLocationMock();
        places = new HashMap<>();
        places.put(Location.BUILDING_TILE1, figureLocationMock1);
        places.put(Location.BUILDING_TILE2, figureLocationMock2);

        this.placeFiguresState = new PlaceFiguresState(places);
    }

    private HasAction tryToMakeAutomaticAction(){

        return placeFiguresState.tryToMakeAutomaticAction(new PlayerOrder(0,1));
    }

    private ActionResult placeFigures(){

        return placeFiguresState.placeFigures(new PlayerOrder(0,1),Location.BUILDING_TILE1,1);

    }

    @Test
    public void testTryToMakeAutomaticAction(){

        figureLocationMock1.expectedHasAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        figureLocationMock2.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;

        assertEquals(tryToMakeAutomaticAction(), HasAction.WAITING_FOR_PLAYER_ACTION);

        figureLocationMock1.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;
        figureLocationMock2.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;

        assertEquals(tryToMakeAutomaticAction(), HasAction.NO_ACTION_POSSIBLE);
    }


    @Test
    public void testPlaceFigures() {

        figureLocationMock1.expectedBoolean = false;

        assertEquals(placeFigures(), ActionResult.FAILURE);

        figureLocationMock1.expectedBoolean = true;

        assertEquals(placeFigures(), ActionResult.ACTION_DONE);


    }
}