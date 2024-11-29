package sk.uniba.fmph.dcs.game_board;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.OptionalInt;

import static org.junit.Assert.*;

public class ResourceSourceTest {

    Player player1;
    Player player2;
    Player player3;
    Player player4;
    CurrentThrowMock currentThrow;

    @Before
    public void setup() {
        player1 = new Player(new PlayerOrder(0, 4), new PlayerBoardMock());
        player2 = new Player(new PlayerOrder(1, 4), new PlayerBoardMock());
        player3 = new Player(new PlayerOrder(2, 4), new PlayerBoardMock());
        player4 = new Player(new PlayerOrder(3, 4), new PlayerBoardMock());
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

        assertFalse(resourceSource.placeFigures(player1, 1));
        assertFalse(resourceSource.placeFigures(player2, 2));
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
        ResourceSource resourceSource = new ResourceSource(currentThrow, "Forest", Effect.WOOD, 7, 4);
        Collection<Effect> outputResources = new ArrayList<>();

        currentThrow.expectedThrowResult = 1;
        assertEquals(resourceSource.makeAction(player1, null, outputResources), ActionResult.FAILURE);
        assertEquals(resourceSource.makeAction(player2, null, outputResources), ActionResult.FAILURE);
    }

    @Test
    public void testMakeActionCorrectPlayer() {
        ResourceSource resourceSource = new ResourceSource(currentThrow, "Forest", Effect.WOOD, 7, 4);
        Collection<Effect> outputResources = new ArrayList<>();

        currentThrow.expectedThrowResult = 4;
        Effect[] expectedList = new Effect[] { Effect.WOOD, Effect.WOOD, Effect.WOOD, Effect.WOOD };
        PlayerBoardMock playerBoardMock1 = (PlayerBoardMock) player1.getPlayerBoard();

        assertTrue(resourceSource.placeFigures(player1, 3));
        assertEquals(resourceSource.makeAction(player1, null, outputResources), ActionResult.ACTION_DONE);
        assertEquals(expectedList, playerBoardMock1.givenEffects);

        assertEquals(resourceSource.makeAction(player2, null, outputResources), ActionResult.FAILURE);
    }

    @Test
    public void testMakeActionCorrectPlayer2() {
        ResourceSource resourceSource = new ResourceSource(currentThrow, "Quarry", Effect.STONE, 7, 4);
        Collection<Effect> outputResources = new ArrayList<>();

        currentThrow.expectedThrowResult = 2;
        Effect[] expectedList = new Effect[] { Effect.STONE, Effect.STONE };
        PlayerBoardMock playerBoardMock1 = (PlayerBoardMock) player1.getPlayerBoard();

        assertTrue(resourceSource.placeFigures(player1, 3));
        assertEquals(resourceSource.makeAction(player1, null, outputResources), ActionResult.ACTION_DONE);
        assertEquals(expectedList, playerBoardMock1.givenEffects);

        outputResources.clear();
        currentThrow.expectedThrowResult = 3;
        expectedList = new Effect[] { Effect.STONE, Effect.STONE, Effect.STONE };
        PlayerBoardMock playerBoardMock2 = (PlayerBoardMock) player2.getPlayerBoard();

        assertTrue(resourceSource.placeFigures(player2, 3));
        assertEquals(resourceSource.makeAction(player2, null, outputResources), ActionResult.ACTION_DONE);
        assertEquals(expectedList, playerBoardMock2.givenEffects);



        assertEquals(resourceSource.makeAction(player3, null, outputResources), ActionResult.FAILURE);
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

    private static class PlayerBoardMock implements InterfacePlayerBoardGameBoard {
        public boolean expectedHasFigures;
        public int takeFiguresAmount = 0;
        public Effect[] givenEffects;

        @Override
        public void giveEffect(Effect[] stuff) {
            this.givenEffects = stuff;
        }

        @Override
        public void giveEndOfGameEffect(EndOfGameEffect[] stuff) {

        }

        @Override
        public boolean takeResources(Effect[] stuff) {
            return false;
        }

        @Override
        public boolean takeFigures(int count) {
            takeFiguresAmount = count;
            return false;
        }

        @Override
        public boolean hasFigures(int count) {
            return expectedHasFigures;
        }

        @Override
        public boolean hasSufficientTools(int goal) {
            return false;
        }

        @Override
        public OptionalInt useTool(int idx) {
            return null;
        }

        @Override
        public boolean addNewFigure() {
            return false;
        }
    }
}
