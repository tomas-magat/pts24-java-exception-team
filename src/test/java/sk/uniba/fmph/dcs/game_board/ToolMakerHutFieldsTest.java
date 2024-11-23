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
        playerBoardMock2.expectedHasFigures = true;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertTrue(toolMakerHutFields.placeOnToolMaker(player));
        assertFalse(toolMakerHutFields.placeOnToolMaker(player));
        assertFalse(toolMakerHutFields.placeOnToolMaker(player2));
    }

    @Test
    public void testToolMakerPlaceNotEnoughFigures() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = false;
        playerBoardMock2.expectedHasFigures = true;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertFalse(toolMakerHutFields.placeOnToolMaker(player));
        assertTrue(toolMakerHutFields.placeOnToolMaker(player2));
        assertFalse(toolMakerHutFields.placeOnToolMaker(player2));
    }


    @Test
    public void testToolMakerActionIncorrectPlayer() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.effectToolCalled = false;
        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.effectToolCalled = false;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertFalse(toolMakerHutFields.actionToolMaker(player));
        assertFalse(playerBoardMock.effectToolCalled);

        toolMakerHutFields.placeOnToolMaker(player);
        assertFalse(toolMakerHutFields.actionToolMaker(player2));
        assertFalse(playerBoardMock2.effectToolCalled);
        assertTrue(toolMakerHutFields.actionToolMaker(player));
        assertTrue(playerBoardMock.effectToolCalled);
    }

    @Test
    public void testToolMakerActionCalledTwice() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.effectToolCalled = false;
        player = makePlayer(0,4, playerBoardMock);

        toolMakerHutFields.placeOnToolMaker(player);
        assertTrue(toolMakerHutFields.actionToolMaker(player));
        assertFalse(toolMakerHutFields.actionToolMaker(player));
    }

    // Fields Tests
    @Test
    public void testFieldsPlace1PlayerLimit() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock2.expectedHasFigures = true;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertTrue(toolMakerHutFields.placeOnFields(player));
        assertFalse(toolMakerHutFields.placeOnFields(player));
        assertFalse(toolMakerHutFields.placeOnFields(player2));
    }

    @Test
    public void testFieldsPlaceNotEnoughFigures() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = false;
        playerBoardMock2.expectedHasFigures = true;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertFalse(toolMakerHutFields.placeOnToolMaker(player));
        assertTrue(toolMakerHutFields.placeOnToolMaker(player2));
        assertFalse(toolMakerHutFields.placeOnToolMaker(player2));
    }


    @Test
    public void testFieldsActionIncorrectPlayer() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.effectFieldCalled = false;
        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.effectFieldCalled = false;
        player = makePlayer(0,4, playerBoardMock);
        player2 = makePlayer(1, 4, playerBoardMock2);

        assertFalse(toolMakerHutFields.actionFields(player));
        assertFalse(playerBoardMock.effectFieldCalled);

        toolMakerHutFields.placeOnFields(player);
        assertFalse(toolMakerHutFields.actionFields(player2));
        assertFalse(playerBoardMock2.effectFieldCalled);
        assertTrue(toolMakerHutFields.actionFields(player));
        assertTrue(playerBoardMock.effectFieldCalled);
    }

    @Test
    public void testFieldsActionCalledTwice() {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();
        playerBoardMock.expectedHasFigures = true;
        playerBoardMock.effectToolCalled = false;
        player = makePlayer(0,4, playerBoardMock);

        toolMakerHutFields.placeOnFields(player);
        assertTrue(toolMakerHutFields.actionFields(player));
        assertFalse(toolMakerHutFields.actionFields(player));
    }



    private static class PlayerBoardMock implements InterfacePlayerBoardGameBoard {
        public boolean expectedHasFigures;
        public boolean effectToolCalled, effectFieldCalled;
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
