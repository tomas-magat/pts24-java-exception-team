package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.game_board.Player;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;
import java.util.Map;

public class NewRoundState implements InterfaceGamePhaseState{

    private final Collection<InterfaceFigureLocation> places;
    private final Map<PlayerOrder, InterfaceNewTurn> playerNewTurn;

    public NewRoundState(Collection<InterfaceFigureLocation> places, Map<PlayerOrder, InterfaceNewTurn> playerNewTurn){

        this.places = places;
        this.playerNewTurn = playerNewTurn;
    }
    @Override
    public HasAction tryToMakeAutomaticAction(PlayerOrder player) {

        HasAction gameEnded = HasAction.NO_ACTION_POSSIBLE;
        for(InterfaceFigureLocation place : places){

            if(!place.newTurn()){

                gameEnded = HasAction.AUTOMATIC_ACTION_DONE;
            }
        }

        if(gameEnded == HasAction.AUTOMATIC_ACTION_DONE){

            for(PlayerOrder pl : playerNewTurn.keySet()){

                playerNewTurn.get(pl).newTurn();
            }
        }

        return gameEnded;

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
