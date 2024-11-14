package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;
import java.util.Map;

public class MakeActionState implements InterfaceGamePhaseState{

    private Map<Location, InterfaceFigureLocation> places;
    public MakeActionState(Map<Location, InterfaceFigureLocation> places){

        this.places = places;
    }

    @Override
    public ActionResult placeFigures(PlayerOrder player, Location location, int figuresCount) {

        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult makeAction(PlayerOrder player, Location location, Collection<Effect> inputResources, Collection<Effect> outputResources) {

        return places.get(location).makeAction(player, inputResources.toArray(new Effect[0]), outputResources.toArray(new Effect[0]));
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

        return ActionResult.FAILURE;
    }

    @Override
    public HasAction tryToMakeAutomaticAction(PlayerOrder player) {

        for(Location location : places.keySet()){

            if(places.get(location).tryToMakeAction(player) != HasAction.NO_ACTION_POSSIBLE){

                return HasAction.WAITING_FOR_PLAYER_ACTION;
            }
        }

        return HasAction.NO_ACTION_POSSIBLE;
    }
}
