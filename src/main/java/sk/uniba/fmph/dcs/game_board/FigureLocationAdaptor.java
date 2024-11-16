package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.*;

public class FigureLocationAdaptor implements InterfaceFigureLocation {

    private InterfaceFigureLocationInternal internal;

    public FigureLocationAdaptor(InterfaceFigureLocationInternal internal) {
        this.internal = internal;
    }

    @Override
    public boolean placeFigures(PlayerOrder player, int figureCount) {
        return internal.placeFigures(new Player(player, /*idk*/), figureCount);
    }

    @Override
    public HasAction tryToPlaceFigures(PlayerOrder player, int count) {
        return internal.tryToPlaceFigures(new Player(player, /**/), count);
    }

    @Override
    public ActionResult makeAction(PlayerOrder player, Effect[] inputResources, Effect[] outputResources) {
        return internal.makeAction(new Player(player, /**/), inputResources, outputResources);
    }

    @Override
    public boolean skipAction(PlayerOrder player) {
        return internal.skipAction(new Player(player, /**/));
    }

    @Override
    public HasAction tryToMakeAction(PlayerOrder player) {
        return internal.tryToMakeAction(new Player(player, /**/));
    }

    @Override
    public boolean newTurn() {
        return internal.newTurn();
    }
}
