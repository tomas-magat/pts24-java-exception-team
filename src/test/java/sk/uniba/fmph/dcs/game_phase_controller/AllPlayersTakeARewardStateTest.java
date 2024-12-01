package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Test;
import org.junit.Before;
import sk.uniba.fmph.dcs.game_phase_controller.mocks.TakeRewardMock;
import sk.uniba.fmph.dcs.stone_age.*;


import static org.junit.Assert.*;

public class AllPlayersTakeARewardStateTest {

    private TakeRewardMock takeReward;
    private AllPlayersTakeARewardState allPlayersTakeARewardState;
    @Before
    public void setUp(){

        takeReward = new TakeRewardMock();
        allPlayersTakeARewardState = new AllPlayersTakeARewardState(takeReward);

    }
    public ActionResult makeAllPlayersTakeARewardChoice(){

        return allPlayersTakeARewardState.makeAllPlayersTakeARewardChoice(new PlayerOrder(0,1), Effect.WOOD);
    }
    @Test
    public void testMakeAllPlayersTakeARewardChoice() {

        takeReward.expectedTakeReward = true;

        assertEquals(ActionResult.ACTION_DONE, makeAllPlayersTakeARewardChoice());

        takeReward.expectedTakeReward = false;
        assertEquals(ActionResult.FAILURE, makeAllPlayersTakeARewardChoice());
    }

    public HasAction tryToMakeAutomaticAction(PlayerOrder player){

        return allPlayersTakeARewardState.tryToMakeAutomaticAction(player);
    }
    @Test
    public void testTryToMakeAutomaticAction() {

        takeReward.expectedHasAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        assertEquals(HasAction.WAITING_FOR_PLAYER_ACTION, tryToMakeAutomaticAction(new PlayerOrder(0,2)));

        takeReward.expectedHasAction = HasAction.AUTOMATIC_ACTION_DONE;
        assertEquals(HasAction.AUTOMATIC_ACTION_DONE, tryToMakeAutomaticAction(new PlayerOrder(1,2)));

        takeReward.expectedHasAction = HasAction.NO_ACTION_POSSIBLE;
        assertEquals(HasAction.NO_ACTION_POSSIBLE, tryToMakeAutomaticAction(new PlayerOrder(1,2)));
    }
}