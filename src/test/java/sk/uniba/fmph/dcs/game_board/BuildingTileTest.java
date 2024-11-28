package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.OptionalInt;

import org.junit.Test;
import static org.junit.Assert.*;


class PlayerBoardMock implements InterfacePlayerBoardGameBoard {
    private int figureCount = 4; // starting figure count
    public int getFigureCount() {
        return figureCount;
    }

    @Override
    public void giveEffect(Effect[] stuff) {}

    @Override
    public void giveEndOfGameEffect(EndOfGameEffect[] stuff) {}

    @Override
    public boolean takeResources(Effect[] stuff) {
        return false;
    }

    @Override
    public boolean takeFigures(int count) {
        figureCount -= count;
        return true;
    }

    @Override
    public boolean hasFigures(int count) {
        return count <= figureCount;
    }

    @Override
    public boolean hasSufficientTools(int goal) {
        return false;
    }

    @Override
    public OptionalInt useTool(int idx) {
        return OptionalInt.empty();
    }

    @Override
    public boolean addNewFigure() {
        figureCount += 1;
        return true;
    }
}

public class BuildingTileTest {
    @Test
    public void testOccupiedState() {
        ArrayList<PlayerOrder> figures = new ArrayList<>();
        figures.add(new PlayerOrder(4, 6));
        BuildingTile buildingTile = new BuildingTile(figures);
        Map<String, String> expectedStateMap = Map.of(
                "building tile state", "occupied",
                "player count", String.valueOf(1),
                "player", String.valueOf(4)
        );
        String expectedState = new JSONObject(expectedStateMap).toString();
        assertEquals(buildingTile.state(), expectedState);
    }

    @Test
    public void testEmptyState() {
        ArrayList<PlayerOrder> figures = new ArrayList<>();
        BuildingTile buildingTile = new BuildingTile(figures);
        Map<String, String> expectedStateMap = Map.of(
                "building tile state", "unoccupied",
                "player count", String.valueOf(0)
        );
        String expectedState = new JSONObject(expectedStateMap).toString();
        assertEquals(buildingTile.state(), expectedState);
    }

    @Test
    public void testTryToPlaceFiguresIntoOccupied() {
        PlayerBoardMock pbm = new PlayerBoardMock();
        Player player = new Player(new PlayerOrder(4, 6), pbm);
        ArrayList<PlayerOrder> figures = new ArrayList<>();
        figures.add(player.getPlayerOrder());
        BuildingTile buildingTile = new BuildingTile(figures);

        assertEquals(buildingTile.tryToPlaceFigures(player, 0), HasAction.NO_ACTION_POSSIBLE);
        assertEquals(buildingTile.tryToPlaceFigures(player, 1), HasAction.NO_ACTION_POSSIBLE);
        assertEquals(buildingTile.tryToPlaceFigures(player, 2), HasAction.NO_ACTION_POSSIBLE);
        assertEquals(buildingTile.tryToPlaceFigures(player, 3), HasAction.NO_ACTION_POSSIBLE);
        assertEquals(buildingTile.tryToPlaceFigures(player, 4), HasAction.NO_ACTION_POSSIBLE);
    }

    @Test
    public void testTryToPlaceFiguresIntoEmpty() {
        PlayerBoardMock pbm = new PlayerBoardMock();
        Player player = new Player(new PlayerOrder(4, 6), pbm);
        ArrayList<PlayerOrder> figures = new ArrayList<>();
        BuildingTile buildingTile = new BuildingTile(figures);

        assertEquals(buildingTile.tryToPlaceFigures(player, 0), HasAction.WAITING_FOR_PLAYER_ACTION);
        assertTrue(buildingTile.placeFigures(player, 0));
        assertEquals(buildingTile.tryToPlaceFigures(player, 1), HasAction.WAITING_FOR_PLAYER_ACTION);
        assertTrue(buildingTile.placeFigures(player, 1));
        assertEquals(buildingTile.tryToPlaceFigures(player, 2), HasAction.NO_ACTION_POSSIBLE);
        assertFalse(buildingTile.placeFigures(player, 2));
        assertEquals(buildingTile.tryToPlaceFigures(player, 3), HasAction.NO_ACTION_POSSIBLE);
        assertFalse(buildingTile.placeFigures(player, 3));
        assertEquals(buildingTile.tryToPlaceFigures(player, 4), HasAction.NO_ACTION_POSSIBLE);
        assertFalse(buildingTile.placeFigures(player, 4));
    }

    @Test
    public void testSuccessfulSkipAction() {
        PlayerBoardMock pbm = new PlayerBoardMock();
        Player player = new Player(new PlayerOrder(4, 6), pbm);
        ArrayList<PlayerOrder> figures = new ArrayList<>();
        figures.add(player.getPlayerOrder());
        BuildingTile buildingTile = new BuildingTile(figures);

        assertTrue(buildingTile.skipAction(player));
        assertEquals(buildingTile.getFigures().size(), 0);
        assertEquals(pbm.getFigureCount(), 5);
    }

    @Test
    public void testUnsuccessfulSkipAction() {
        Player player = new Player(new PlayerOrder(4, 6), new PlayerBoardMock());
        ArrayList<PlayerOrder> figures = new ArrayList<>();
        BuildingTile buildingTile = new BuildingTile(figures);

        assertFalse(buildingTile.skipAction(player));
    }
}