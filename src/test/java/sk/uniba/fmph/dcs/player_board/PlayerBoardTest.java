package sk.uniba.fmph.dcs.player_board;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

class MockPlayerResourcesAndFood implements PlayerResourcesAndFood {
    private int expectedResources;


    @Override
    public int numberOfResourcesForFinalPoints() {
        return expectedResources;
    }

    @Override
    public String state() {
        return "";
    }

    public void setResources(int expectedResources) {
        this.expectedResources = expectedResources;
    }
}

class MockTribeFedStatus implements TribeFedStatus {
    private int expectedFields;


    @Override
    public String state() {
        return "";
    }

    @Override
    public int getFields() {
        return expectedFields;
    }

    public void setFields(int fields) {
        this.expectedFields = fields;
    }
}

class MockPlayerFigures implements PlayerFigures {
    private int expectedFigures;


    @Override
    public int totalFigures() {
        return expectedFigures;
    }

    @Override
    public String state() {
        return "";
    }

    public void setFigures(int expectedFigures) {
        this.expectedFigures = expectedFigures;
    }
}


public class PlayerBoardTest {
    private PlayerBoard playerBoard;
    private MockPlayerResourcesAndFood mockResources;
    private MockTribeFedStatus mockFedStatus;
    private MockPlayerFigures mockFigures;
    private PlayerCivilisationCards cards;
    private PlayerTools tools;

    @Before
    public void setUp() {
        mockResources = new MockPlayerResourcesAndFood();
        mockFedStatus = new MockTribeFedStatus();
        mockFigures = new MockPlayerFigures();
        cards = new PlayerCivilisationCards();
        tools = new PlayerTools();

        this.playerBoard = new PlayerBoard(
                0, 0,
                cards,
                tools,
                mockResources,
                mockFedStatus,
                mockFigures
        );
    }

    @Test
    public void testEndOfGamePoints() {
        playerBoard.addPoints(10);
        mockResources.setResources(15);
        mockFigures.setFigures(10);
        mockFedStatus.setFields(7);

        // Test case with only green cards (no sand background)
        List<EndOfGameEffect> effects = new ArrayList<>(Arrays.asList(
                EndOfGameEffect.Medicine,
                EndOfGameEffect.Writing,
                EndOfGameEffect.Pottery,
                EndOfGameEffect.Pottery,
                EndOfGameEffect.ToolMaker,
                EndOfGameEffect.ToolMaker,
                EndOfGameEffect.Farmer,
                EndOfGameEffect.Builder,
                EndOfGameEffect.Shaman
        ));
        cards.addEndOfGameEffects(effects);

        for (int i = 0; i < 10; i++) {
            tools.addTool();
        }
        for (int i = 0; i < 5; i++) {
            playerBoard.addHouse();
        }
        playerBoard.addEndOfGamePoints();

        // points: 10 default, 15 for resources, 10 for green cards,
        // 20 for tools, 7 for fields, 5 for houses, 10 for shamans
        assertEquals(77, playerBoard.getPoints());
    }

    @Test
    public void testEmptyBoard() {
        assertEquals(0, playerBoard.getPoints());
    }
}
