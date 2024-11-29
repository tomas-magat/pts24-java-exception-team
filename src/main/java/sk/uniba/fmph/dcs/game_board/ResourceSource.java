package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.*;

public class ResourceSource implements InterfaceFigureLocationInternal {
    private String name;
    private Effect resource;
    private int maxFigures;
    private int maxFigureColours;
    private Map<PlayerOrder, Integer> figures;
    private InterfaceCurrentThrow currentThrow;

    public ResourceSource(InterfaceCurrentThrow currentThrow, String name, Effect resource, int maxFigures, int maxFigureColours) {
        figures = new HashMap<>();
        this.name = name;
        this.resource = resource;
        this.maxFigures = maxFigures;
        this.maxFigureColours = maxFigureColours;
        this.currentThrow = currentThrow;
    }

    @Override
    public boolean placeFigures(Player player, int figureCount) {
        if (tryToPlaceFigures(player, figureCount) == HasAction.NO_ACTION_POSSIBLE) return false;

        PlayerOrder playerOrder = player.getPlayerOrder();
        if (figures.containsKey(playerOrder)) {
            figures.put(playerOrder, figures.get(playerOrder) + figureCount);
        } else {
            figures.put(playerOrder, figureCount);
        }
        return true;
    }

    @Override
    public HasAction tryToPlaceFigures(Player player, int count) {
        if (totalFigures() + count > maxFigures) return HasAction.NO_ACTION_POSSIBLE;

        PlayerOrder playerOrder = player.getPlayerOrder();
        if (figures.containsKey(playerOrder)) {
            return HasAction.WAITING_FOR_PLAYER_ACTION;
        } else {
            if (figures.keySet().size() < maxFigureColours) {
                return HasAction.WAITING_FOR_PLAYER_ACTION;
            } else {
                return HasAction.NO_ACTION_POSSIBLE;
            }
        }
    }

    @Override
    public ActionResult makeAction(Player player, Collection<Effect> inputResources, Collection<Effect> outputResources) {
        if (tryToMakeAction(player) == HasAction.NO_ACTION_POSSIBLE) return ActionResult.FAILURE;

        currentThrow.initiate(player, this.resource, figures.get(player.getPlayerOrder()));
        int throwResult = currentThrow.getThrowResult();

        for (int i = 0; i < throwResult; i++) {
            outputResources.add(this.resource); // idk
        }

        Effect[] effects = new Effect[outputResources.size()];
        int i = 0;
        for (Effect e : outputResources) {
            effects[i] = e;
            i++;
        }
        player.getPlayerBoard().giveEffect(effects);

        return ActionResult.ACTION_DONE;
    }

    @Override
    public boolean skipAction(Player player) {
        return false; // can resource acquiring be skipped?
    }

    @Override
    public HasAction tryToMakeAction(Player player) {
        if (!figures.containsKey(player.getPlayerOrder())) return HasAction.NO_ACTION_POSSIBLE;
        else return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public boolean newTurn() {
        figures.clear();
        return false;
    }

    public String state() {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("resource", resource.toString());
        map.put("maxFigures", maxFigures+"");
        map.put("maxFigureColours", maxFigureColours+"");
        map.put("figures", figures.toString());
        return new JSONObject(map).toString();
    }

    private int totalFigures() {
        int result = 0;
        for (Map.Entry<PlayerOrder, Integer> entry : figures.entrySet()) {
            result += entry.getValue();
        }
        return result;
    }
}
