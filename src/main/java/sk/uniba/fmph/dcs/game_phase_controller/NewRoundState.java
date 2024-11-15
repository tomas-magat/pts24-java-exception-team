package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;

public class NewRoundState implements InterfaceGamePhaseState{

    private final Collection<InterfaceFigureLocation> places;
    private final InterfaceNewTurn newTurn;

    public NewRoundState(Collection<InterfaceFigureLocation> places, InterfaceNewTurn newTurn){

        this.places = places;
        this.newTurn = newTurn;
    }
    @Override
    public HasAction tryToMakeAutomaticAction(PlayerOrder player) {

        for(InterfaceFigureLocation place : places){

            if(!place.newTurn()){

                newTurn.newTurn();
                return HasAction.AUTOMATIC_ACTION_DONE;
            }
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

        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult useTools(PlayerOrder player, int toolIndex) {

        return ActionResult.FAILURE;
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
