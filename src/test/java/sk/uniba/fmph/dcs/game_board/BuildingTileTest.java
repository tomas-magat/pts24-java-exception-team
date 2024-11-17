package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BuildingTileTest {
    @Test
    public void testOccupiedState() {
        ArrayList<PlayerOrder> figures = new ArrayList<>();
        figures.add(new PlayerOrder(4, 6));
        BuildingTile buildingTile = new BuildingTile(figures);
        String expectedState = "Building Tile state:\n- player: 4";
        assertEquals(buildingTile.state(), expectedState);
    }

    @Test
    public void testEmptyState() {
        ArrayList<PlayerOrder> figures = new ArrayList<>();
        BuildingTile buildingTile = new BuildingTile(figures);
        String expectedState = "Building Tile state:\n- The building is currently unoccupied.";
        assertEquals(buildingTile.state(), expectedState);
    }

    // TODO ine testy
}
