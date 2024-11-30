package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Map;

public class GamePhaseControllerFactory {

    public static GamePhaseController createGamePhaseController(GamePhaseController gamePhaseController,
                                                                Map<Location, InterfaceFigureLocation> places,
                                                                Map<PlayerOrder, InterfaceFeedTribe> playerFeedTribe,
                                                                Map<PlayerOrder, InterfaceNewTurn> playerNewTUrn) {

        placeFiguresState = new PlaceFiguresState(places);
        makeActionState = new MakeActionState(places);
        feedTribeState = new FeedTribeState(playerFeedTribe);
        newRoundState = new NewRoundState(places,playerNewTUrn);
        waitingForToolUseState = new WaitingForToolUseState();
        allPlayersTakeARewardState = new StateMock();
        gameEndState = new StateMock();

        Map<GamePhase, InterfaceGamePhaseState> dispatchers = Map.of(GamePhase.PLACE_FIGURES, placeFiguresState,
                GamePhase.MAKE_ACTION, makeActionState, GamePhase.FEED_TRIBE, feedTribeState, GamePhase.NEW_ROUND,
                newRoundState, GamePhase.WAITING_FOR_TOOL_USE, waitingForToolUseState,
                GamePhase.ALL_PLAYERS_TAKE_A_REWARD, allPlayersTakeARewardState, GamePhase.GAME_END, gameEndState);

        return new GamePhaseController()
    }
}
