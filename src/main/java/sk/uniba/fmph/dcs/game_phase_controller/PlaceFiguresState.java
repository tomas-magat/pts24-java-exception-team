package sk.uniba.fmph.dcs.game_phase_controller;


import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;
import java.util.Map;

public class PlaceFiguresState implements InterfaceGamePhaseState{

    private final Map<Location, InterfaceFigureLocation> places;

    public PlaceFiguresState(Map<Location, InterfaceFigureLocation> places){

        this.places = places;
    }

    @Override
    public ActionResult placeFigures(PlayerOrder player, Location location, int figuresCount) {
       return (places.get(location).placeFigures(player, figuresCount)?ActionResult.ACTION_DONE:ActionResult.FAILURE);
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
        return ActionResult.FAILURE;
    }

    @Override
    public HasAction tryToMakeAutomaticAction(PlayerOrder player) {

        //can place at least 1 figure at some location
        for(Location location : places.keySet()){

            if(places.get(location).tryToPlaceFigures(player, 1) != HasAction.NO_ACTION_POSSIBLE){

                return HasAction.WAITING_FOR_PLAYER_ACTION;
            }
        }

        return HasAction.NO_ACTION_POSSIBLE;
    }
}
