package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Map;

public class GamePhaseControllerFactory {

    public static GamePhaseController createGamePhaseController(
                                                                Map<Location, InterfaceFigureLocation> places,
                                                                Map<PlayerOrder, InterfaceFeedTribe> playerFeedTribe,
                                                                Map<PlayerOrder, InterfaceNewTurn> playerNewTurn,
                                                                InterfaceToolUse toolUse,
                                                                InterfaceTakeReward takeReward,
                                                                PlayerOrder startingPlayer) {

        PlaceFiguresState placeFiguresState = new PlaceFiguresState(places);
        MakeActionState makeActionState = new MakeActionState(places);
        FeedTribeState feedTribeState = new FeedTribeState(playerFeedTribe);
        NewRoundState newRoundState = new NewRoundState(places.values(),playerNewTurn);
        WaitingForToolUseState waitingForToolUseState = new WaitingForToolUseState(toolUse);
        AllPlayersTakeARewardState allPlayersTakeARewardState = new AllPlayersTakeARewardState(takeReward);
        GameEndState gameEndState = new GameEndState();

        Map<GamePhase, InterfaceGamePhaseState> dispatchers = Map.of(GamePhase.PLACE_FIGURES, placeFiguresState,
                GamePhase.MAKE_ACTION, makeActionState, GamePhase.FEED_TRIBE, feedTribeState, GamePhase.NEW_ROUND,
                newRoundState, GamePhase.WAITING_FOR_TOOL_USE, waitingForToolUseState,
                GamePhase.ALL_PLAYERS_TAKE_A_REWARD, allPlayersTakeARewardState, GamePhase.GAME_END, gameEndState);

        return new GamePhaseController(dispatchers, startingPlayer);
    }
}
