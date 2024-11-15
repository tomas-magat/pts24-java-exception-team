package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.InterfaceToolUse;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

class MockToolUse implements InterfaceToolUse{

    boolean useToolsExpectedBoolean;
    boolean canUseToolsExpectedBoolean;
    boolean finishUsingToolsExpectedBoolean;

    @Override
    public boolean useTool(int idx) {

        return useToolsExpectedBoolean;
    }

    @Override
    public boolean canUseTools() {

        return canUseToolsExpectedBoolean;
    }

    @Override
    public boolean finishUsingTools() {

        return finishUsingToolsExpectedBoolean;
    }
}

public class WaitingForToolUseStateTest {

    private PlayerOrder player;
    private MockToolUse mockToolUse;
    private WaitingForToolUseState waitingForToolUseState;
    @Before
    public void setUp() {

        player = new PlayerOrder(0,1);
        mockToolUse = new MockToolUse();
        Map<PlayerOrder, InterfaceToolUse> playerToolUse = new HashMap<>();
        playerToolUse.put(player, mockToolUse);

        waitingForToolUseState = new WaitingForToolUseState(playerToolUse);

    }


    private HasAction tryToMakeAutomaticAction(){

        return waitingForToolUseState.tryToMakeAutomaticAction(player);
    }
    @Test
    public void testTryToMakeAutomaticAction() {

        mockToolUse.canUseToolsExpectedBoolean = false;

        assertEquals(tryToMakeAutomaticAction(), HasAction.NO_ACTION_POSSIBLE);

        mockToolUse.canUseToolsExpectedBoolean = true;

    }

    private ActionResult noMoreToolsThisThrow(){

        return waitingForToolUseState.noMoreToolsThisThrow(player);
    }

    @Test
    public void testNoMoreToolsThisThrow() {

        mockToolUse.finishUsingToolsExpectedBoolean = false;

        assertEquals(noMoreToolsThisThrow(), ActionResult.FAILURE);

        mockToolUse.finishUsingToolsExpectedBoolean = true;

        assertEquals(noMoreToolsThisThrow(), ActionResult.ACTION_DONE);
    }

    private ActionResult useTools(){

        return waitingForToolUseState.useTools(player, 0);
    }
    @Test
    public void testUseTools() {

        mockToolUse.useToolsExpectedBoolean = true;

        assertEquals(useTools(), ActionResult.ACTION_DONE);

        mockToolUse.useToolsExpectedBoolean = false;

        assertEquals(useTools(),ActionResult.FAILURE);
    }
}