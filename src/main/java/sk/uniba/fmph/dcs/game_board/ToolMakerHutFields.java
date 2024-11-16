package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;
import java.util.List;

public class ToolMakerHutFields {

    private List<PlayerOrder> toolMakerFigures;
    private List<PlayerOrder> hutFigures;
    private List<PlayerOrder> fieldsFigures;
    private int restriction; // no idea

    public ToolMakerHutFields() {
        toolMakerFigures = new ArrayList<>();
        hutFigures = new ArrayList<>();
        fieldsFigures = new ArrayList<>();
    }

    public boolean placeOnToolMaker(Player player) {
        if (!canPlaceOnToolMaker(player)) return false;
        toolMakerFigures.add(player.getPlayerOrder());
        player.getPlayerBoard().takeFigures(1);
        return true;
    }

    public boolean actionToolMaker(Player player) {
        // TODO
        return false;
    }

    public boolean canPlaceOnToolMaker(Player player) {
        if (!toolMakerFigures.isEmpty()) return false;
        if (!player.getPlayerBoard().hasFigures(1)) return false;
        return true;
    }

    public boolean placeOnHut(Player player) {
        if (!canPlaceOnHut(player)) return false;
        // place 2 figures
        hutFigures.add(player.getPlayerOrder());
        hutFigures.add(player.getPlayerOrder());
        player.getPlayerBoard().takeFigures(2);
        return true;
    }

    public boolean actionHut(Player player) {
        // TODO
        return false;
    }

    public boolean canPlaceOnHut(Player player) {
        if (!hutFigures.isEmpty()) return false;
        if (!player.getPlayerBoard().hasFigures(2)) return false;
        return true;
    }

    public boolean placeOnFields(Player player) {
        if (!canPlaceOnFields(player)) return false;
        fieldsFigures.add(player.getPlayerOrder());
        player.getPlayerBoard().takeFigures(1);
        return true;
    }

    public boolean actionFields(Player player) {
        // TODO
        return false;
    }

    public boolean canPlaceOnFields(Player player) {
        if (!fieldsFigures.isEmpty()) return false;
        if (!player.getPlayerBoard().hasFigures(1)) return false;
        return true;
    }

    public void newTurn() {
        toolMakerFigures.clear();
        hutFigures.clear();
        fieldsFigures.clear();
    }

    public String getState() {
        // TODO
        return null;
    }
}
