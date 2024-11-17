package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.ArrayList;
import java.util.Collection;

public class BuildingTile implements InterfaceFigureLocationInternal {
    private final ArrayList<PlayerOrder> figures;

    public BuildingTile(Collection<PlayerOrder> figures) {
        this.figures = new ArrayList<>();
        this.figures.addAll(figures);
    }

    public String state() {
        StringBuilder buildingState = new StringBuilder();
        buildingState.append("Building Tile state:");

        if(figures.isEmpty()) {
            buildingState.append("\n- The building is currently unoccupied.");
        } else {
            for(PlayerOrder figure: figures) {
                buildingState.append("\n- player: ");
                buildingState.append(figure.getOrder());
            }
        }
        return buildingState.toString();
    }

    public ArrayList<PlayerOrder> getFigures() {
        return figures;
    }

    @Override
    public boolean placeFigures(Player player, int figureCount) {
        if(tryToPlaceFigures(player, figureCount) == HasAction.NO_ACTION_POSSIBLE) {
            return false;
        }
        player.getPlayerBoard().takeFigures(figureCount);
        for(int i = 0; i < figureCount; ++i) { // vykona sa 0 alebo 1 krat, vid tryToPlaceFigures
            figures.add(player.getPlayerOrder());
        }
        return true;
    }

    @Override
    public HasAction tryToPlaceFigures(Player player, int count) {
        if(figures.size() == 1 || (count != 0 && count != 1) || !player.getPlayerBoard().hasFigures(count)) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public ActionResult makeAction(Player player, Collection<Effect> inputResources, Collection<Effect> outputResources) {
        // TODO co su outputResources?
        // TODO move scoring marker
        boolean tookResourcesFromPlayer = player.getPlayerBoard().takeResources(inputResources.toArray(new Effect[]{}));
        if(!tookResourcesFromPlayer) {
            return ActionResult.FAILURE;
        }
        player.getPlayerBoard().giveEffect(inputResources.toArray(new Effect[]{}));
        return ActionResult.ACTION_DONE;
    }

    @Override
    public boolean skipAction(Player player) {
        for(PlayerOrder figure: figures) {
            if(figure.getOrder() == player.getPlayerOrder().getOrder()) {
                figures.remove(figure);
                player.getPlayerBoard().takeFigures(-1); // ako inak urobit to, ze si hrac vezme spat figurku?
                return true;
            }
        }
        return false; // neuspesny skip
    }

    @Override
    public HasAction tryToMakeAction(Player player) {
        // TODO ako checknut ze Player ma dostatok resources na action?
        boolean playerFigureOnTile = false;
        for(PlayerOrder figure: figures) {
            if(figure.getOrder() == player.getPlayerOrder().getOrder()) {
                playerFigureOnTile = true;
                break;
            }
        }
        if(!playerFigureOnTile || skipAction(player)) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public boolean newTurn() {
        return false;
    }
}