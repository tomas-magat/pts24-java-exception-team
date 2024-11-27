package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class FigureLocationAdaptor implements InterfaceFigureLocation {

    private InterfaceFigureLocationInternal internal;
    private Map<InterfacePlayerBoardGameBoard, Integer> playerBoardMap;
    private Map<PlayerOrder, Integer> playerOrderMap;

    public FigureLocationAdaptor(InterfaceFigureLocationInternal internal,
                                 Map<InterfacePlayerBoardGameBoard, Integer> playerBoardMap,
                                 Map<PlayerOrder, Integer> playerOrderMap) {
        this.internal = internal;
        this.playerBoardMap = playerBoardMap;
        this.playerOrderMap = playerOrderMap;
    }

    @Override
    public boolean placeFigures(PlayerOrder player, int figureCount) {
        return internal.placeFigures(new Player(player, getPlayerBoardByPlayerOrder(player)), figureCount);
    }

    @Override
    public HasAction tryToPlaceFigures(PlayerOrder player, int count) {
        return internal.tryToPlaceFigures(new Player(player, getPlayerBoardByPlayerOrder(player)), count);
    }

    @Override
    public ActionResult makeAction(PlayerOrder player, Effect[] inputResources, Effect[] outputResources) {
        return internal.makeAction(new Player(player, getPlayerBoardByPlayerOrder(player)),
                new ArrayList<Effect>(Arrays.asList(inputResources)),
                new ArrayList<Effect>(Arrays.asList(outputResources)));
    }

    @Override
    public boolean skipAction(PlayerOrder player) {
        return internal.skipAction(new Player(player, getPlayerBoardByPlayerOrder(player)));
    }

    @Override
    public HasAction tryToMakeAction(PlayerOrder player) {
        return internal.tryToMakeAction(new Player(player, getPlayerBoardByPlayerOrder(player)));
    }

    @Override
    public boolean newTurn() {
        return internal.newTurn();
    }

    private InterfacePlayerBoardGameBoard getPlayerBoardByPlayerOrder(PlayerOrder order) {
        int playerId = playerOrderMap.get(order);
        for (Map.Entry<InterfacePlayerBoardGameBoard, Integer> entry : playerBoardMap.entrySet()) {
            if (entry.getValue() == playerId) {
                return entry.getKey();
            }
        }
        return null;
    }
}
