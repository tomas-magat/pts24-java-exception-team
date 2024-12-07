package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ToolMakerHutFields implements InterfaceGetState {

    private List<PlayerOrder> toolMakerFigures;
    private List<PlayerOrder> hutFigures;
    private List<PlayerOrder> fieldsFigures;
    private int restriction;
    private int locationsOccupied;

    public ToolMakerHutFields() {
        toolMakerFigures = new ArrayList<>();
        hutFigures = new ArrayList<>();
        fieldsFigures = new ArrayList<>();
        locationsOccupied = 0;
    }

    public boolean placeOnToolMaker(Player player) {
        if (!canPlaceOnToolMaker(player)) return false;
        locationsOccupied++;
        toolMakerFigures.add(player.getPlayerOrder());
        player.getPlayerBoard().takeFigures(1);
        return true;
    }

    public boolean tryToMakeActionToolMaker(Player player) {
        return toolMakerFigures.contains(player.getPlayerOrder());
    }

    public boolean actionToolMaker(Player player) {
        if (!tryToMakeActionToolMaker(player)) return false;
        player.getPlayerBoard().takeFigures(-1);
        player.getPlayerBoard().giveEffect(new Effect[] {Effect.TOOL});
        toolMakerFigures.clear();
        return true;
    }

    public boolean canPlaceOnToolMaker(Player player) {
        if (player.getPlayerOrder().getPlayers() < 4 && locationsOccupied >= 2) return false;
        if (!toolMakerFigures.isEmpty()) return false;
        if (!player.getPlayerBoard().hasFigures(1)) return false;
        return true;
    }

    public boolean placeOnHut(Player player) {
        if (!canPlaceOnHut(player)) return false;
        locationsOccupied++;
        // place 2 figures
        hutFigures.add(player.getPlayerOrder());
        hutFigures.add(player.getPlayerOrder());
        player.getPlayerBoard().takeFigures(2);
        return true;
    }

    public boolean tryToMakeActionHut(Player player) {
        return hutFigures.contains(player.getPlayerOrder());
    }

    public boolean actionHut(Player player) {
        if (!tryToMakeActionHut(player)) return false;
        player.getPlayerBoard().takeFigures(-2);
        player.getPlayerBoard().addNewFigure();
        hutFigures.clear();
        return true;
    }

    public boolean canPlaceOnHut(Player player) {
        if (player.getPlayerOrder().getPlayers() < 4 && locationsOccupied >= 2) return false;
        if (!hutFigures.isEmpty()) return false;
        if (!player.getPlayerBoard().hasFigures(2)) return false;
        return true;
    }

    public boolean placeOnFields(Player player) {
        if (!canPlaceOnFields(player)) return false;
        locationsOccupied++;
        fieldsFigures.add(player.getPlayerOrder());
        player.getPlayerBoard().takeFigures(1);
        return true;
    }

    public boolean tryToMakeActionFields(Player player) {
        return fieldsFigures.contains(player.getPlayerOrder());
    }

    public boolean actionFields(Player player) {
        if (!tryToMakeActionFields(player)) return false;
        player.getPlayerBoard().takeFigures(-1);
        player.getPlayerBoard().giveEffect(new Effect[] {Effect.FIELD});
        fieldsFigures.clear();
        return true;
    }

    public boolean canPlaceOnFields(Player player) {
        if (player.getPlayerOrder().getPlayers() < 4 && locationsOccupied >= 2) return false;
        if (!fieldsFigures.isEmpty()) return false;
        if (!player.getPlayerBoard().hasFigures(1)) return false;
        return true;
    }

    public void newTurn() {
        toolMakerFigures.clear();
        hutFigures.clear();
        fieldsFigures.clear();
        locationsOccupied = 0;
    }

    @Override
    public String state() {
        Map<String, String> state = Map.of(
                "toolMakerFigures", toolMakerFigures.toString(),
                "hutFigures", hutFigures.toString(),
                "fieldFigures", fieldsFigures.toString(),
                "locationsOccupied", locationsOccupied+"");

        return new JSONObject(state).toString();
    }

    // for testing
    public int getLocationsOccupied() {
        return locationsOccupied;
    }
}
