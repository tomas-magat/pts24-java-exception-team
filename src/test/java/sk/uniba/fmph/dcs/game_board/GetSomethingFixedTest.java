package sk.uniba.fmph.dcs.game_board;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class MockPlayerBoard extends sk.uniba.fmph.dcs.player_board.PlayerBoard {
    private final List<Effect> effects = new ArrayList<>();

    public MockPlayerBoard() {
        super(0, 0, null, null, null, null, null);
    }

    public boolean hasEffect(Effect effect) {
        return effects.contains(effect);
    }
}

public class GetSomethingFixedTest {
    private GetSomethingFixed getSomethingFixed;
    private MockPlayerBoard playerBoard;
    private Player player;

    @Before
    public void setUp() {
        player = new Player(new PlayerOrder(1, 4), new PlayerBoardMock());

        List<Effect> effects = new ArrayList<>();
        effects.add(Effect.FOOD);
        effects.add(Effect.WOOD);
        effects.add(Effect.STONE);

        getSomethingFixed = new GetSomethingFixed(effects);
    }

    @Test
    public void testPerformEffectWithValidChoice() {
        boolean result = getSomethingFixed.performEffect(player, Effect.FOOD);

        assertTrue("Effect should be successfully applied for a valid choice", result);
    }

    @Test
    public void testPerformEffectWithInvalidChoice() {
        boolean result = getSomethingFixed.performEffect(player, Effect.GOLD);

        assertFalse("Effect should not be applied for an invalid choice", result);
    }

    @Test
    public void testPerformEffectWithEmptyEffectsList() {
        getSomethingFixed = new GetSomethingFixed(new ArrayList<>());

        boolean result = getSomethingFixed.performEffect(player, Effect.FOOD);

        assertFalse("Effect should not be applied when the effects list is empty", result);
    }

}
