package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;

public class WaitingForToolUseState implements InterfaceGamePhaseState{

    private final InterfaceToolUse toolUse;
    public WaitingForToolUseState(InterfaceToolUse toolUse) {

        this.toolUse = toolUse;
    }

    @Override
    public HasAction tryToMakeAutomaticAction(PlayerOrder player) {

        if(toolUse.canUseTools()){

            return HasAction.WAITING_FOR_PLAYER_ACTION;
        }

        toolUse.finishUsingTools();
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

        return toolUse.finishUsingTools()?ActionResult.ACTION_DONE:ActionResult.FAILURE;

    }

    @Override
    public ActionResult useTools(PlayerOrder player, int toolIndex) {

        return toolUse.useTool(toolIndex)?ActionResult.ACTION_DONE:ActionResult.FAILURE;
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
