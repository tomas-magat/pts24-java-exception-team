package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.CivilisationCard;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;

import java.util.List;
import java.util.Map;

public class GameBoard implements InterfaceGetState {

    private ToolMakerHutFields toolMakerHutFields;
    private ResourceSource resourceSourceForest, resourceSourceClay, resourceSourceQuarry, resourceSourceRiver;
    private CurrentThrow currentThrow;
    private CivilizationCardPlace civilizationCardPlace1, civilizationCardPlace2, civilizationCardPlace3, civilizationCardPlace4;
    private CivilizationCardDeck civilizationCardDeck;
    private RewardMenu rewardMenu;
    private BuildingTile buildingTile; // neviem ci staci 1 instancia alebo ako je to myslene
    private List<Player> players;

    public GameBoard(List<Player> players,
                     ToolMakerHutFields toolMakerHutFields,
                     ResourceSource resourceSourceForest,
                     ResourceSource resourceSourceClay,
                     ResourceSource resourceSourceQuarry,
                     ResourceSource resourceSourceRiver,
                     CurrentThrow currentThrow,
                     CivilizationCardPlace civilizationCardPlace1,
                     CivilizationCardPlace civilizationCardPlace2,
                     CivilizationCardPlace civilizationCardPlace3,
                     CivilizationCardPlace civilizationCardPlace4,
                     CivilizationCardDeck civilizationCardDeck,
                     RewardMenu rewardMenu,
                     BuildingTile buildingTile) {
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
        this.buildingTile = buildingTile;
    }

    @Override
    public String state() {
        Map<String, String> state = Map.of(
                "ToolMakerHutFields", toolMakerHutFields.state(),
                "ResourceSourceForest", resourceSourceForest.state(),
                "ResourceSourceClay", resourceSourceClay.state(),
                "ResourceSourceQuarry", resourceSourceQuarry.state(),
                "ResourceSourceRiver", resourceSourceRiver.state(),
                "CurrentThrow", currentThrow.state(),
                "CivilizationCardDeck", civilizationCardDeck.state(),
                "CivilizationCardPlace4", civilizationCardPlace4.state(),
                "CivilizationCardPlace3", civilizationCardPlace3.state(),
                "CivilizationCardPlace2", civilizationCardPlace2.state(),
                "CivilizationCardPlace1", civilizationCardPlace1.state(),
                "RewardMenu", rewardMenu.state(),
                "BuildingTile", buildingTile.state());
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

    public CurrentThrow getCurrentThrow() {
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

    public RewardMenu getRewardMenu() {
        return rewardMenu;
    }

    public BuildingTile getBuildingTile() {
        return buildingTile;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
