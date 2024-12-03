package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import static org.junit.Assert.*;

public class GameEndStateTest {

    private GameEndState gameEndState;
    @Before
    public void setUp(){

        gameEndState = new GameEndState();
    }

    @Test
    public void testTryToMakeAutomaticAction() {

        assertEquals(HasAction.WAITING_FOR_PLAYER_ACTION, gameEndState.tryToMakeAutomaticAction(new PlayerOrder(0,1)));
    }
}