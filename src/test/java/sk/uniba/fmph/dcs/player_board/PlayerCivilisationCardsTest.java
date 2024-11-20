package sk.uniba.fmph.dcs.player_board;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class PlayerCivilisationCardsTest {
    private PlayerCivilisationCards cards;

    @Before
    public void setUp() {
        cards = new PlayerCivilisationCards();
    }

    @Test
    public void testCalculateEndOfGamePointsRulesExample() {
        // Test example from the game rules sheet
        List<EndOfGameEffect> effects = new ArrayList<>(Arrays.asList(
                EndOfGameEffect.Medicine,
                EndOfGameEffect.Writing,
                EndOfGameEffect.Pottery,
                EndOfGameEffect.Art,
                EndOfGameEffect.Music,
                EndOfGameEffect.Pottery
        ));
        for (int i = 0; i < 5; i++) {
            effects.add(EndOfGameEffect.Farmer);
        }
        for (int i = 0; i < 3; i++) {
            effects.add(EndOfGameEffect.ToolMaker);
        }
        for (int i = 0; i < 7; i++) {
            effects.add(EndOfGameEffect.Builder);
        }
        for (int i = 0; i < 3; i++) {
            effects.add(EndOfGameEffect.Shaman);
        }
        cards.addEndOfGameEffects(effects);

        assertEquals(148,
                cards.calculateEndOfGameCivilisationCardPoints(
                        6, 7, 7, 8)
        );
    }

    @Test
    public void testNoCards() {
        // Test for a case with no cards
        assertEquals(0,
                cards.calculateEndOfGameCivilisationCardPoints(
                        6, 7, 7, 8)
        );
    }

    @Test
    public void testNoGreenCards() {
        // Test for a case with no green cards (only sand background)
        List<EndOfGameEffect> effects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            effects.add(EndOfGameEffect.Farmer);
        }
        for (int i = 0; i < 10; i++) {
            effects.add(EndOfGameEffect.ToolMaker);
        }
        for (int i = 0; i < 10; i++) {
            effects.add(EndOfGameEffect.Builder);
        }
        for (int i = 0; i < 10; i++) {
            effects.add(EndOfGameEffect.Shaman);
        }
        cards.addEndOfGameEffects(effects);

        assertEquals(200,
                cards.calculateEndOfGameCivilisationCardPoints(
                        5, 5, 5, 5)
        );
    }

    @Test
    public void testOnlyGreenCards() {
        // Test for a case with only green cards (no sand background)
        List<EndOfGameEffect> effects = new ArrayList<>(Arrays.asList(
                EndOfGameEffect.Medicine,
                EndOfGameEffect.Writing,
                EndOfGameEffect.Pottery,
                EndOfGameEffect.Pottery,
                EndOfGameEffect.Pottery
        ));
        cards.addEndOfGameEffects(effects);

        assertEquals(11,
                cards.calculateEndOfGameCivilisationCardPoints(
                        0,0, 0, 0)
        );
    }
}
