package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;
import java.util.Map;

public class FeedTribeState implements InterfaceGamePhaseState{

    private final Map<PlayerOrder, InterfaceFeedTribe> playerBoardFeedTribe;

    public FeedTribeState(Map<PlayerOrder, InterfaceFeedTribe> playerBoardFeedTribe){

        this.playerBoardFeedTribe = playerBoardFeedTribe;
    }
    @Override
    public ActionResult placeFigures(PlayerOrder player, Location location, int figuresCount) {

        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult makeAction(PlayerOrder player, Location location, Collection<Effect> inputResources, Collection<Effect> outputResources) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult skipAction(PlayerOrder player, Location location) {
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult useTools(PlayerOrder player, int toolIndex) {

        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult noMoreToolsThisThrow(PlayerOrder player) {

        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult feedTribe(PlayerOrder player, Collection<Effect> resources) {

        return playerBoardFeedTribe.get(player).feedTribe(resources.toArray(new Effect[0]))?ActionResult.ACTION_DONE:ActionResult.FAILURE;

    }

    @Override
    public ActionResult doNotFeedThisTurn(PlayerOrder player) {

        return playerBoardFeedTribe.get(player).doNotFeedThisTurn()?ActionResult.ACTION_DONE:ActionResult.FAILURE;
    }

    @Override
    public ActionResult makeAllPlayersTakeARewardChoice(PlayerOrder player, Effect reward) {
        return ActionResult.FAILURE;

    }

    @Override
    public HasAction tryToMakeAutomaticAction(PlayerOrder player) {

        if(playerBoardFeedTribe.get(player).isTribeFed()){
            return HasAction.NO_ACTION_POSSIBLE;
        }

        if(playerBoardFeedTribe.get(player).feedTribeIfEnoughFood()){

            return HasAction.AUTOMATIC_ACTION_DONE;
        }

        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }
}
