package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.*;

public class BuildingTile implements InterfaceFigureLocationInternal {
    private final ArrayList<PlayerOrder> figures;
    private final Building building; // na kazdej BuildingTile sa nachadza nejaky building

    public BuildingTile(Collection<PlayerOrder> figures, Building building) {
        this.figures = new ArrayList<>();
        this.figures.addAll(figures);
        this.building = building;
    }

    public String state() {
        StringBuilder occupiedState = new StringBuilder();
        if(figures.isEmpty()) {
            occupiedState.append("unoccupied");
        } else {
            occupiedState.append("occupied");
        }

        Map<String, String> buildingState = new LinkedHashMap<>();
        buildingState.put("building tile state", occupiedState.toString());

        String buildingType;
        if(building instanceof VariableBuilding) {
            buildingType = "variable building";
        } else if(building instanceof SimpleBuilding) {
            buildingType = "simple building";
        } else if(building instanceof ArbitraryBuilding) {
            buildingType = "arbitrary building";
        } else {
            throw new IllegalArgumentException("Unknown type of building.");
        }

        buildingState.put("type of building", buildingType);
        buildingState.put("player count", String.valueOf(figures.size()));
        for(PlayerOrder figure: figures) {
            buildingState.put("player", String.valueOf(figure.getOrder()));
        }
        return new JSONObject(buildingState).toString();
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
        if(tryToMakeAction(player) == HasAction.NO_ACTION_POSSIBLE) {
            return ActionResult.FAILURE;
        }

        boolean tookResourcesFromPlayer = player.getPlayerBoard().takeResources(inputResources.toArray(new Effect[]{}));
        if(!tookResourcesFromPlayer) {
            return ActionResult.FAILURE;
        }

        // treba zistit, ci konkretny building ide postavit z danych inputResources
        OptionalInt buildingPoints = building.build(inputResources);
        if(buildingPoints.isEmpty()) {
            throw new IllegalArgumentException("Failed to calculate building score.");
        } else {
            player.getPlayerBoard().giveEffect(new Effect[]{Effect.BUILDING});
            for(int i = 0; i < buildingPoints.getAsInt(); ++i) {
                player.getPlayerBoard().giveEffect(new Effect[]{Effect.POINT});
            }
        }

        // po skonceni akcie si hrac zoberie naspat figurku
        for(PlayerOrder figure: figures) {
            if(figure.getOrder() == player.getPlayerOrder().getOrder()) {
                figures.remove(figure);
                player.getPlayerBoard().addNewFigure();
            }
        }

        return ActionResult.ACTION_DONE;
    }

    @Override
    public boolean skipAction(Player player) {
        for(PlayerOrder figure: figures) {
            if(figure.getOrder() == player.getPlayerOrder().getOrder()) {
                figures.remove(figure);
                player.getPlayerBoard().addNewFigure();
                return true;
            }
        }
        return false; // neuspesny skip
    }

    @Override
    public HasAction tryToMakeAction(Player player) {
        boolean playerFigureOnTile = false;
        for(PlayerOrder figure: figures) {
            if(figure.getOrder() == player.getPlayerOrder().getOrder()) {
                playerFigureOnTile = true;
                break;
            }
        }
        if(!playerFigureOnTile) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public boolean newTurn() {
        return false;
    }
}
