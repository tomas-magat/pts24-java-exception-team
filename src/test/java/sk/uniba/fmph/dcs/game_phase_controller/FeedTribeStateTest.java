package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.game_phase_controller.mocks.FeedTribeMock;
import sk.uniba.fmph.dcs.stone_age.*;

import static org.junit.Assert.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class FeedTribeStateTest {

    private FeedTribeState feedTribeState;
    private FeedTribeMock feedTribeMock;
    private PlayerOrder player;
    @Before
    public void setUp() {

        this.player = new PlayerOrder(0,1);
        this.feedTribeMock = new FeedTribeMock();

        Map<PlayerOrder, InterfaceFeedTribe> playerBoardFeedTribe = new HashMap<>();
        playerBoardFeedTribe.put(player, feedTribeMock);
        this.feedTribeState = new FeedTribeState(playerBoardFeedTribe);
    }

    private void setExpectedAutomaticAction(List<Boolean> list){

        feedTribeMock.expectedIsTribeFed = list.get(0);
        feedTribeMock.expectedFeedTribeIfEnoughFood = list.get(1);
    }

    private ActionResult feedTribe(){

        return feedTribeState.feedTribe(player,List.of());
    }
    @Test
    public void testFeedTribe() {

        feedTribeMock.expectedFeedTribe = false;

        assertEquals(ActionResult.FAILURE, feedTribe());

        feedTribeMock.expectedFeedTribe = true;

        assertEquals(ActionResult.ACTION_DONE, feedTribe());
    }

    private ActionResult doNotFeedThisTurn(){

        return feedTribeState.doNotFeedThisTurn(player);

    }
    @Test
    public void testDoNotFeedThisTurn() {

        feedTribeMock.expectedDoNotFeedThisTurn = false;

        assertEquals(ActionResult.FAILURE, doNotFeedThisTurn());

        feedTribeMock.expectedDoNotFeedThisTurn = true;
        assertEquals(ActionResult.ACTION_DONE, doNotFeedThisTurn());
    }

    private HasAction tryToMakeAutomaticAction(){

        return feedTribeState.tryToMakeAutomaticAction(player);

    }

    @Test
    public void testTryToMakeAutomaticAction() {

        setExpectedAutomaticAction(List.of(true, true));

        assertEquals(HasAction.NO_ACTION_POSSIBLE, tryToMakeAutomaticAction());

        setExpectedAutomaticAction(List.of(false, true));

        assertEquals(HasAction.AUTOMATIC_ACTION_DONE, tryToMakeAutomaticAction());

        setExpectedAutomaticAction(List.of(false, false));

        assertEquals(HasAction.WAITING_FOR_PLAYER_ACTION, tryToMakeAutomaticAction());
    }
}