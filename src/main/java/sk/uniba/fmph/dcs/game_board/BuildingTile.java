package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

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
        // TODO najkomplikovanejsie
        // pay resources to acquire the building
        // handle points scored
        return null;
    }

    @Override
    public boolean skipAction(Player player) {
        // TODO
        // "If a player cannot or does not want to pay the resources,
        // she takes back her figure and leaves
        // the building in place" - pravidla hry
        return false;
    }

    @Override
    public HasAction tryToMakeAction(Player player) {
        // TODO
        // prerekvizity pre makeAction()
        return null;
    }

    @Override
    public boolean newTurn() {
        // TODO
        // check ci to staci takto
        return false;
    }
}
