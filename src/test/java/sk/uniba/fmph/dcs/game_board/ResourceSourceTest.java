package sk.uniba.fmph.dcs.game_board;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class ResourceSourceTest {

    Player player1;
    Player player2;
    Player player3;
    Player player4;
    CurrentThrowMock currentThrow;

    @Before
    public void setup() {
        player1 = new Player(new PlayerOrder(0, 4), null);
        player2 = new Player(new PlayerOrder(1, 4), null);
        player3 = new Player(new PlayerOrder(2, 4), null);
        player4 = new Player(new PlayerOrder(3, 4), null);
        currentThrow = new CurrentThrowMock();
    }

    @Test
    public void testPlaceFigures() {
        ResourceSource resourceSource = new ResourceSource(null, "Forest", Effect.WOOD, 7, 4);

        assertTrue(resourceSource.placeFigures(player1, 2));
        assertTrue(resourceSource.placeFigures(player2, 2));

        assertTrue(resourceSource.placeFigures(player3, 1));
        assertTrue(resourceSource.placeFigures(player3, 1));

        assertTrue(resourceSource.placeFigures(player4, 1));

        assertFalse(resourceSource.placeFigures(player4, 1));
        assertFalse(resourceSource.placeFigures(player3, 1));
        assertFalse(resourceSource.placeFigures(player2, 3));
        assertFalse(resourceSource.placeFigures(player1, 2));
    }

    @Test
    public void testPlaceFigures3Players() {
        ResourceSource resourceSource = new ResourceSource(null, "Forest", Effect.WOOD, 7, 2);

        assertTrue(resourceSource.placeFigures(player1, 3));
        assertTrue(resourceSource.placeFigures(player2, 1));

        assertFalse(resourceSource.placeFigures(player3, 1));
        assertFalse(resourceSource.placeFigures(player4, 2));

        assertTrue(resourceSource.placeFigures(player1, 1));
        assertTrue(resourceSource.placeFigures(player2, 2));
    }
    @Test
    public void testPlaceFigures2Players() {
        ResourceSource resourceSource = new ResourceSource(null, "Forest", Effect.WOOD, 7, 1);

        assertTrue(resourceSource.placeFigures(player1, 3));

        assertFalse(resourceSource.placeFigures(player2, 1));
        assertFalse(resourceSource.placeFigures(player3, 1));
        assertFalse(resourceSource.placeFigures(player4, 2));

        assertTrue(resourceSource.placeFigures(player1, 2));
        assertFalse(resourceSource.placeFigures(player2, 2));
    }

    @Test
    public void testMakeActionWrongPlayer() {
        ResourceSource resourceSource = new ResourceSource(null, "Forest", Effect.WOOD, 7, 4);
        Collection<Effect> outputResources = new ArrayList<>();

        currentThrow.expectedThrowResult = 1;
        assertEquals(resourceSource.makeAction(player1, null, outputResources), ActionResult.FAILURE);
    }




    private static class CurrentThrowMock implements InterfaceCurrentThrow {
        public int expectedThrowResult;
        @Override
        public void initiate(Player player, Effect effect, int dices) {
            // nothing to see here...
        }

        @Override
        public int getThrowResult() {
            return expectedThrowResult;
        }

        @Override
        public String state() {
            return null; // don't need
        }
    }
}
