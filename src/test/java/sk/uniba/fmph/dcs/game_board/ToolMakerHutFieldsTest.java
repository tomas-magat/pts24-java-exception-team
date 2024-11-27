package sk.uniba.fmph.dcs.game_board;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.OptionalInt;

public class ToolMakerHutFieldsTest {

    Player player;
    Player player2;
    PlayerBoardMock playerBoardMock;
    PlayerBoardMock playerBoardMock2;

    private Player makePlayer(int order, int players, InterfacePlayerBoardGameBoard playerBoard) {
        return new Player(new PlayerOrder(order, players), playerBoard);
    }

    @Before
    public void setup() {
        player = new Player(new PlayerOrder(0, 4), playerBoardMock);
        player2 = new Player(new PlayerOrder(1, 4), playerBoardMock2);
        playerBoardMock = new PlayerBoardMock();
        playerBoardMock2 = new PlayerBoardMock();
    }

    // ToolMaker Tests
    @Test
    public void testToolMakerPlace1PlayerLimit() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.takeFiguresAmount = 0;
        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.takeFiguresAmount = 0;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertTrue(toolMakerHutFields.placeOnToolMaker(player));
        assertEquals(playerBoardMock.takeFiguresAmount, 1);

        playerBoardMock.takeFiguresAmount = 0;
        assertFalse(toolMakerHutFields.placeOnToolMaker(player));
        assertEquals(playerBoardMock.takeFiguresAmount, 0);

