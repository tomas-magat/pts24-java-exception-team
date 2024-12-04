package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.CivilisationCard;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard implements InterfaceGetState {

    private ToolMakerHutFields toolMakerHutFields;
    private ResourceSource resourceSourceForest, resourceSourceClay, resourceSourceQuarry, resourceSourceRiver;
    private InterfaceCurrentThrow currentThrow;
    private CivilizationCardPlace civilizationCardPlace1, civilizationCardPlace2, civilizationCardPlace3, civilizationCardPlace4;
    private CivilizationCardDeck civilizationCardDeck;
    private InterfaceRewardMenu rewardMenu;
    private List<BuildingTile> buildingTiles;
    private List<Player> players;

    public GameBoard(List<Player> players,
                     ToolMakerHutFields toolMakerHutFields,
                     ResourceSource resourceSourceForest,
                     ResourceSource resourceSourceClay,
                     ResourceSource resourceSourceQuarry,
                     ResourceSource resourceSourceRiver,
                     InterfaceCurrentThrow currentThrow,
                     CivilizationCardPlace civilizationCardPlace1,
                     CivilizationCardPlace civilizationCardPlace2,
                     CivilizationCardPlace civilizationCardPlace3,
                     CivilizationCardPlace civilizationCardPlace4,
                     CivilizationCardDeck civilizationCardDeck,
                     RewardMenu rewardMenu,
                     List<BuildingTile> buildingTiles) {
        this.players = players;
        this.toolMakerHutFields = toolMakerHutFields;
        this.resourceSourceForest = resourceSourceForest;
        this.resourceSourceClay = resourceSourceClay;
        this.resourceSourceQuarry = resourceSourceQuarry;
        this.resourceSourceRiver = resourceSourceRiver;
        this.currentThrow = currentThrow;
        this.civilizationCardPlace1 = civilizationCardPlace1;
        this.civilizationCardPlace2 = civilizationCardPlace2;
        this.civilizationCardPlace3 = civilizationCardPlace3;
        this.civilizationCardPlace4 = civilizationCardPlace4;
        this.civilizationCardDeck = civilizationCardDeck;
        this.rewardMenu = rewardMenu;
        this.buildingTiles = buildingTiles;
    }

    @Override
    public String state() {
        Map<String, String> state = new HashMap<>();
        state.put("CurrentThrow", currentThrow.state());
        state.put("CivilizationCardDeck", civilizationCardDeck.state());
        state.put("CivilizationCardPlace1", civilizationCardPlace1.state());
        state.put("CivilizationCardPlace2", civilizationCardPlace2.state());
        state.put("CivilizationCardPlace3", civilizationCardPlace3.state());
        state.put("CivilizationCardPlace4", civilizationCardPlace4.state());
        state.put("RewardMenu", rewardMenu.state());
        state.put("BuildingTile1", buildingTiles.get(0).state());
        state.put("BuildingTile2", buildingTiles.get(1).state());
        state.put("BuildingTile3", buildingTiles.get(2).state());
        state.put("BuildingTile4", buildingTiles.get(3).state());
        state.put("ToolMakerHutFields", toolMakerHutFields.state());
        state.put("ResourceSourceForest", resourceSourceForest.state());
        state.put("ResourceSourceClay", resourceSourceClay.state());
        state.put("ResourceSourceQuarry", resourceSourceQuarry.state());
        state.put("ResourceSourceRiver", resourceSourceRiver.state());
        return new JSONObject(state).toString();
    }

    public ToolMakerHutFields getToolMakerHutFields() {
        return toolMakerHutFields;
    }

    public ResourceSource getResourceSourceForest() {
        return resourceSourceForest;
    }

    public ResourceSource getResourceSourceClay() {
        return resourceSourceClay;
    }

    public ResourceSource getResourceSourceQuarry() {
        return resourceSourceQuarry;
    }

    public ResourceSource getResourceSourceRiver() {
        return resourceSourceRiver;
    }

    public InterfaceCurrentThrow getCurrentThrow() {
        return currentThrow;
    }

    public CivilizationCardPlace getCivilizationCardPlace1() {
        return civilizationCardPlace1;
    }

    public CivilizationCardPlace getCivilizationCardPlace2() {
        return civilizationCardPlace2;
    }

    public CivilizationCardPlace getCivilizationCardPlace3() {
        return civilizationCardPlace3;
    }

    public CivilizationCardPlace getCivilizationCardPlace4() {
        return civilizationCardPlace4;
    }

    public CivilizationCardDeck getCivilizationCardDeck() {
        return civilizationCardDeck;
    }

    public InterfaceRewardMenu getRewardMenu() {
        return rewardMenu;
    }

    public List<BuildingTile> getBuildingTiles() {
        return buildingTiles;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
