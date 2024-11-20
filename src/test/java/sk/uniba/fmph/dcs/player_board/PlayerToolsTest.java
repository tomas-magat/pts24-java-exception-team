package sk.uniba.fmph.dcs.player_board;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class PlayerToolsTest {
    private PlayerTools playerTools;

    @Before
    public void setUp() {
        this.playerTools = new PlayerTools();
    }

    @Test
    public void testAddTool() {
        assertEquals(0, playerTools.getToolCount());
        playerTools.addTool();
        assertEquals(1, playerTools.getToolCount());

        for (int i = 0; i < 4; i++) {
            playerTools.addTool();
        }
        assertEquals(5, playerTools.getToolCount());

        // add too many tools
        for (int i = 0; i < 40; i++) {
            playerTools.addTool();
        }
        assertEquals(12, playerTools.getToolCount());
    }

    @Test
    public void testUseTool() {
        for (int i = 0; i < 4; i++) {
            playerTools.addTool();
        }
        // tools should now look like [2, 1, 1]
        playerTools.useTool(0);
        // tool count should not change after using
        assertEquals(4, playerTools.getToolCount());
        // sufficient tools should now be 1 (only unused tools are of value 1)
        assertTrue(playerTools.hasSufficientTools(1));
        assertFalse(playerTools.hasSufficientTools(2));
        assertFalse(playerTools.hasSufficientTools(3));
        // should not be able to use again (currently prints message:
        // "This tool has been already used"
        playerTools.useTool(0);
        // use all tools
        playerTools.useTool(1);
        assertTrue(playerTools.hasSufficientTools(1));
        playerTools.useTool(2);
        assertFalse(playerTools.hasSufficientTools(1));
        // test new turn
        playerTools.newTurn();
        assertTrue(playerTools.hasSufficientTools(2));
        assertEquals(4, playerTools.getToolCount());
    }

    @Test
    public void testSingleUseTool() {
        playerTools.addSingleUseTool(4);
        assertTrue(playerTools.hasSufficientTools(4));
        playerTools.useTool(3);
        assertFalse(playerTools.hasSufficientTools(4));
    }
}
