package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;

public class AllPlayersTakeARewardState implements InterfaceGamePhaseState{

    private final InterfaceTakeReward takeReward;
    public AllPlayersTakeARewardState(InterfaceTakeReward takeReward){

        this.takeReward = takeReward;

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

        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult doNotFeedThisTurn(PlayerOrder player) {

        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult makeAllPlayersTakeARewardChoice(PlayerOrder player, Effect reward) {

        return takeReward.takeReward(player, reward)?ActionResult.ACTION_DONE:ActionResult.FAILURE;
    }

    @Override
    public HasAction tryToMakeAutomaticAction(PlayerOrder player) {

        int cntPlayers = player.getPlayers();
        for(int i = 0; i < cntPlayers; i++){
            if(takeReward.tryMakeAction(new PlayerOrder(i, cntPlayers)) != HasAction.NO_ACTION_POSSIBLE){
                return takeReward.tryMakeAction(player);
            }
        }
        return HasAction.NO_ACTION_POSSIBLE;
    }
}
