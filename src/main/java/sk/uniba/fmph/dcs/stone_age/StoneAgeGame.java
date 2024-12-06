package sk.uniba.fmph.dcs.stone_age;

import sk.uniba.fmph.dcs.game_board.GameBoard;

import java.util.*;


public class StoneAgeGame implements InterfaceStoneAgeGame {
    private final GameBoard gameBoardState;
    private final List<InterfaceGetState> playerBoardStates;
    private final InterfaceGamePhaseController gamePhaseController;
    private final int playerCount;
    private final Map<Integer, PlayerOrder> players = constructMap();
    private final StoneAgeObservable observable = new StoneAgeObservable();


    public StoneAgeGame(GameBoard gameBoardState,
                        Collection<InterfaceGetState> playerBoardStates,
                        InterfaceGamePhaseController gamePhaseController, int playerCount) {
        this.gameBoardState = gameBoardState;
        this.playerBoardStates = new ArrayList<>(playerBoardStates);
        this.gamePhaseController = gamePhaseController;
        this.playerCount = playerCount;
    }

    private Map<Integer, PlayerOrder> constructMap() {
        Map<Integer, PlayerOrder> result = new LinkedHashMap<>();
        for(int i = 0; i < playerCount; ++i) {
            result.put(i, new PlayerOrder(i, playerCount));
        }
        return result;
    }

    private String getGameState() {
        StringBuilder gameState = new StringBuilder();
        gameState.append(gameBoardState);

        for(InterfaceGetState playerBoardState: playerBoardStates) {
            gameState.append(playerBoardState);
        }
        gameState.append(gamePhaseController.state());

        return gameState.toString();
    }

    @Override
    public boolean placeFigures(int playerId, Location location, int figuresCount) {
        observable.notify(getGameState());
        return gamePhaseController.placeFigures(players.get(playerId), location, figuresCount);
    }

    @Override
    public boolean makeAction(int playerId, Location location, Effect[] usedResources, Effect[] desiredResources) {
        observable.notify(getGameState());
        return gamePhaseController.makeAction(players.get(playerId), location,
                List.of(usedResources), List.of(desiredResources));
    }

    @Override
    public boolean skipAction(int playerId, Location location) {
        observable.notify(getGameState());
        return gamePhaseController.skipAction(players.get(playerId), location);
    }

    @Override
    public boolean useTools(int playerId, int toolIndex) {
        observable.notify(getGameState());
        return gamePhaseController.useTools(players.get(playerId), toolIndex);
    }

    @Override
    public boolean noMoreToolsThisThrow(int playerId) {
        observable.notify(getGameState());
        return gamePhaseController.noMoreToolsThisThrow(players.get(playerId));
    }

    @Override
    public boolean feedTribe(int playerId, Effect[] resources) {
        observable.notify(getGameState());
        return gamePhaseController.feedTribe(players.get(playerId), List.of(resources));
    }

    @Override
    public boolean doNotFeedThisTurn(int playerId) {
        observable.notify(getGameState());
        return gamePhaseController.doNotFeedThisTurn(players.get(playerId));
    }

    @Override
    public boolean makeAllPlayersTakeARewardChoice(int playerId, Effect reward) {
        observable.notify(getGameState());
        return gamePhaseController.makeAllPlayersTakeARewardChoice(players.get(playerId), reward);
    }
}
