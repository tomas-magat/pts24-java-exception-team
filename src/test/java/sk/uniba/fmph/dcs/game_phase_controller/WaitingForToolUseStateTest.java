package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.InterfaceToolUse;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MockToolUse implements InterfaceToolUse{

    List<Boolean> expectedBooleans;

    @Override
    public boolean useTool(int idx) {

        return expectedBooleans.remove(0);
    }

    @Override
    public boolean canUseTools() {

        return expectedBooleans.remove(0);
    }

    @Override
    public boolean finishUsingTools() {

        return expectedBooleans.remove(0);
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

    private void setExpectedBooleans(List<Boolean> list){

        mockToolUse.expectedBooleans = new ArrayList<>(list);
    }

    private HasAction tryToMakeAutomaticAction(){

        return waitingForToolUseState.tryToMakeAutomaticAction(player);
    }
    @Test
    public void testTryToMakeAutomaticAction() {

        setExpectedBooleans(List.of(false, true));

        assertEquals(tryToMakeAutomaticAction(), HasAction.NO_ACTION_POSSIBLE);

        setExpectedBooleans(List.of(true,false));

        assertEquals(tryToMakeAutomaticAction(), HasAction.WAITING_FOR_PLAYER_ACTION);
    }

    private ActionResult noMoreToolsThisThrow(){

        return waitingForToolUseState.noMoreToolsThisThrow(player);
    }

    @Test
    public void testNoMoreToolsThisThrow() {

        setExpectedBooleans(List.of(false));

        assertEquals(noMoreToolsThisThrow(), ActionResult.FAILURE);

        setExpectedBooleans(List.of(true));

        assertEquals(noMoreToolsThisThrow(), ActionResult.ACTION_DONE);
    }

    private ActionResult useTools(){

        return waitingForToolUseState.useTools(player, 0);
    }
    @Test
    public void testUseTools() {

        setExpectedBooleans(List.of(true));

        assertEquals(useTools(), ActionResult.ACTION_DONE);

        setExpectedBooleans(List.of(false));

        assertEquals(useTools(),ActionResult.FAILURE);
    }
}