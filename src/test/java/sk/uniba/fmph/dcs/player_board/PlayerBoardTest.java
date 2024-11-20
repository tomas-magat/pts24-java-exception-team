package sk.uniba.fmph.dcs.player_board;

import org.junit.Before;
import org.junit.Test;

class MockPlayerResourcesAndFood implements PlayerResourcesAndFood {
    private final int resources;

    MockPlayerResourcesAndFood(int resources) {
        this.resources = resources;
    }

    @Override
    public int numberOfResourcesForFinalPoints() {
        return resources;
    }

    @Override
    public String state() {
        return "";
    }
}

class MockTribeFedStatus implements TribeFedStatus {

    @Override
    public String state() {
        return "";
    }
}

record MockPlayerFigures(int totalFigures) implements PlayerFigures {
    @Override
    public String state() {
        return "";
    }
}


public class PlayerBoardTest {
    private PlayerBoard playerBoard = null;

    @Before
    public void setUp() {
        this.playerBoard = new PlayerBoard(
                0, 0,
                new PlayerCivilisationCards(),
                new PlayerTools(),
                new MockPlayerResourcesAndFood(7),
                new MockTribeFedStatus(),
                new MockPlayerFigures(10)
        );
    }

    @Test
    public void testEndOfGamePoints() {

    }
}
