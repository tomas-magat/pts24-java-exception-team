package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.game_phase_controller.mocks.ToolUseMock;
import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import static org.junit.Assert.*;

public class WaitingForToolUseStateTest {

    private PlayerOrder player;
    private ToolUseMock toolUseMock;
    private WaitingForToolUseState waitingForToolUseState;
    @Before
    public void setUp() {

        player = new PlayerOrder(0,1);
        toolUseMock = new ToolUseMock();

        waitingForToolUseState = new WaitingForToolUseState(toolUseMock);

    }


    private HasAction tryToMakeAutomaticAction(){

        return waitingForToolUseState.tryToMakeAutomaticAction(player);
    }
    @Test
    public void testTryToMakeAutomaticAction() {

        toolUseMock.expectedCanUseTools = false;

        assertEquals(HasAction.NO_ACTION_POSSIBLE, tryToMakeAutomaticAction());

        toolUseMock.expectedCanUseTools = true;

    }

    private ActionResult noMoreToolsThisThrow(){

        return waitingForToolUseState.noMoreToolsThisThrow(player);
    }

    @Test
    public void testNoMoreToolsThisThrow() {

        toolUseMock.expectedFinishUsingTools = false;

        assertEquals(ActionResult.FAILURE, noMoreToolsThisThrow());

        toolUseMock.expectedFinishUsingTools = true;

        assertEquals(ActionResult.ACTION_DONE, noMoreToolsThisThrow());
    }

    private ActionResult useTools(){

        return waitingForToolUseState.useTools(player, 0);
    }
    @Test
    public void testUseTools() {

        toolUseMock.expectedUseTool = true;

        assertEquals(ActionResult.ACTION_DONE, useTools());

        toolUseMock.expectedUseTool = false;

        assertEquals(ActionResult.FAILURE, useTools());
    }
}