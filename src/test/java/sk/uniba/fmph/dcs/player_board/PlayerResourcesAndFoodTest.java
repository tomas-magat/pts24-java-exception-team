package sk.uniba.fmph.dcs.player_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerResourcesAndFoodTest {

    @Test
    public void testHasResourcesEmpty() {
        PlayerResourcesAndFood res = new PlayerResourcesAndFood();

        List<Effect> list1 = new ArrayList<>(Arrays.asList(Effect.WOOD, Effect.CLAY));
        assertFalse(res.hasResources(list1));
    }

    @Test
    public void testHasResources() {
        PlayerResourcesAndFood res = new PlayerResourcesAndFood();
        res.giveResources(Arrays.asList(Effect.WOOD, Effect.WOOD, Effect.CLAY, Effect.GOLD, Effect.GOLD, Effect.GOLD));

        List<Effect> list1 = new ArrayList<>(Arrays.asList(Effect.WOOD, Effect.CLAY));
        assertTrue(res.hasResources(list1));

        list1.addAll(Arrays.asList(Effect.WOOD, Effect.GOLD, Effect.GOLD, Effect.GOLD));
        assertTrue(res.hasResources(list1));

        list1.add(Effect.WOOD);
        assertFalse(res.hasResources(list1));
    }

    @Test
    public void testTakeResourcesEmpty() {
        PlayerResourcesAndFood res = new PlayerResourcesAndFood();
        assertFalse(res.takeResources(Arrays.asList(Effect.WOOD)));
    }

    @Test
    public void testTakeResources() {
        PlayerResourcesAndFood res = new PlayerResourcesAndFood();
        res.giveResources(Arrays.asList(Effect.WOOD, Effect.WOOD, Effect.CLAY, Effect.GOLD, Effect.GOLD, Effect.GOLD));

        List<Effect> list1 = new ArrayList<>(Arrays.asList(Effect.WOOD, Effect.CLAY));
        assertTrue(res.takeResources(list1));

        assertFalse(res.hasResources(Arrays.asList(Effect.WOOD, Effect.CLAY)));

        assertFalse(res.takeResources(list1));

        List<Effect> list2 = new ArrayList<>(Arrays.asList(Effect.GOLD, Effect.GOLD, Effect.GOLD));
        assertTrue(res.takeResources(list2));
        assertFalse(res.takeResources(list2));
    }

    @Test
    public void testNumberOfResourcesForFinalPointsNoResources() {
        PlayerResourcesAndFood res = new PlayerResourcesAndFood();
        res.giveResources(Arrays.asList(Effect.FOOD, Effect.FOOD, Effect.BUILDING, Effect.ONE_TIME_TOOL2, Effect.TOOL));

        assertEquals(res.numberOfResourcesForFinalPoints(), 0);
    }

    @Test
    public void testNumberOfResourcesForFinalPoints() {
        PlayerResourcesAndFood res = new PlayerResourcesAndFood();
        res.giveResources(Arrays.asList(Effect.WOOD, Effect.WOOD, Effect.CLAY, Effect.GOLD, Effect.GOLD, Effect.GOLD, Effect.FOOD));

        assertEquals(res.numberOfResourcesForFinalPoints(), 6);
    }
}
