package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FigureLocationAdaptor implements InterfaceFigureLocation {

    private InterfaceFigureLocationInternal internal;
    private List<Player> players; // list of all players, needed to convert PlayerOrder to Player class

    public FigureLocationAdaptor(InterfaceFigureLocationInternal internal,
                                 List<Player> players) {
        this.internal = internal;
        this.players = players;
    }

    @Override
    public boolean placeFigures(PlayerOrder player, int figureCount) {
        return internal.placeFigures(getPlayerByPlayerOrder(player), figureCount);
    }

    @Override
    public HasAction tryToPlaceFigures(PlayerOrder player, int count) {
        return internal.tryToPlaceFigures(getPlayerByPlayerOrder(player), count);
    }

    @Override
    public ActionResult makeAction(PlayerOrder player, Effect[] inputResources, Effect[] outputResources) {
        return internal.makeAction(getPlayerByPlayerOrder(player),
                new ArrayList<Effect>(Arrays.asList(inputResources)),
                new ArrayList<Effect>(Arrays.asList(outputResources)));
    }

    @Override
    public boolean skipAction(PlayerOrder player) {
        return internal.skipAction(getPlayerByPlayerOrder(player));
    }

    @Override
    public HasAction tryToMakeAction(PlayerOrder player) {
        return internal.tryToMakeAction(getPlayerByPlayerOrder(player));
    }

    @Override
    public boolean newTurn() {
        return internal.newTurn();
    }

    private Player getPlayerByPlayerOrder(PlayerOrder order) {
        for (Player p : players) {
            if (p.getPlayerOrder().equals(order)) {
                return p;
            }
        }
        return null;
    }
}
