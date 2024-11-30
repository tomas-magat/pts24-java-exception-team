package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.game_phase_controller.mocks.FigureLocationMock;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PlaceFiguresStateTest {

    private PlaceFiguresState placeFiguresState;
    private FigureLocationMock figureLocationMock1;
    private FigureLocationMock figureLocationMock2;

    @Before
    public void setup(){

        figureLocationMock1 = new FigureLocationMock();
        figureLocationMock2 = new FigureLocationMock();
        Map<Location, InterfaceFigureLocation> places = new HashMap<>();
        places.put(Location.BUILDING_TILE1, figureLocationMock1);
        places.put(Location.BUILDING_TILE2, figureLocationMock2);

        this.placeFiguresState = new PlaceFiguresState(places);
    }

    private HasAction tryToMakeAutomaticAction(){

        return placeFiguresState.tryToMakeAutomaticAction(new PlayerOrder(0,1));
    }

    @Test
    public void testTryToMakeAutomaticAction(){

        figureLocationMock1.expectedTryToPlaceFigures = HasAction.WAITING_FOR_PLAYER_ACTION;
        figureLocationMock2.expectedTryToPlaceFigures = HasAction.WAITING_FOR_PLAYER_ACTION;
        assertEquals(HasAction.WAITING_FOR_PLAYER_ACTION, tryToMakeAutomaticAction());

        figureLocationMock1.expectedTryToPlaceFigures = HasAction.WAITING_FOR_PLAYER_ACTION;
        figureLocationMock2.expectedTryToPlaceFigures = HasAction.NO_ACTION_POSSIBLE;
        assertEquals(HasAction.WAITING_FOR_PLAYER_ACTION, tryToMakeAutomaticAction());

        figureLocationMock1.expectedTryToPlaceFigures = HasAction.NO_ACTION_POSSIBLE;
        figureLocationMock2.expectedTryToPlaceFigures = HasAction.WAITING_FOR_PLAYER_ACTION;
        assertEquals(HasAction.WAITING_FOR_PLAYER_ACTION, tryToMakeAutomaticAction());

        figureLocationMock1.expectedTryToPlaceFigures = HasAction.NO_ACTION_POSSIBLE;
        figureLocationMock2.expectedTryToPlaceFigures = HasAction.NO_ACTION_POSSIBLE;
        assertEquals(HasAction.NO_ACTION_POSSIBLE, tryToMakeAutomaticAction());
    }


    private ActionResult placeFigures(){

        return placeFiguresState.placeFigures(new PlayerOrder(0,1),Location.BUILDING_TILE1,1);

    }

    @Test
    public void testPlaceFigures() {

        figureLocationMock1.expectedPlaceFigures = false;
        assertEquals(ActionResult.FAILURE, placeFigures());

        figureLocationMock1.expectedPlaceFigures = true;
        assertEquals(ActionResult.ACTION_DONE, placeFigures());


    }
}