        assertFalse(toolMakerHutFields.placeOnToolMaker(player2));
        assertEquals(playerBoardMock2.takeFiguresAmount, 0);
    }

    @Test
    public void testToolMakerPlaceNotEnoughFigures() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = false;
        playerBoardMock.takeFiguresAmount = 0;
        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.takeFiguresAmount = 0;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertFalse(toolMakerHutFields.placeOnToolMaker(player));
        assertEquals(playerBoardMock.takeFiguresAmount, 0);

        assertTrue(toolMakerHutFields.placeOnToolMaker(player2));
        assertEquals(playerBoardMock2.takeFiguresAmount, 1);

        playerBoardMock2.takeFiguresAmount = 0;
        assertFalse(toolMakerHutFields.placeOnToolMaker(player2));
        assertEquals(playerBoardMock2.takeFiguresAmount, 0);
    }


    @Test
    public void testToolMakerActionIncorrectPlayer() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.effectToolCalled = false;
        playerBoardMock.takeFiguresAmount = 0;
        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.effectToolCalled = false;
        playerBoardMock2.takeFiguresAmount = 0;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertFalse(toolMakerHutFields.actionToolMaker(player));
        assertFalse(playerBoardMock.effectToolCalled);
        assertEquals(playerBoardMock.takeFiguresAmount, 0);

        toolMakerHutFields.placeOnToolMaker(player);
        assertFalse(toolMakerHutFields.actionToolMaker(player2));
        assertFalse(playerBoardMock2.effectToolCalled);
        assertEquals(playerBoardMock2.takeFiguresAmount, 0);

        assertTrue(toolMakerHutFields.actionToolMaker(player));
        assertTrue(playerBoardMock.effectToolCalled);
        assertEquals(playerBoardMock.takeFiguresAmount, -1);
    }

    @Test
    public void testToolMakerActionCalledTwice() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.effectToolCalled = false;
        playerBoardMock.takeFiguresAmount = 0;
        player = makePlayer(0,4, playerBoardMock);

        toolMakerHutFields.placeOnToolMaker(player);
        assertTrue(toolMakerHutFields.actionToolMaker(player));
        assertEquals(playerBoardMock.takeFiguresAmount, -1);

        playerBoardMock.takeFiguresAmount = 0;
        assertFalse(toolMakerHutFields.actionToolMaker(player));
        assertEquals(playerBoardMock.takeFiguresAmount, 0);
    }

    // Fields Tests
    @Test
    public void testFieldsPlace1PlayerLimit() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.takeFiguresAmount = 0;
        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.takeFiguresAmount = 0;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertTrue(toolMakerHutFields.placeOnFields(player));
        assertEquals(playerBoardMock.takeFiguresAmount, 1);

        playerBoardMock.takeFiguresAmount = 0;
        assertFalse(toolMakerHutFields.placeOnFields(player));
        assertEquals(playerBoardMock.takeFiguresAmount, 0);

        assertFalse(toolMakerHutFields.placeOnFields(player2));
        assertEquals(playerBoardMock.takeFiguresAmount, 0);
    }

    @Test
    public void testFieldsPlaceNotEnoughFigures() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = false;
        playerBoardMock.takeFiguresAmount = 0;
        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.takeFiguresAmount = 0;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertFalse(toolMakerHutFields.placeOnToolMaker(player));
        assertEquals(playerBoardMock.takeFiguresAmount, 0);

        assertTrue(toolMakerHutFields.placeOnToolMaker(player2));
        assertEquals(playerBoardMock2.takeFiguresAmount, 1);

        playerBoardMock2.takeFiguresAmount = 0;
        assertFalse(toolMakerHutFields.placeOnToolMaker(player2));
        assertEquals(playerBoardMock2.takeFiguresAmount, 0);
    }


    @Test
    public void testFieldsActionIncorrectPlayer() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.effectFieldCalled = false;
        playerBoardMock.takeFiguresAmount = 0;
        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.effectFieldCalled = false;
        playerBoardMock2.takeFiguresAmount = 0;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertFalse(toolMakerHutFields.actionFields(player));
        assertFalse(playerBoardMock.effectFieldCalled);
        assertEquals(playerBoardMock.takeFiguresAmount, 0);

        toolMakerHutFields.placeOnFields(player);
        assertFalse(toolMakerHutFields.actionFields(player2));
        assertFalse(playerBoardMock2.effectFieldCalled);
        assertEquals(playerBoardMock2.takeFiguresAmount, 0);

        assertTrue(toolMakerHutFields.actionFields(player));
        assertTrue(playerBoardMock.effectFieldCalled);
        assertEquals(playerBoardMock.takeFiguresAmount, -1);
    }

    @Test
    public void testFieldsActionCalledTwice() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.effectToolCalled = false;
        playerBoardMock.takeFiguresAmount = 0;
        player = makePlayer(0,4, playerBoardMock);

        toolMakerHutFields.placeOnFields(player);
        assertTrue(toolMakerHutFields.actionFields(player));
        assertEquals(playerBoardMock.takeFiguresAmount, -1);

        playerBoardMock.takeFiguresAmount = 0;
        assertFalse(toolMakerHutFields.actionFields(player));
        assertEquals(playerBoardMock.takeFiguresAmount, 0);
    }

    // Hut Tests
    @Test
    public void testHutPlace1PlayerLimit() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.takeFiguresAmount = 0;
        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.takeFiguresAmount = 0;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertTrue(toolMakerHutFields.placeOnHut(player));
        assertEquals(playerBoardMock.takeFiguresAmount, 2);

        playerBoardMock.takeFiguresAmount = 0;
        assertFalse(toolMakerHutFields.placeOnHut(player));
        assertEquals(playerBoardMock.takeFiguresAmount, 0);

        assertFalse(toolMakerHutFields.placeOnHut(player2));
        assertEquals(playerBoardMock2.takeFiguresAmount, 0);
    }

    @Test
    public void testHutPlaceNotEnoughFigures() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = false;
        playerBoardMock.takeFiguresAmount = 0;
        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.takeFiguresAmount = 0;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertFalse(toolMakerHutFields.placeOnHut(player));
        assertEquals(playerBoardMock.takeFiguresAmount, 0);

        assertTrue(toolMakerHutFields.placeOnHut(player2));
        assertEquals(playerBoardMock2.takeFiguresAmount, 2);

        playerBoardMock2.takeFiguresAmount = 0;
        assertFalse(toolMakerHutFields.placeOnHut(player2));
        assertEquals(playerBoardMock2.takeFiguresAmount, 0);
    }

    @Test
    public void testHutActionIncorrectPlayer() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.addNewFigureCalled = false;
        playerBoardMock.takeFiguresAmount = 0;
        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.addNewFigureCalled = false;
        playerBoardMock2.takeFiguresAmount = 0;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertFalse(toolMakerHutFields.actionHut(player));
        assertFalse(playerBoardMock.addNewFigureCalled);
        assertEquals(playerBoardMock.takeFiguresAmount, 0);

        toolMakerHutFields.placeOnHut(player);
        assertFalse(toolMakerHutFields.actionHut(player2));
        assertFalse(playerBoardMock2.addNewFigureCalled);
        assertEquals(playerBoardMock2.takeFiguresAmount, 0);

        assertTrue(toolMakerHutFields.actionHut(player));
        assertTrue(playerBoardMock.addNewFigureCalled);
        assertEquals(playerBoardMock.takeFiguresAmount, -2);
    }

    @Test
    public void testHutActionCalledTwice() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.addNewFigureCalled = false;
        playerBoardMock.takeFiguresAmount = 0;
        player = makePlayer(0,4, playerBoardMock);

        toolMakerHutFields.placeOnHut(player);
        assertTrue(toolMakerHutFields.actionHut(player));
        assertEquals(playerBoardMock.takeFiguresAmount, -2);

        playerBoardMock.takeFiguresAmount = 0;
        assertFalse(toolMakerHutFields.actionHut(player));
        assertEquals(playerBoardMock.takeFiguresAmount, 0);
    }

    // Place Restriction 3 Players Tests
    @Test
    public void testPlacingRestriction3Players_1() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock2.expectedHasFigures = true;
        player = makePlayer(0,3, playerBoardMock);
        player2 = makePlayer(1, 3, playerBoardMock2);

        assertTrue(toolMakerHutFields.placeOnToolMaker(player));
        assertTrue(toolMakerHutFields.placeOnHut(player2));
        assertFalse(toolMakerHutFields.placeOnFields(player));
    }

    @Test
    public void testPlacingRestriction3Players_2() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock2.expectedHasFigures = true;
        player = makePlayer(0,3, playerBoardMock);
        player2 = makePlayer(1, 3, playerBoardMock2);

        assertTrue(toolMakerHutFields.placeOnHut(player));
        assertTrue(toolMakerHutFields.placeOnFields(player2));
        assertFalse(toolMakerHutFields.placeOnToolMaker(player));
    }

    @Test
    public void testPlacingRestriction3Players_3() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock2.expectedHasFigures = true;
        player = makePlayer(0,3, playerBoardMock);
        player2 = makePlayer(1, 3, playerBoardMock2);

        assertTrue(toolMakerHutFields.placeOnFields(player));
        assertTrue(toolMakerHutFields.placeOnToolMaker(player2));
        assertFalse(toolMakerHutFields.placeOnHut(player));
    }

    private static class PlayerBoardMock implements InterfacePlayerBoardGameBoard {
        public boolean expectedHasFigures;
        public boolean effectToolCalled, effectFieldCalled, addNewFigureCalled;
        public int takeFiguresAmount = 0;
        public PlayerBoardMock() {

        }

        @Override
        public void giveEffect(Effect[] stuff) {
            for (Effect e : stuff) {
                if (e == Effect.TOOL) {
                    effectToolCalled = true;
                    return;
                } else if (e == Effect.FIELD) {
                    effectFieldCalled = true;
                    return;
                }
            }
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
            addNewFigureCalled = true;
            return false;
        }
    }
}
