package sk.uniba.fmph.dcs.game_board;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;
import sk.uniba.fmph.dcs.stone_age.InterfacePlayerBoardGameBoard;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.OptionalInt;

import static org.junit.Assert.*;

public class CurrentThrowTest {

    CurrentThrow currentThrow;
    ThrowMock throwMock;
    Player player1;
    PlayerBoardMock playerBoardMock1;

    @Before
    public void setup() {
        throwMock = new ThrowMock();
        currentThrow = new CurrentThrow(throwMock);
        playerBoardMock1 = new PlayerBoardMock();
        player1 = new Player(new PlayerOrder(0, 4), playerBoardMock1);
    }

    @Test
    public void testThrowFood() {
        throwMock.expectedResult = new int[] {1, 2, 3, 4, 5, 6};
        currentThrow.initiate(player1, Effect.FOOD, 6);
        assertEquals(currentThrow.getThrowResult(), 10);

        assertTrue(currentThrow.canUseTools());

        playerBoardMock1.expectedHasSufficientTools = false;
        assertFalse(currentThrow.useTool(2));

        playerBoardMock1.expectedHasSufficientTools = true;
        assertTrue(currentThrow.useTool(2));
        assertEquals(playerBoardMock1.usedToolID, 2);
        assertEquals(currentThrow.getThrowResult(), 11);

        playerBoardMock1.expectedHasSufficientTools = true;
        assertTrue(currentThrow.useTool(4));
        assertEquals(playerBoardMock1.usedToolID, 4);
        assertEquals(currentThrow.getThrowResult(), 13);
    }

    @Test
    public void testThrowWood() {
        throwMock.expectedResult = new int[] {3, 2, 5};
        currentThrow.initiate(player1, Effect.WOOD, 3);
        assertEquals(currentThrow.getThrowResult(), 3);

        assertTrue(currentThrow.canUseTools());

        playerBoardMock1.expectedHasSufficientTools = false;
        assertFalse(currentThrow.useTool(4));

        playerBoardMock1.expectedHasSufficientTools = true;
        assertTrue(currentThrow.useTool(4));
        assertEquals(playerBoardMock1.usedToolID, 4);
        assertEquals(currentThrow.getThrowResult(), 4);
    }

    @Test
    public void testThrowClay() {
        throwMock.expectedResult = new int[] {4};
        currentThrow.initiate(player1, Effect.CLAY, 1);
        assertEquals(currentThrow.getThrowResult(), 1);

        assertTrue(currentThrow.canUseTools());

        playerBoardMock1.expectedHasSufficientTools = false;
        assertFalse(currentThrow.useTool(6));

        playerBoardMock1.expectedHasSufficientTools = true;
        assertTrue(currentThrow.useTool(6));
        assertEquals(playerBoardMock1.usedToolID, 6);
        assertEquals(currentThrow.getThrowResult(), 2);
    }

    @Test
    public void testThrowStone() {
        throwMock.expectedResult = new int[] {6, 2, 3, 1};
        currentThrow.initiate(player1, Effect.STONE, 4);
        assertEquals(currentThrow.getThrowResult(), 2);

        assertTrue(currentThrow.canUseTools());

        playerBoardMock1.expectedHasSufficientTools = false;
        assertFalse(currentThrow.useTool(3));

        playerBoardMock1.expectedHasSufficientTools = true;
        assertTrue(currentThrow.useTool(3));
        assertEquals(playerBoardMock1.usedToolID, 3);
        assertEquals(currentThrow.getThrowResult(), 3);
    }

    @Test
    public void testThrowGold() {
        throwMock.expectedResult = new int[] {6, 4, 2, 5, 2};
        currentThrow.initiate(player1, Effect.GOLD, 5);
        assertEquals(currentThrow.getThrowResult(), 3);

        assertTrue(currentThrow.canUseTools());

        playerBoardMock1.expectedHasSufficientTools = false;
        assertFalse(currentThrow.useTool(10));

        playerBoardMock1.expectedHasSufficientTools = true;
        assertTrue(currentThrow.useTool(10));
        assertEquals(playerBoardMock1.usedToolID, 10);
        assertEquals(currentThrow.getThrowResult(), 4);

        playerBoardMock1.expectedHasSufficientTools = true;
        assertTrue(currentThrow.useTool(7));
        assertEquals(playerBoardMock1.usedToolID, 7);
        assertEquals(currentThrow.getThrowResult(), 6);
    }

    @Test
    public void testThrowCivilizationCardDiceRoll() {
        // test rolling dice on the civilization card "Dice roll"
        throwMock.expectedResult = new int[] {6, 4, 4, 2};
        currentThrow.initiate(player1, Effect.BUILDING, 4); // don't know what effect to put here since the enum doesn't have civilization card or something
        assertEquals(currentThrow.getThrowResult(), 16);

        assertFalse(currentThrow.canUseTools());

        playerBoardMock1.expectedHasSufficientTools = false;
        assertFalse(currentThrow.useTool(1));

        playerBoardMock1.expectedHasSufficientTools = true;
        assertFalse(currentThrow.useTool(1));
        assertEquals(playerBoardMock1.usedToolID, 0);
        assertEquals(currentThrow.getThrowResult(), 16);
    }



    private static class ThrowMock implements InterfaceThrow {
        public int[] expectedResult;
        @Override
        public int[] throwDices(int dices) {
            return expectedResult;
        }
    }

    private static class PlayerBoardMock implements InterfacePlayerBoardGameBoard {
        public boolean expectedHasSufficientTools;
        public int usedToolID;

        @Override
        public void giveEffect(Effect[] stuff) {

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
            return false;
        }

        @Override
        public boolean hasFigures(int count) {
            return false;
        }

        @Override
        public boolean hasSufficientTools(int goal) {
            return expectedHasSufficientTools;
        }

        @Override
        public OptionalInt useTool(int idx) {
            this.usedToolID = idx;
            return null;
        }

        @Override
        public boolean addNewFigure() {
            return false;
        }
    }
}
