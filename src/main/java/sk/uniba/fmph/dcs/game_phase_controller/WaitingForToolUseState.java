package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;
import java.util.Map;

public class WaitingForToolUseState implements InterfaceGamePhaseState{

    private Map<PlayerOrder, InterfaceToolUse> playerToolUse;
    public WaitingForToolUseState(Map<PlayerOrder, InterfaceToolUse> playerToolUse){

        this.playerToolUse = playerToolUse;
    }

    @Override
    public HasAction tryToMakeAutomaticAction(PlayerOrder player) {

        if(playerToolUse.get(player).canUseTools()){

            return HasAction.WAITING_FOR_PLAYER_ACTION;
        }

        return HasAction.NO_ACTION_POSSIBLE;
    }

    @Override
    public ActionResult makeAllPlayersTakeARewardChoice(PlayerOrder player, Effect reward) {

        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult doNotFeedThisTurn(PlayerOrder player) {

        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult feedTribe(PlayerOrder player, Collection<Effect> resources) {

        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult noMoreToolsThisThrow(PlayerOrder player) {

        return playerToolUse.get(player).finishUsingTools()?ActionResult.ACTION_DONE:ActionResult.FAILURE;
    }

    @Override
    public ActionResult useTools(PlayerOrder player, int toolIndex) {

        return playerToolUse.get(player).useTool(toolIndex)?ActionResult.ACTION_DONE:ActionResult.FAILURE;
    }

    @Override
    public ActionResult skipAction(PlayerOrder player, Location location) {

        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult makeAction(PlayerOrder player, Location location, Collection<Effect> inputResources, Collection<Effect> outputResources) {

        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult placeFigures(PlayerOrder player, Location location, int figuresCount) {

        return ActionResult.FAILURE;
    }
}
