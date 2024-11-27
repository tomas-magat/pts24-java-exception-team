package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Test;
import org.junit.Before;
import sk.uniba.fmph.dcs.stone_age.*;

import static org.junit.Assert.*;

class MockTakeReward implements InterfaceTakeReward{

    HasAction expectedHasAction;
    boolean expectedBoolean;
    @Override
    public boolean takeReward(PlayerOrder player, Effect reward) {

        return expectedBoolean;
    }

    @Override
    public HasAction tryMakeAction(PlayerOrder player) {

        return expectedHasAction;
    }
}
public class AllPlayersTakeARewardStateTest {

    private MockTakeReward takeReward;
    private AllPlayersTakeARewardState allPlayersTakeARewardState;
    @Before
    public void setUp(){

        takeReward = new MockTakeReward();
        allPlayersTakeARewardState = new AllPlayersTakeARewardState(takeReward);

    }
    public ActionResult makeAllPlayersTakeARewardChoice(){

        return allPlayersTakeARewardState.makeAllPlayersTakeARewardChoice(new PlayerOrder(0,1), Effect.WOOD);
    }
    @Test
    public void testMakeAllPlayersTakeARewardChoice() {

        takeReward.expectedBoolean = true;

        assertEquals(makeAllPlayersTakeARewardChoice(), ActionResult.ACTION_DONE);

        takeReward.expectedBoolean = false;
        assertEquals(makeAllPlayersTakeARewardChoice(), ActionResult.FAILURE);
    }

    public HasAction tryToMakeAutomaticAction(){

        return allPlayersTakeARewardState.tryToMakeAutomaticAction(new PlayerOrder(0,1));
    }
    @Test
    public void testTryToMakeAutomaticAction() {

        takeReward.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;

        assertEquals(tryToMakeAutomaticAction(), HasAction.NO_ACTION_POSSIBLE);

        takeReward.expectedHasAction = HasAction.AUTOMATIC_ACTION_DONE;

        assertEquals(tryToMakeAutomaticAction(), HasAction.AUTOMATIC_ACTION_DONE);

        takeReward.expectedHasAction = HasAction.WAITING_FOR_PLAYER_ACTION;
    }
}